package com.tp.action;

import java.io.File;
import java.util.List;

import com.tp.entity.Store;
import com.tp.service.CategoryManager;
import com.tp.utils.Constants;
import com.tp.utils.FileUtils;
import com.tp.utils.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.tp.entity.Advertisement;
import com.tp.orm.Page;
import com.tp.orm.PropertyFilter;
import com.tp.orm.PageRequest.Sort;
import com.tp.service.AdvertisementService;
import com.tp.utils.Struts2Utils;

@Namespace("/poll")
@Results({@Result(name = CRUDActionSupport.RELOAD, params = {"filter_EQS_store", "${store}","filter_EQS_dtype","${dtype}"}, location = "advertisement.action", type = "redirect")})
public class AdvertisementAction extends CRUDActionSupport<Advertisement> {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Advertisement entity;
    private AdvertisementService advertisementService;
    private CategoryManager categoryManager;
    private Page<Advertisement> page = new Page<Advertisement>();
    private List<Integer> sliders = Lists.newArrayList();
    private File preview;
    private String previewFileName;
    private String store;
    private String dtype;

    @Override
    public Advertisement getModel() {

        return entity;
    }

    @Override
    public String list() throws Exception {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());
        String defaultFilter = Struts2Utils.getParameter("filter_EQS_store");
        dtype = Struts2Utils.getParameter("filter_EQS_dtype");
        if (defaultFilter == null) {
            PropertyFilter filter = new PropertyFilter("EQS_store", "lock");
            filters.add(filter);
        }
        if (StringUtils.isBlank(dtype)) {
            PropertyFilter filter = new PropertyFilter("EQS_dtype", "client");
            filters.add(filter);
        }
        if (!page.isOrderBySetted()) {
            page.setOrderBy("status,sort,createTime");
            page.setOrderDir(Sort.DESC + "," +Sort.ASC+ "," + Sort.DESC);
        }
        page = advertisementService.searchAdvertisement(page, filters);
        sliders = page.getSlider(10);
        return SUCCESS;
    }

    @Override
    public String input() throws Exception {

        return INPUT;
    }

    @Override
    public String save() throws Exception {
        if (preview != null) {
            String imgext = FileUtils.getExtension(previewFileName);
            File targetDir = new File(Constants.POLL_STORAGE);
            File targetFile = new File(targetDir, UUIDGenerator.randomLong() + "." + imgext);
            org.apache.commons.io.FileUtils.copyFile(preview, targetFile);
            entity.setImgExt(imgext);
            entity.setImgName(previewFileName);
            entity.setImgLink(StringUtils.substringAfter(targetFile.getPath(), Constants.LOCKER_STORAGE));
        }
        advertisementService.save(entity);
        return RELOAD;
    }

    @Override
    public String delete() throws Exception {
        advertisementService.delete(id);
        return RELOAD;
    }

    public String change() throws Exception {
        entity = advertisementService.get(id);
        if (entity.getStatus() == 0L) {
            entity.setStatus(1L);
        } else if (entity.getStatus() == 1L) {
            entity.setStatus(0L);
        }
        advertisementService.save(entity);
        store = entity.getStore();
        dtype=entity.getDtype();
        return RELOAD;
    }

    public String generateXml() throws Exception {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());
        if (!page.isOrderBySetted()) {
            page.setOrderBy("sort");
            page.setOrderDir(Sort.ASC);
        }
        String store = Struts2Utils.getParameter("st");
        page.setPageSize(5);
        filters.add(new PropertyFilter("EQS_dtype", "client"));
        filters.add(new PropertyFilter("EQS_store", store));
        filters.add(new PropertyFilter("EQL_status", "1"));
        String xml = advertisementService.toXml(page, filters, store);
        Struts2Utils.renderXml(xml);
        return null;
    }

    @Override
    protected void prepareModel() throws Exception {
        if (id == null) {
            entity = new Advertisement();
            entity.setStatus(1L);
            entity.setImgLink("");
            entity.setImgName("");
            entity.setImgExt("");
        } else {
            entity = advertisementService.get(id);
        }

    }

    @Autowired
    public void setAdvertisementService(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    public void setCategoryManager(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }

    public List<Store> getStores() {
        return categoryManager.getAllStore();
    }

    public Page<Advertisement> getPage() {
        return page;
    }

    public List<Integer> getSliders() {
        return sliders;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPreview(File preview) {
        this.preview = preview;
    }

    public void setPreviewFileName(String previewFileName) {
        this.previewFileName = previewFileName;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }
}
