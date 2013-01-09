package com.tp.dao;

import org.springframework.stereotype.Component;

import com.tp.entity.Category;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class CategoryDao extends HibernateDao<Category, Long>{

}
