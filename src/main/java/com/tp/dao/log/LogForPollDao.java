package com.tp.dao.log;

import org.springframework.stereotype.Component;

import com.tp.entity.log.LogForPoll;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class LogForPollDao extends HibernateDao<LogForPoll, Long> {

}
