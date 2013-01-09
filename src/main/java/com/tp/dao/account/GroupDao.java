package com.tp.dao.account;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tp.entity.account.Group;
import com.tp.entity.account.User;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class GroupDao extends HibernateDao<Group, Long> {
	private static final String QUERY_USER_BY_GROUPID = "select u from User u left join u.groupList g where g.id=?";

	public void deleteWithReference(Long id) {
		//因為Group中沒有与User的关联，只能用笨办法，查询出拥有该权限组的用户, 并删除该用户的权限组.
		Group group = get(id);
		List<User> users = createQuery(QUERY_USER_BY_GROUPID, id).list();
		for (User u : users) {
			u.getGroupList().remove(group);
		}
		super.delete(group);

	}
}
