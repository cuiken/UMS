package com.tp.dao;

import org.springframework.stereotype.Component;

import com.tp.entity.LogForRedirect;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class LogForRedirectDao extends HibernateDao<LogForRedirect, Long> {

}
