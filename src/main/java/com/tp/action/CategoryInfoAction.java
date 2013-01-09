package com.tp.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.tp.entity.Category;
import com.tp.entity.CategoryInfo;
import com.tp.service.CategoryInfoManager;
import com.tp.service.CategoryManager;

@Namespace("/category")
@Results({ @Result(name = CRUDActionSupport.RELOAD, location = "category-info.action", params = { "cid", "${cid}" }, type = "redirect") })
public class CategoryInfoAction extends CRUDActionSupport<CategoryInfo> {

	private static final long serialVersionUID = 1L;

	private CategoryInfo entity;
	private Long id;
	private Long cid;
	private CategoryInfoManager categoryInfoManager;
	private CategoryManager categoryManager;
	private List<CategoryInfo> categoryInfos = Lists.newArrayList();

	@Override
	public CategoryInfo getModel() {

		return entity;
	}

	@Override
	public String list() throws Exception {
		Category category = categoryManager.getCategory(cid);
		categoryInfos = category.getInfos();
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {

		return INPUT;
	}

	@Override
	public String save() throws Exception {
		Category category = categoryManager.getCategory(cid);
		entity.setCategory(category);
		categoryInfoManager.saveInfo(entity);
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		categoryInfoManager.deleteInfo(id);
		return RELOAD;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id == null) {
			entity = new CategoryInfo();
		} else {
			entity = categoryInfoManager.getInfo(id);
		}

	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<CategoryInfo> getCategoryInfos() {
		return categoryInfos;
	}

	@Autowired
	public void setCategoryInfoManager(CategoryInfoManager categoryInfoManager) {
		this.categoryInfoManager = categoryInfoManager;
	}

	@Autowired
	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}
}
