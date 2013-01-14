package com.tp.dao.log;

import org.springframework.stereotype.Component;

import com.tp.entity.log.LogCountClient;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class LogCountClientDao extends HibernateDao<LogCountClient, Long> {

}
