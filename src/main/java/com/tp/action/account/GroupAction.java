package com.tp.action.account;

import java.util.Arrays;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.tp.action.CRUDActionSupport;
import com.tp.entity.account.Group;
import com.tp.entity.account.Permission;
import com.tp.service.account.AccountManager;

@Namespace("/account")
@Results({ @Result(name = CRUDActionSupport.RELOAD, location = "group.action", type = "redirect") })
public class GroupAction extends CRUDActionSupport<Group> {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Group entity;
	private List<Group> groups;

	private AccountManager accountManager;

	@Override
	public Group getModel() {

		return entity;
	}

	@Override
	@RequiresPermissions("group:view")
	public String list() throws Exception {
		groups = accountManager.getAllGroup();
		return SUCCESS;
	}

	@Override
	@RequiresPermissions("group:edit")
	public String input() throws Exception {

		return INPUT;
	}

	@Override
	@RequiresPermissions("group:edit")
	public String save() throws Exception {
		accountManager.saveGroup(entity);
		return RELOAD;
	}

	@Override
	@RequiresPermissions("group:edit")
	public String delete() throws Exception {
		accountManager.deleteGroup(id);
		return RELOAD;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id == null) {
			entity = new Group();
		} else {
			entity = accountManager.getGroup(id);
		}

	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public List<Permission> getAllPermissions() {
		return Arrays.asList(Permission.values());
	}

	public void setId(Long id) {
		this.id = id;
	}

}
