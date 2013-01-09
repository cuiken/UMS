package com.tp.action;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.tp.entity.ClientType;
import com.tp.service.CategoryManager;

@Namespace("/category")
@Results({ @Result(name = CRUDActionSupport.RELOAD, location = "client-type.action", type = "redirect") })
public class ClientTypeAction extends CRUDActionSupport<ClientType> {

	private static final long serialVersionUID = 1L;

	private ClientType entity;
	private Long id;
	private List<ClientType> cts;
	private CategoryManager categoryManager;

	@Override
	public ClientType getModel() {

		return entity;
	}

	@Override
	public String list() throws Exception {
		cts = categoryManager.getClientTypes();
		return SUCCESS;
	}

	@Override
	@RequiresPermissions("category:edit")
	public String input() throws Exception {

		return INPUT;
	}

	@Override
	@RequiresPermissions("category:edit")
	public String save() throws Exception {
		categoryManager.saveClientType(entity);
		return RELOAD;
	}

	@Override
	@RequiresPermissions("category:edit")
	public String delete() throws Exception {
		categoryManager.deleteClientType(id);
		return RELOAD;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id == null) {
			entity = new ClientType();
		} else {
			entity = categoryManager.getClientType(id);
		}
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ClientType> getCts() {
		return cts;
	}

	@Autowired
	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

}
