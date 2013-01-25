package com.tp.action.account;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.tp.action.CRUDActionSupport;
import com.tp.dao.HibernateUtils;
import com.tp.entity.account.Group;
import com.tp.entity.account.User;
import com.tp.service.account.AccountManager;
import com.tp.utils.Struts2Utils;

@Namespace("/account")
@Results({ @Result(name = CRUDActionSupport.RELOAD, location = "user.action", type = "redirect") })
public class UserAction extends CRUDActionSupport<User> {

	private static final long serialVersionUID = 1L;

	private Long id;
	private User entity;
	private List<User> users;
	private List<Long> checkedGroupIds;

	private AccountManager accountManager;

	@Override
	public User getModel() {

		return entity;
	}

	@Override
	@RequiresPermissions("user:view")
	public String list() throws Exception {
		users = accountManager.getAllUser();
		return SUCCESS;
	}

	@Override
	@RequiresAuthentication
	@RequiresPermissions("user:edit")
	public String input() throws Exception {
		checkedGroupIds = entity.getCheckedGroupIds();
		return INPUT;
	}

	@Override
	@RequiresAuthentication
	@RequiresPermissions("user:edit")
	public String save() throws Exception {
		HibernateUtils.mergeByCheckedIds(entity.getGroupList(), checkedGroupIds, Group.class);
		accountManager.saveUser(entity);
		return RELOAD;
	}

	@Override
	@RequiresPermissions("user:edit")
	public String delete() throws Exception {
		
		return RELOAD;
	}

	public String checkLoginName() throws Exception {
		String oldLoginName = Struts2Utils.getParameter("oldLoginName");
		String loginName = Struts2Utils.getParameter("loginName");
		if (accountManager.isLoginNameUnique(loginName, oldLoginName)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;

	}

	@Override
	protected void prepareModel() throws Exception {
		if (id == null) {
			entity = new User();
		} else {
			entity = accountManager.getUser(id);
		}

	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	public List<User> getUsers() {
		return users;
	}

	public List<Group> getAllGroups() {
		return accountManager.getAllGroup();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Long> getCheckedGroupIds() {
		return checkedGroupIds;
	}

	public void setCheckedGroupIds(List<Long> checkedGroupIds) {
		this.checkedGroupIds = checkedGroupIds;
	}

}
