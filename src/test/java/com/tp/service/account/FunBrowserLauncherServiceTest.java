package com.tp.service.account;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.tp.dao.nav.FunBrowserLaunchLogDao;
import com.tp.dto.FunBrowserLaunchDTO;
import com.tp.entity.nav.LaunchLog;
import com.tp.mapper.BeanMapper;
import com.tp.mapper.JsonMapper;
import com.tp.service.nav.FunBrowserLaunchService;

public class FunBrowserLauncherServiceTest {

	private final String json = "{\"launcher\":\"default\",\"model\":\"HTC Desire\",\"v\":\"1.0.0\",\"op\":\"null\",\"r\":\"480*800\",\"imei\":\"355302041796484\",\"l\":\"zh\",\"imsi\":\"null\",\"net\":\"UNKNOW\"}";

	@InjectMocks
	private FunBrowserLaunchService launcerService;

	@Mock
	private FunBrowserLaunchLogDao logDao;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void saveLog() {
		LaunchLog entity = getEntity();
		launcerService.save(entity);
		Mockito.verify(logDao, Mockito.never()).delete(1L);
	}

	@Test
	public void mapper() {

		LaunchLog entity = getEntity();
		assertEquals("default", entity.getLauncher());
		assertEquals("HTC Desire", entity.getModel());
		assertEquals("1.0.0", entity.getVersion());
		assertEquals("null", entity.getOp());
		assertEquals("480*800", entity.getResolution());
		assertEquals("355302041796484", entity.getImei());
		assertEquals("null", entity.getImsi());
		assertEquals("zh", entity.getLanguage());
		assertEquals("UNKNOW", entity.getNet());
	}

	private LaunchLog getEntity() {
		JsonMapper mapper = JsonMapper.buildNonDefaultMapper();
		FunBrowserLaunchDTO dto = mapper.fromJson(json, FunBrowserLaunchDTO.class);
		return BeanMapper.map(dto, LaunchLog.class);
	}
}
