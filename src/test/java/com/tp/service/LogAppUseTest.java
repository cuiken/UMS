package com.tp.service;

import com.tp.dto.LogAppUseDTO;
import com.tp.entity.log.LogAppUse;
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


}
