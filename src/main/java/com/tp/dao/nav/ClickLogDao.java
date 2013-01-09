package com.tp.dao.nav;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.tp.entity.nav.ClickLog;

@Component
public class ClickLogDao {

	private static final String INSERT = "INSERT INTO click_log(user_id,button_id,date) VALUES(?,?,?)";

	private static final String COUNT_TAG_CLICKS = "SELECT count(*) as clicks,n.uuid "
			+ "FROM click_log cl LEFT JOIN nav_category n "
			+ "ON cl.button_id=n.uuid where n.dtype='tag' AND cl.user_id=? AND n.uuid is not null GROUP BY n.uuid";

	private static final String COUNT_NAVIGATOR_CLICKS = "SELECT count(*) as clicks,n.uuid FROM click_log cl LEFT JOIN nav_item n "
			+ "ON cl.button_id=n.uuid where  cl.user_id=? and n.uuid is not null GROUP BY n.uuid";

	private static final String GET_NEWEST_CLICK = "select max(date) as date,button_id from click_log where button_id in(?,?,?,?,?,?)";

	private JdbcTemplate jdbcTemplate;

	public void save(ClickLog entity) {
		jdbcTemplate.update(INSERT, entity.getUserId(), entity.getButtonId(), entity.getDate());
	}

	public List<Map<String, Object>> countNavClicks(String userId) {
		return jdbcTemplate.queryForList(COUNT_NAVIGATOR_CLICKS, userId);
	}

	public List<Map<String, Object>> countTagClicks(String userId) {
		return jdbcTemplate.queryForList(COUNT_TAG_CLICKS, userId);
	}

	public List<Map<String, Object>> getNewsNewestClickButton(String id1, String id2, String id3, String id4,
			String id5, String id6) {

		return jdbcTemplate.queryForList(GET_NEWEST_CLICK, id1, id2, id3, id4, id5, id6);
	}

	@Resource
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
}
