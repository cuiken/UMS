package com.tp.service.nav;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tp.dao.nav.FunBrowserLaunchLogDao;
import com.tp.entity.nav.LaunchLog;

@Component
@Transactional
public class FunBrowserLaunchService {

	private FunBrowserLaunchLogDao logDao;

	public void save(LaunchLog entity) {
		logDao.save(entity);
	}

	@Autowired
	public void setLogDao(FunBrowserLaunchLogDao logDao) {
		this.logDao = logDao;
	}
}
