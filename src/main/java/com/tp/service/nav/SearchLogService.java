package com.tp.service.nav;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tp.dao.nav.SearchLogDao;
import com.tp.entity.nav.SearchLog;

@Component
@Transactional
public class SearchLogService {

	private SearchLogDao searchLogDao;

	public void save(SearchLog entity) {
		searchLogDao.save(entity);
	}

	@Autowired
	public void setSearchLogDao(SearchLogDao searchLogDao) {
		this.searchLogDao = searchLogDao;
	}
}
