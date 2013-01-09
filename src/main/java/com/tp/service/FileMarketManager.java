package com.tp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tp.dao.FileMarketDao;
import com.tp.entity.FileMarketValue;

@Component
@Transactional
public class FileMarketManager {

	private FileMarketDao fileMarketDao;

	public List<FileMarketValue> getAll() {
		return fileMarketDao.getAll();
	}

	public List<FileMarketValue> getByFileAndMarket(Long themeId, Long marketId) {
		return fileMarketDao.getFileMarketInfo(themeId, marketId);
	}

	public void save(FileMarketValue entity) {
		fileMarketDao.save(entity);
	}

	public FileMarketValue get(Long id) {
		return fileMarketDao.get(id);
	}

	public void delete(Long id) {
		fileMarketDao.delete(id);
	}

	@Autowired
	public void setFileMarketDao(FileMarketDao fileMarketDao) {
		this.fileMarketDao = fileMarketDao;
	}
}
