package com.tp.dao.log;

import java.util.List;

import com.tp.utils.DateUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.tp.entity.log.LogInHome;
import com.tp.orm.hibernate.HibernateDao;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Component
public class LogInHomeDao {

    private static final String COUNT_BY_METHOD = "select count(*) from ? l where l.request_method=? and l.create_time between ? and ?";
    private static final String COUNT_BY_METHOD_DISTINCT = "select count(distinct l.imei) from ? l where l.request_method=? and l.create_time between ? and ?";
    private static final String DOWNLOAD_BY_CONTENT = "select count(*) from ? l where l.request_method=? and l.request_params like ? and l.create_time between ? and ?";

    private static final String QUERY_BY_DATE = "select * from ? log where log.create_time between ? and ?";

    private JdbcTemplate jdbcTemplate;

    public List<LogInHome> queryByDate(String sdate, String edate) {
        String table = selectTable(sdate);
        return jdbcTemplate.queryForList(QUERY_BY_DATE, LogInHome.class, table, sdate, edate);
    }

    public Long countClientDownload(String method, String sdate, String edate) {
        String table = selectTable(sdate);
        return jdbcTemplate.queryForLong(COUNT_BY_METHOD, table, method, sdate, edate);
    }

    public Long countUserInHome(String method, String sdate, String edate) {
        String table = selectTable(sdate);
        return jdbcTemplate.queryForLong(COUNT_BY_METHOD_DISTINCT, table, method, sdate, edate);
    }

    public Long countClientDownByContent(String method, String param, String sdate, String edate) {
        String table = selectTable(sdate);
        return jdbcTemplate.queryForLong(DOWNLOAD_BY_CONTENT, table, method, param, sdate, edate);
    }

    public void save(LogInHome entity) {
        //TODO
    }

    private String selectTable(String date) {
        String tableSuffix = DateUtil.get6charDateString(date);
        if (tableSuffix.equals("201303")) {
            return "log_f_store2";
        } else {
            return "log_f_store" + tableSuffix;
        }
    }

    @Resource
    public void setJdbcTemplate(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
