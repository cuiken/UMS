package com.tp.dao.log;

import org.springframework.stereotype.Component;

import com.tp.entity.log.LogCountClientInstallPerMarket;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class LogCCInstallPerMarketDao  extends HibernateDao<LogCountClientInstallPerMarket, Long>{

}
