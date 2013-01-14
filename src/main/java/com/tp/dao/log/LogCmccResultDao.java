package com.tp.dao.log;

import org.springframework.stereotype.Component;

import com.tp.entity.log.LogCmccResult;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class LogCmccResultDao extends HibernateDao<LogCmccResult, Long> {

}
