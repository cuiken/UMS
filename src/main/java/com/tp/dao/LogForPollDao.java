package com.tp.dao;

import org.springframework.stereotype.Component;

import com.tp.entity.LogForPoll;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class LogForPollDao extends HibernateDao<LogForPoll, Long> {

}
