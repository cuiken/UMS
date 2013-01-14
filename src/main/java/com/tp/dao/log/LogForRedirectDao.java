package com.tp.dao.log;

import org.springframework.stereotype.Component;

import com.tp.entity.log.LogForRedirect;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class LogForRedirectDao extends HibernateDao<LogForRedirect, Long> {

}
