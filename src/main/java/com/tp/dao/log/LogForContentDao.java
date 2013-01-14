package com.tp.dao.log;

import org.springframework.stereotype.Component;

import com.tp.entity.log.LogForContent;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class LogForContentDao extends HibernateDao<LogForContent, Long> {

}
