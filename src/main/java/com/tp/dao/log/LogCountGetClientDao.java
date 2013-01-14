package com.tp.dao.log;

import org.springframework.stereotype.Component;

import com.tp.entity.log.LogCountGetClient;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class LogCountGetClientDao extends HibernateDao<LogCountGetClient, Long> {

}
