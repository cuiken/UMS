package com.tp.dao.log;

import com.google.common.collect.Lists;
import com.tp.entity.log.LogInHome;
import com.tp.mapper.JsonMapper;
import com.tp.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class LogInHomeDao {

    private static final String COUNT_BY_METHOD = "select count(*) from ? l where l.request_method=? and l.create_time between ? and ?";
    private static final String COUNT_BY_METHOD_DISTINCT = "select count(distinct l.imei) from ? l where l.request_method=? and l.create_time between ? and ?";
    private static final String DOWNLOAD_BY_CONTENT = "select count(*) from ? l where l.request_method=? and l.request_params like ? and l.create_time between ? and ?";

    private static final String QUERY_BY_DATE = "select * from ? log where log.create_time between ? and ?";

    private static final String INSERT_INTO = "insert into ? (imei,imsi,store_type,down_type,language,client_version,resolution,app_name,from_market,request_method,request_params,create_time)"
            + " values (?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String CREATE_TABLE = "create table ? (\n" +
            "id bigint auto_increment,\n" +
            "imei varchar(50),\n" +
            "imsi varchar(50),\n" +
            "app_name varchar(50) not null default '',\n" +
            "store_type varchar(20),\n" +
            "down_type varchar(20),\n" +
            "language varchar(20),\n" +
            "client_version varchar(20),\n" +
            "resolution varchar(100),\n" +
            "from_market varchar(255),\n" +
            "request_method varchar(255),\n" +
            "request_params varchar(255),\n" +
            "create_time varchar(32),\n" +
            "index ct_index(create_time),\n"+
            "primary key(id)\n" +
            ")ENGINE=MyISAM;";

    private static final String DROP_TABLE="drop table if exists ?";

    private static final String TABLE_PREFIX="log_f_store_";

    private JdbcTemplate jdbcTemplate;

    private JsonMapper mapper = JsonMapper.buildNormalMapper();

    public List<LogInHome> queryByDate(String sdate, String edate) {
        String table = selectTable(sdate);
        return jdbcTemplate.query(StringUtils.replaceOnce(QUERY_BY_DATE, "?", table), new Object[]{sdate, edate}, new LogInHomeRowMapper());

    }

    public void createTable() {
        String table = getNextMonthTableName();
        jdbcTemplate.execute(StringUtils.replaceOnce(DROP_TABLE, "?", table));
        jdbcTemplate.execute(StringUtils.replaceOnce(CREATE_TABLE, "?", table));
    }

    public Long countClientDownload(String method, String sdate, String edate) {
        String table = selectTable(sdate);
        return jdbcTemplate.queryForLong(StringUtils.replaceOnce(COUNT_BY_METHOD, "?", table), method, sdate, edate);
    }

    public Long countUserInHome(String method, String sdate, String edate) {
        String table = selectTable(sdate);
        return jdbcTemplate.queryForLong(StringUtils.replaceOnce(COUNT_BY_METHOD_DISTINCT, "?", table), method, sdate, edate);
    }

    public Long countClientDownByContent(String method, String param, String sdate, String edate) {
        String table = selectTable(sdate);
        return jdbcTemplate.queryForLong(StringUtils.replaceOnce(DOWNLOAD_BY_CONTENT, "?", table), method, param, sdate, edate);
    }

    public void save(final Map<String, Object> logs) {
        final List<LogInHome> logInHomes = Lists.newArrayList();
        final String table = selectTable(DateUtil.convertDate(new Date()));
        for (Map.Entry<String, Object> entry : logs.entrySet()) {
            String value = (String) entry.getValue();
            final LogInHome entity = mapper.fromJson(value, LogInHome.class);
            logInHomes.add(entity);
        }
        jdbcTemplate.batchUpdate(StringUtils.replaceOnce(INSERT_INTO, "?", table), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, logInHomes.get(i).getImei());
                ps.setString(2, logInHomes.get(i).getImsi());
                ps.setString(3, logInHomes.get(i).getStoreType());
                ps.setString(4, logInHomes.get(i).getDownType());
                ps.setString(5, logInHomes.get(i).getLanguage());
                ps.setString(6, logInHomes.get(i).getClientVersion());
                ps.setString(7, logInHomes.get(i).getResolution());
                ps.setString(8, logInHomes.get(i).getAppName());
                ps.setString(9, logInHomes.get(i).getFromMarket());
                ps.setString(10, logInHomes.get(i).getRequestMethod());
                ps.setString(11, logInHomes.get(i).getRequestParams());
                ps.setString(12, logInHomes.get(i).getCreateTime());
            }

            @Override
            public int getBatchSize() {
                return logs.size();
            }
        });
    }

    private String selectTable(String date) {
        String tableSuffix = DateUtil.get6charDateString(date);
        if (tableSuffix.equals("201303")) {
            return "log_f_store2";
        } else {
            return TABLE_PREFIX + tableSuffix;
        }
    }

    private String getNextMonthTableName() {
        String currentDate = DateUtil.convertDate(new Date());
        String nextMonthDate = DateUtil.getNextMonthDate(currentDate);
        return TABLE_PREFIX + DateUtil.get6charDateString(nextMonthDate);
    }

    @Resource
    public void setJdbcTemplate(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    class LogInHomeRowMapper implements RowMapper {
        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            LogInHome entity = new LogInHome();
            entity.setId(rs.getLong("id"));
            entity.setImei(rs.getString("imei"));
            entity.setImsi(rs.getString("imsi"));
            entity.setRequestParams(rs.getString("request_params"));
            entity.setResolution(rs.getString("resolution"));
            entity.setDownType(rs.getString("down_type"));
            entity.setAppName(rs.getString("app_name"));
            entity.setClientVersion(rs.getString("client_version"));
            entity.setCreateTime(rs.getString("create_time"));
            entity.setFromMarket(rs.getString("from_market"));
            entity.setLanguage(rs.getString("language"));
            entity.setStoreType(rs.getString("store_type"));
            return entity;
        }
    }
}
