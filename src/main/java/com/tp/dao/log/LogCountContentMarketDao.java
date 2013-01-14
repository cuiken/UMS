package com.tp.dao.log;

import org.springframework.stereotype.Component;

import com.tp.entity.log.LogContentMarket;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class LogCountContentMarketDao extends HibernateDao<LogContentMarket, Long> {

}
