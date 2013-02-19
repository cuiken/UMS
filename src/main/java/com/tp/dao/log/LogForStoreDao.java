package com.tp.dao.log;

import org.springframework.stereotype.Component;

import com.tp.entity.log.LogForStore;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class LogForStoreDao extends HibernateDao<LogForStore, Long>{

	private static final String COUNT_VIIST="select count(*) from LogForStore where requestMethod='execute' and createTime between ? and ?";
	
	private static final String COUNT_CLIENTDOWN_BY_SHARE="select count(*) from LogForStore where requestMethod='getClient' and comeFrom='share' and createTime between ? and ?";

	private static final String COUNT_CLIENTDOWN_BY_CONTENT="select count(*) from LogForStore where requestMethod='getClient' and contentVersion is not null and createTime between ? and ?";

	
	
}

