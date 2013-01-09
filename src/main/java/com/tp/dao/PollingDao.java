package com.tp.dao;

import org.springframework.stereotype.Component;

import com.tp.entity.Polling;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class PollingDao extends HibernateDao<Polling, Long> {

}
