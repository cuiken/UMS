package com.tp.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tp.dao.log.LogInHomeDao;
import com.tp.entity.log.LogInHome;
import com.tp.mapper.JsonMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * User: ken.cui
 * Date: 13-3-26
 * Time: 下午5:10
 */
public class LogInHomeServiceTest {

    @InjectMocks
    private LogInHomeDao logInHomeDao;

    @Mock
    private JdbcTemplate jdbcTemplate;
    String sdate="2013-03-25";
    String edate="2013-03-26";
    String method_getclient="getClient";
    String method_execute="execute";
    String like_param="%cv:%";
    private JsonMapper mapper = JsonMapper.buildNormalMapper();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void queryByDateTest(){

        LogInHome entity=new LogInHome();
        entity.setImei("11111");
        entity.setClientVersion("2.9.0");
        entity.setAppName("app1");
        entity.setDownType("1");
        entity.setRequestMethod(method_getclient);
        entity.setRequestParams("id:1");
        entity.setResolution("300*200");
        entity.setCreateTime("2013-03-25");

        LogInHome entity2=new LogInHome();
        entity2.setRequestMethod(method_execute);
        entity.setCreateTime("2013-03-25");
        entity.setRequestParams("cv:11");


        Map<String,Object> map= Maps.newHashMap();
        map.put("1",mapper.toJson(entity));
        map.put("2",mapper.toJson(entity2));

        logInHomeDao.save(map);


        Mockito.when(logInHomeDao.queryByDate(sdate, edate)).thenReturn(Lists.newArrayList(entity,entity2));
        Mockito.when(logInHomeDao.countClientDownload(method_getclient, sdate, edate)).thenReturn(new Long(1));
        Mockito.when(logInHomeDao.countUserInHome(method_execute,sdate, edate)).thenReturn(new Long(1));
        Mockito.when(logInHomeDao.countClientDownByContent(method_getclient, like_param, sdate, edate)).thenReturn(new Long(1));


        List<LogInHome> logs= logInHomeDao.queryByDate(sdate,edate);
        long clientDown=logInHomeDao.countClientDownload(method_getclient,sdate,edate);
        long countUser=logInHomeDao.countUserInHome(method_execute,sdate,edate);
        long clientDownByContent=logInHomeDao.countClientDownByContent(method_getclient,like_param,sdate,edate);

        assertEquals(2,logs.size());
        assertEquals(1L,clientDown);
        assertEquals(1L,countUser);
        assertEquals(1L,clientDownByContent);
    }

}
