package com.tp.service.account;

import com.tp.dao.account.GroupDao;
import com.tp.dao.account.UserDao;
import com.tp.entity.account.Group;
import com.tp.entity.account.User;
import com.tp.jms.simple.NotifyMessageProducer;
import com.tp.service.ServiceException;
import com.tp.utils.Digests;
import com.tp.utils.Encodes;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class AccountManager {

	private static Logger logger = LoggerFactory.getLogger(AccountManager.class);

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;

	private UserDao userDao;
	private GroupDao groupDao;
	private NotifyMessageProducer notifyProducer; // JMS消息发送

	//-- User Manager --//
	public User getUser(Long id) {
		return userDao.get(id);
	}

	@Transactional
	public void saveUser(User user) {
		if (isSupervisor(user)) {
			logger.warn("操作员{}尝试修改超级管理员用户", SecurityUtils.getSubject().getPrincipal());
			throw new ServiceException("不能修改超级管理员用户");
		}

		if (StringUtils.isNotBlank(user.getPlainPassword())) {
            entryptPassword(user);
		}
		userDao.save(user);

		sendNotifyMessage(user);
	}

    private void entryptPassword(User user) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        user.setSalt(Encodes.encodeHex(salt));

        byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
        user.setPassword(Encodes.encodeHex(hashPassword));
    }

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(User user) {
		return (user.getId() != null && user.getId() == 1L);
	}
	/**
	 * 发送用户变更消息.
	 * 
	 * 同时发送只有一个消费者的Queue消息与发布订阅模式有多个消费者的Topic消息.
	 */
	private void sendNotifyMessage(User user) {
		if (notifyProducer != null) {
			try {
//				notifyProducer.sendQueue(user);
				notifyProducer.sendTopic(user);
			} catch (Exception e) {
				logger.error("消息发送失败", e);
			}
		}
	}
	
	public List<User> getAllUser() {
		return userDao.getAll();
	}

	public User findUserByLoginName(String loginName) {
		return userDao.findUniqueBy("loginName", loginName);
	}

	@Transactional
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

	@Transactional
	public void saveGroup(Group entity) {
		groupDao.save(entity);

	}

	@Transactional
	public void deleteGroup(Long id) {
		groupDao.delete(id);

	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

    @Autowired
    public void setNotifyProducer(NotifyMessageProducer notifyProducer) {
        this.notifyProducer = notifyProducer;
    }
}
