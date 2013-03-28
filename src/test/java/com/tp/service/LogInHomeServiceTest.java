package com.tp.service;

import com.google.common.collect.Maps;
import com.tp.dao.log.LogInHomeDao;
import com.tp.entity.log.LogInHome;
import com.tp.mapper.JsonMapper;
import com.tp.spring.SpringTxTestCase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * User: ken.cui
 * Date: 13-3-27
 * Time: 下午3:56
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class LogInHomeServiceTest extends SpringTxTestCase {

    private LogInHomeDao logInHomeDao;
    private Map<String, Object> logs = Maps.newHashMap();
    private JsonMapper mapper = JsonMapper.buildNormalMapper();
    private String sdate = "2013-03-25";
    private String edate = "2013-03-26";
    private String method_execute = "execute";
    private String method_getclient = "getClient";

    @Before
    public void setUp() {

        LogInHome log1 = new LogInHome();
        log1.setImei("111");
        log1.setRequestMethod(method_execute);
        log1.setCreateTime(sdate);

        LogInHome log2 = new LogInHome();
        log2.setImei("111");
        log2.setRequestMethod(method_getclient);
        log2.setCreateTime(sdate);
        log2.setRequestParams("cv:test");

        LogInHome log3 = new LogInHome();
        log3.setImei("222");
        log3.setRequestMethod(method_getclient);
        log3.setCreateTime(sdate);

        logs.put("1", mapper.toJson(log1));
        logs.put("2", mapper.toJson(log2));
        logs.put("3", mapper.toJson(log3));

        logInHomeDao.save(logs);
    }

    @Test
    public void queryTest() {
        List<LogInHome> logInHomes = logInHomeDao.queryByDate(sdate, edate);
        long clientDown = logInHomeDao.countClientDownload(method_getclient, sdate, edate);
        long users = logInHomeDao.countUserInHome(method_execute, sdate, edate);
        long down_by_content = logInHomeDao.countClientDownByContent(method_getclient, "%cv:%", sdate, edate);

        assertEquals(3L, logInHomes.size());
        assertEquals(2L, clientDown);
        assertEquals(1L, users);
        assertEquals(1L, down_by_content);
    }

    @Autowired
    public void setLogInHomeDao(LogInHomeDao logInHomeDao) {
        this.logInHomeDao = logInHomeDao;
    }
}
