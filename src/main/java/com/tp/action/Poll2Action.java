package com.tp.action;

import com.google.common.collect.Lists;
import com.tp.entity.PollEnhancement;
import com.tp.orm.Page;
import com.tp.orm.PageRequest;
import com.tp.orm.PropertyFilter;
import com.tp.service.PollEnhancementService;
import com.tp.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

/**
 * User: ken.cui
 * Date: 13-3-26
 * Time: 上午10:43
 */
@Namespace("/poll")
@Results({@Result(name = CRUDActionSupport.RELOAD, location = "poll2.action", type = "redirect")})
public class Poll2Action extends CRUDActionSupport<PollEnhancement> {
    private Long id;
    private PollEnhancement entity;
    private Page<PollEnhancement> page = new Page<PollEnhancement>();
    private File image;
    private String imageFileName;
    private List<Integer> sliders = Lists.newArrayList();

    private PollEnhancementService pollEnhancementService;

    @Override
    public String list() throws Exception {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());
        if (!page.isOrderBySetted()) {
            page.setOrderBy("createTime");
            page.setOrderDir(PageRequest.Sort.DESC);
        }
        page = pollEnhancementService.searchPage(page, filters);
        sliders = page.getSlider(10);
        return SUCCESS;
    }

    @Override
    public String input() throws Exception {
        return INPUT;
    }

    @Override
    public String save() throws Exception {
        if (image != null) {
            String imgext = FileUtils.getExtension(imageFileName);
            File targetDir = new File(Constants.POLL_STORAGE);
            File targetFile = new File(targetDir, UUIDGenerator.randomLong() + "." + imgext);
            org.apache.commons.io.FileUtils.copyFile(image, targetFile);
            entity.setImgName(targetFile.getName());
            entity.setImgLink(StringUtils.substringAfter(targetFile.getPath(), Constants.LOCKER_STORAGE));
        }
        pollEnhancementService.save(entity);
        addActionMessage("保存成功");
        return RELOAD;
    }

    public String change() throws Exception {
        entity = pollEnhancementService.get(id);
        if (entity.getStatus() == 0L) {
            entity.setStatus(1L);
        } else if (entity.getStatus() == 1L) {
            entity.setStatus(0L);
        }
        pollEnhancementService.save(entity);
        return RELOAD;
    }

    public String generateXml() throws Exception {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());
        filters.add(new PropertyFilter("EQS_store", "lock"));
        filters.add(new PropertyFilter("EQL_status", "1"));
        if (!page.isOrderBySetted()) {
            page.setOrderBy("createTime,dtype");
            page.setOrderDir(PageRequest.Sort.ASC+","+PageRequest.Sort.ASC);
        }
        String xml = pollEnhancementService.toXml(page, filters);
        ServletUtils.setEtag(Struts2Utils.getResponse(), "W/\"" + Encodes.encodeMd5(xml) + "\"");
        Struts2Utils.renderXml(xml);
        return null;
    }

    @Override
    public String delete() throws Exception {
        pollEnhancementService.delete(id);
        addActionMessage("删除成功");
        return RELOAD;
    }

    @Override
    protected void prepareModel() throws Exception {
        if (id == null) {
            entity = new PollEnhancement();
            entity.setUuid(UUIDGenerator.uuid2());
            entity.setStore("lock");
        } else {
            entity = pollEnhancementService.get(id);
        }
    }

    @Override
    public PollEnhancement getModel() {
        return entity;
    }

    @Autowired
    public void setPollEnhancementService(PollEnhancementService pollEnhancementService) {
        this.pollEnhancementService = pollEnhancementService;
    }

    public Page<PollEnhancement> getPage() {
        return page;
    }

    public List<Integer> getSliders() {
        return sliders;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
