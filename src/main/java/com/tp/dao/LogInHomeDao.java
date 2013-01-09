package com.tp.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tp.entity.LogInHome;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class LogInHomeDao extends HibernateDao<LogInHome, Long> {

	private static final String COUNT_BY_METHOD = "select count(l) from LogInHome l where l.requestMethod=? and l.createTime between ? and ?";
	private static final String COUNT_BY_METHOD_DISTINCT = "select count(distinct l.imei) from LogInHome l where l.requestMethod=? and l.createTime between ? and ?";
	private static final String DWONLOAD_BY_CONTENT = "select count(l) from LogInHome l where l.requestMethod=? and l.requestParams like ? and l.createTime between ? and ?";

	private static final String QUERY_BY_DATE = "select log from LogInHome log where log.createTime between ? and ?";

	@SuppressWarnings("unchecked")
	public List<LogInHome> queryByDate(String sdate, String edate) {
		return createQuery(QUERY_BY_DATE, sdate, edate).list();
	}

	public Long countClientDownload(String method, String sDate, String eDate) {
		return (Long) createQuery(COUNT_BY_METHOD, method, sDate, eDate).uniqueResult();
	}

	public Long countUserInHome(String method, String sdate, String edate) {
		return (Long) createQuery(COUNT_BY_METHOD_DISTINCT, method, sdate, edate).uniqueResult();
	}

	public Long countClientDownByContent(String method, String param, String sdate, String edate) {
		return (Long) createQuery(DWONLOAD_BY_CONTENT, method, param, sdate, edate).uniqueResult();
	}
}
