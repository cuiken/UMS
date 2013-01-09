package com.tp.dao;

import org.springframework.stereotype.Component;

import com.tp.entity.CategoryInfo;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class CategoryInfoDao extends HibernateDao<CategoryInfo, Long> {

	public static final String QUERY_BY_CATEGORY_AND_LANGUAGE = "select info from CategoryInfo info where info.category.id=? and info.description=?";

	public CategoryInfo findByCategoryAndLanguage(Long cid, String language) {
		return findUnique(QUERY_BY_CATEGORY_AND_LANGUAGE, cid, language);
	}

}
