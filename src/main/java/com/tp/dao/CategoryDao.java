package com.tp.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tp.entity.Category;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class CategoryDao extends HibernateDao<Category, Long> {

	private static final String Q_BY_ORDER = "select c from Category c order by c.value asc";

	private static final String Q_BY_ENABLED = "select c from Category c where c.description!='hidden' order by c.value asc";

	@Override
	public List<Category> getAll() {
		return createQuery(Q_BY_ORDER).list();
	}

	public List<Category> getAllInStore() {
		return createQuery(Q_BY_ENABLED).list();
	}
}
