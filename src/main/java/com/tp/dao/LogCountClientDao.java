package com.tp.dao;

import org.springframework.stereotype.Component;

import com.tp.entity.LogCountClient;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class LogCountClientDao extends HibernateDao<LogCountClient, Long> {

}
