package com.tp.dao.log;

import java.util.*;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogContentJdbcDao {

	private static final String QUERY_CONTENT_UNZIP = "SELECT count(*) as unzip,app_name,m.name as market FROM log_f_content l"
			+ " LEFT JOIN f_market m ON m.pk_name=l.from_market  WHERE l.create_time BETWEEN"
			+ " ? AND ? AND l.do_type='unzip'  GROUP BY app_name,from_market ORDER BY app_name";

	private static final String QEURY_CLIENT_INSTALL = "SELECT count(*) as installed ,from_market FROM log_f_content l"
			+ "  WHERE l.create_time BETWEEN ? AND ? AND l.do_type='client'  GROUP BY from_market ORDER BY NULL;";

	private JdbcTemplate jdbcTemplate;

	public List<Map<String, Object>> countContentUnzip(String sdate, String edate) {
		return jdbcTemplate.queryForList(QUERY_CONTENT_UNZIP, sdate, edate);
	}

	public List<Map<String, Object>> countClientInstall(String sdate, String edate) {
		return jdbcTemplate.queryForList(QEURY_CLIENT_INSTALL, sdate, edate);
	}

	@Resource
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
}
