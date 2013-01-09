package com.tp.dao;

import org.springframework.stereotype.Component;

import com.tp.entity.LogForContent;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class LogForContentDao extends HibernateDao<LogForContent, Long> {

}
