package com.tp.dao;

import org.springframework.stereotype.Component;

import com.tp.entity.Store;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class StoreDao extends HibernateDao<Store, Long> {

	
}
