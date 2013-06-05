package com.tp.service;

import com.tp.dto.LogAppUseDTO;
import com.tp.dto.LogCoopDTO;
import com.tp.entity.log.LogAppUse;
import com.tp.entity.log.LogForCoop;
import com.tp.mapper.BeanMapper;
import com.tp.mapper.JsonMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: ken.cui
 * Date: 13-4-24
 * Time: 上午10:42
 */
public class LogAppUseTest {
    private final String json = "{\"mac\":\"AA-BB\"," +
            "\"st\":\"lock\"," +
            "\"v\":\"1.0.0\"," +
            "\"op\":\"YD\"," +
            "\"r\":\"480*800\"," +
            "\"imei\":\"355302041796484\"," +
            "\"l\":\"zh\"," +
            "\"imsi\":\"null\"," +
            "\"net\":\"UNKNOW\"," +
            "\"app\":\"简单爱\"," +
            "\"f\":\"client\"," +
            "\"fm\":\"com\"}";


    private final String coopJson="{\"deduct\":\"AA-BB\"," +
            "\"ct\":\"lock\"," +
            "\"v\":\"1.0.0\"," +
            "\"op\":\"YD\"," +
            "\"r\":\"480*800\"," +
            "\"imei\":\"355302041796484\"," +
            "\"l\":\"zh\"," +
            "\"imsi\":\"null\"," +
            "\"net\":\"UNKNOW\"," +
            "\"app\":\"简单爱\"," +
            "\"dt\":\"client\"," +
            "\"fm\":\"com\"}";

    @Test
    public void mapperTest(){
        JsonMapper mapper=JsonMapper.buildNormalMapper();
        LogAppUseDTO dto=mapper.fromJson(json,LogAppUseDTO.class);
        LogAppUse entity= BeanMapper.map(dto,LogAppUse.class);
        assertEquals("AA-BB",entity.getMac());
        assertEquals("lock",entity.getSt());
        assertEquals("1.0.0",entity.getVersion());
        assertEquals("480*800",entity.getResolution());
        assertEquals("YD",entity.getOp());
        assertEquals("355302041796484",entity.getImei());
        assertEquals("null",entity.getImsi());
        assertEquals("zh",entity.getLanguage());
        assertEquals("UNKNOW",entity.getNet());
        assertEquals("简单爱",entity.getApp());
        assertEquals("client",entity.getComeFrom());
        assertEquals("com",entity.getFm());
    }

    @Test
    public void mapperCoopTest(){
        JsonMapper mapper=JsonMapper.buildNormalMapper();
        LogCoopDTO dto=mapper.fromJson(coopJson,LogCoopDTO.class);
        LogForCoop entity=BeanMapper.map(dto,LogForCoop.class);
        assertEquals("AA-BB",entity.getDeduct());
        assertEquals("lock",entity.getClientType());
        assertEquals("1.0.0",entity.getClientVersion());
        assertEquals("480*800",entity.getResolution());
        assertEquals("YD",entity.getOperators());
        assertEquals("355302041796484",entity.getImei());
        assertEquals("null",entity.getImsi());
        assertEquals("zh",entity.getLanguage());
        assertEquals("UNKNOW",entity.getNetEnv());
        assertEquals("简单爱",entity.getAppName());
        assertEquals("client",entity.getDoType());
        assertEquals("com",entity.getFromMarket());
    }

}
