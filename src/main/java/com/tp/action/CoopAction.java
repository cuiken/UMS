package com.tp.action;

import com.google.common.collect.Lists;
import com.tp.entity.Coop;
import com.tp.orm.Page;
import com.tp.orm.PropertyFilter;
import com.tp.service.CoopService;
import com.tp.utils.Struts2Utils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * User: ken.cui
 * Date: 13-6-6
 * Time: 上午11:17
 */
@Namespace("/category")
@Results({@Result(name=CRUDActionSupport.RELOAD,location="coop.action",type="redirect")})
public class CoopAction extends CRUDActionSupport<Coop> {

    private Coop entity;
    private Long id;
    private List<Integer> sliders = Lists.newArrayList();
    private CoopService coopService;
    private Page<Coop> page=new Page<Coop>();

    @Override
    public String list() throws Exception {
        List<PropertyFilter> filters=PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());
        page=coopService.searchPage(page,filters);
        sliders = page.getSlider(10);
        return SUCCESS;
    }

    @Override
    public String input() throws Exception {
        return INPUT;
    }

    @Override
    public String save() throws Exception {
        coopService.save(entity);
        addActionMessage("保存成功");
        return RELOAD;
    }

    @Override
    public String delete() throws Exception {
        coopService.delete(id);
        addActionMessage("删除成功");
        return RELOAD;
    }

    public String checkName() throws Exception{
        String oldValue = new String(Struts2Utils.getParameter("oldValue").getBytes("iso-8859-1"), "utf-8");
        String newValue = new String(Struts2Utils.getParameter("value").getBytes("iso-8859-1"), "utf-8");
        if(coopService.isCoopUnique(newValue,oldValue)){
            Struts2Utils.renderText("true");
        }else {
            Struts2Utils.renderText("false");
        }
        return null;
    }

    @Override
    protected void prepareModel() throws Exception {
       if(id==null){
           entity=new Coop();
       }else{
           entity=coopService.get(id);
       }
    }

    @Override
    public Coop getModel() {
        return entity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Integer> getSliders() {
        return sliders;
    }

    public Page<Coop> getPage() {
        return page;
    }

    @Autowired
    public void setCoopService(CoopService coopService) {
        this.coopService = coopService;
    }

}
