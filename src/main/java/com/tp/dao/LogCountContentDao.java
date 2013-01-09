package com.tp.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tp.entity.LogCountContent;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class LogCountContentDao extends HibernateDao<LogCountContent, Long> {

	@SuppressWarnings("unchecked")
	public List<LogCountContent> getByContentOrDate(String theme, String date) {
		String hql = "select c from LogCountContent  c ";

		if (theme != null && !theme.isEmpty() && (date == null || date.isEmpty())) {
			hql += "where c.themeName=?";
			return createQuery(hql, theme).list();
		} else if (date != null && !date.isEmpty() && (theme == null || theme.isEmpty())) {
			hql += "where c.logDate=?";
			return createQuery(hql, date).list();
		} else if (theme != null && !theme.isEmpty() && date != null && !date.isEmpty()) {
			hql += "where c.themeName=? and c.logDate=?";
			return createQuery(hql, theme, date).list();
		} else {
			return createQuery(hql).list();
		}
	}
}
