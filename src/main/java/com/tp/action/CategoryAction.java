package com.tp.action;

import java.io.File;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.tp.entity.Category;
import com.tp.service.CategoryManager;
import com.tp.utils.Constants;
import com.tp.utils.FileUtils;
import com.tp.utils.Struts2Utils;

@Namespace("/category")
@Results({ @Result(name = CRUDActionSupport.RELOAD, location = "category.action", type = "redirect") })
public class CategoryAction extends CRUDActionSupport<Category> {

	private static final long serialVersionUID = 1L;

	private Category entity;
	private Long id;
	private List<Category> categories;
	private CategoryManager categoryManager;

	private File iicon;
	private String iiconFileName;

	@Override
	@RequiresPermissions("category:edit")
	public String delete() throws Exception {
		categoryManager.deleteCategory(id);
		addActionMessage("删除成功");
		return RELOAD;
	}

	@Override
	@RequiresPermissions("category:edit")
	public String input() throws Exception {

		return INPUT;
	}

	@Override
	@RequiresPermissions("category:view")
	public String list() throws Exception {
		categories = categoryManager.getCategories();
		return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {

		if (id == null) {
			entity = new Category();
		} else {
			entity = categoryManager.getCategory(id);
		}
	}

	@Override
	@RequiresPermissions("category:edit")
	public String save() throws Exception {
		if (iicon != null) {
			String iconPath = FileUtils.getIcon(iicon, Constants.CATEGROY_STORAGE, iiconFileName);
			entity.setIcon(iconPath);
		}
		categoryManager.saveCategory(entity);
		addActionMessage("保存成功");
		return RELOAD;
	}

	public String checkCategoryName() throws Exception {
		String newCategoryName = Struts2Utils.getParameter("name");
		String oldCategoryName = Struts2Utils.getParameter("oldCategoryName");
		if (categoryManager.isCategoryUnique(newCategoryName, oldCategoryName)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;
	}

	@Override
	public Category getModel() {

		return entity;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIicon(File iicon) {
		this.iicon = iicon;
	}

	public void setIiconFileName(String iiconFileName) {
		this.iiconFileName = iiconFileName;
	}

	@Autowired
	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}
}
