package com.tp.dao.log;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogForContentJdbcDao {

	private JdbcTemplate jdbcTemplate;

	public static final String Q_APP_EACH_MARKET = "SELECT  l.app_name,market.`name` AS market_name, count(*) AS total_download"
			+ " FROM log_f_content l LEFT JOIN  f_market market ON l.from_market=market.pk_name "
			+ " WHERE l.create_time BETWEEN ? AND ? AND l.do_type='unzip' GROUP BY l.app_name,market.id ORDER BY NULL";

	public List<Map<String, Object>> queryFileByEachMarket(String sdate, String edate) {

		return jdbcTemplate.queryForList(Q_APP_EACH_MARKET, sdate, edate);

	}

	@Resource
	public void setDataSouce(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
}
