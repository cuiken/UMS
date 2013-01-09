package com.tp.service.account;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tp.dao.account.GroupDao;
import com.tp.dao.account.UserDao;
import com.tp.entity.account.Group;
import com.tp.entity.account.User;
import com.tp.service.ServiceException;
import com.tp.service.account.ShiroDbRealm.HashPassword;

@Component
@Transactional(readOnly = true)
public class AccountManager {

	private static Logger logger = LoggerFactory.getLogger(AccountManager.class);
	private UserDao userDao;
	private GroupDao groupDao;
	private ShiroDbRealm shiroRealm;

	//-- User Manager --//
	public User getUser(Long id) {
		return userDao.get(id);
	}

	@Transactional(readOnly = false)
	public void saveUser(User user) {
		if (isSupervisor(user)) {
			logger.warn("操作员{}尝试修改超级管理员用户", SecurityUtils.getSubject().getPrincipal());
			throw new ServiceException("不能修改超级管理员用户");
		}

		if (StringUtils.isNotBlank(user.getPlainPassword()) && shiroRealm != null) {
			HashPassword hashPassword = shiroRealm.encrypt(user.getPlainPassword());
			user.setSalt(hashPassword.salt);
			user.setPassword(hashPassword.password);
		}
		userDao.save(user);
		if (shiroRealm != null) {
			shiroRealm.clearCachedAuthorizationInfo(user.getLoginName());
		}
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(User user) {
		return (user.getId() != null && user.getId() == 1L);
	}

	public List<User> getAllUser() {
		return userDao.getAll();
	}

	public User findUserByLoginName(String loginName) {
		return userDao.findUniqueBy("loginName", loginName);
	}

	@Transactional(readOnly = true)
	public boolean isLoginNameUnique(String newLoginName, String oldLoginName) {
		return userDao.isPropertyUnique("loginName", newLoginName, oldLoginName);
	}

	//-- Group Manager --//
	public Group getGroup(Long id) {
		return groupDao.get(id);
	}

	public List<Group> getAllGroup() {
		return groupDao.getAll();
	}

	@Transactional(readOnly = false)
	public void saveGroup(Group entity) {
		groupDao.save(entity);
		shiroRealm.clearAllCachedAuthorizationInfo();
	}

	@Transactional(readOnly = false)
	public void deleteGroup(Long id) {
		groupDao.delete(id);
		shiroRealm.clearAllCachedAuthorizationInfo();
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	@Autowired(required = false)
	public void setShiroRealm(ShiroDbRealm shiroRealm) {
		this.shiroRealm = shiroRealm;
	}
}
