package com.tp.action;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.tp.entity.Store;
import com.tp.service.CategoryManager;
import com.tp.utils.Struts2Utils;

@Namespace("/category")
@Results({ @Result(name = CRUDActionSupport.RELOAD, location = "store.action", type = "redirect") })
public class StoreAction extends CRUDActionSupport<Store> {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Store entity;
	private List<Store> stores;
	private Long copyId;
	private CategoryManager categoryManager;

	@Override
	@RequiresPermissions("store:edit")
	public String delete() throws Exception {
		categoryManager.deleteStore(id);
		addActionMessage("删除成功");
		return RELOAD;
	}

	@Override
	@RequiresPermissions("store:edit")
	public String input() throws Exception {

		return INPUT;
	}

	@Override
	@RequiresPermissions("store:view")
	public String list() throws Exception {
		stores = categoryManager.getAllStore();
		return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id == null) {
			entity = new Store();
		} else {
			entity = categoryManager.getStore(id);
		}
	}

	@Override
	@RequiresPermissions("store:edit")
	public String save() throws Exception {

		categoryManager.saveStore(entity);
		if (copyId != null)
			categoryManager.copyAllStore(copyId, entity);
		if (id == null && copyId == null)
			categoryManager.createDefaultShelf(entity);
		addActionMessage("保存成功");
		return RELOAD;
	}

	public String checkStoreName() throws Exception {

		String newStoreName = new String(Struts2Utils.getParameter("name").getBytes("iso-8859-1"), "utf-8");
		String oldStoreName = new String(Struts2Utils.getParameter("oldStoreName").getBytes("iso-8859-1"), "utf-8");
		if (categoryManager.isStoreNameUnique(newStoreName, oldStoreName)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;
	}

	public String checkStoreValue() throws Exception {
		String newValue = Struts2Utils.getParameter("value");
		String oldValue = Struts2Utils.getParameter("oldValue");
		if (categoryManager.isStoreValueUnique(newValue, oldValue)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;
	}

	@Override
	public Store getModel() {

		return entity;
	}

	@Autowired
	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Store> getStores() {
		return stores;
	}

	public Long getCopyId() {
		return copyId;
	}

	public void setCopyId(Long copyId) {
		this.copyId = copyId;
	}
}
