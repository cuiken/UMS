package com.tp.dao.log;

import java.util.*;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogJdbcDao {

	private static final String QUERY_CONTENT_UNZIP = "SELECT count(*) as unzip,app_name,m.name as market FROM log_f_content l"
			+ " LEFT JOIN f_market m ON m.pk_name=l.from_market  WHERE l.create_time BETWEEN"
			+ " ? AND ? AND l.do_type='unzip'  GROUP BY app_name,from_market ORDER BY app_name";

	private static final String QEURY_CLIENT_INSTALL = "SELECT count(*) as installed ,from_market FROM log_f_content l"
			+ "  WHERE l.create_time BETWEEN ? AND ? AND l.do_type='client'  GROUP BY from_market ORDER BY NULL;";

	private static final String QUERY_GETCLIENT_PERMARKET = "select l.app_name,m.name as market,count(*) as get_client"
			+ " from log_f_store l left join f_market m on l.from_market=m.pk_name"
			+ " where l.create_time between ? and ? and l.request_method='getClient' group by l.app_name,l.from_market order by l.app_name";

	private JdbcTemplate jdbcTemplate;

	public List<Map<String, Object>> countContentUnzip(String sdate, String edate) {
		return jdbcTemplate.queryForList(QUERY_CONTENT_UNZIP, sdate, edate);
	}

	public List<Map<String, Object>> countClientInstall(String sdate, String edate) {
		return jdbcTemplate.queryForList(QEURY_CLIENT_INSTALL, sdate, edate);
	}

	public List<Map<String, Object>> countGetClientPerMarket(String sdate, String edate) {
		return jdbcTemplate.queryForList(QUERY_GETCLIENT_PERMARKET, sdate, edate);
	}

	@Resource
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
}
