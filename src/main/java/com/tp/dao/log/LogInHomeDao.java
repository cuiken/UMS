package com.tp.dao.log;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tp.mapper.JsonMapper;
import com.tp.utils.DateUtil;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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

    private static final String INSERT = "insert into ? (imei,imsi,store_type,down_type,language,client_version,resolution,app_name,from_market,request_method,request_params,create_time)"
            + " values (?,?,?,?,?,?,?,?,?,?,?,?)";

    private JdbcTemplate jdbcTemplate;

    private JsonMapper mapper = JsonMapper.buildNormalMapper();

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

    public void save(final Map<String,Object> logs) {
       final String table=selectTable(DateUtil.convertDate(new Date()));
        for (Map.Entry<String, Object> entry : logs.entrySet()) {
			String value = (String) entry.getValue();
			final LogInHome entity = mapper.fromJson(value, LogInHome.class);
            jdbcTemplate.batchUpdate(INSERT,new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1,table);
                    ps.setString(2,entity.getImei());
                    ps.setString(3,entity.getImsi());
                    ps.setString(4,entity.getStoreType());
                    ps.setString(5,entity.getDownType());
                    ps.setString(6,entity.getLanguage());
                    ps.setString(7,entity.getClientVersion());
                    ps.setString(8,entity.getResolution());
                    ps.setString(9,entity.getAppName());
                    ps.setString(10,entity.getFromMarket());
                    ps.setString(11,entity.getRequestMethod());
                    ps.setString(12,entity.getRequestParams());
                    ps.setString(13,entity.getCreateTime());
                    if(i%100==0){
                        ps.executeBatch();
                    }
                }

                @Override
                public int getBatchSize() {
                   return  logs.size();
                }
            });

		}
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
