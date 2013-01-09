package com.tp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tp.dao.MarketDao;
import com.tp.entity.Market;

@Component
@Transactional
public class MarketManager {

	private MarketDao marketDao;

	public List<Market> getAll() {
		return marketDao.getAll();
	}

	public Market get(Long id) {
		return marketDao.get(id);
	}

	public Market findByPkName(String pkName){
		return marketDao.findUniqueBy("pkName", pkName);
	}
	
	public void delete(Long id) {
		marketDao.delete(id);
	}

	public void save(Market entity) {
		marketDao.save(entity);
	}
	
	public boolean isNameUnique(String newName,String oldName){
		return marketDao.isPropertyUnique("name", newName, oldName);
	}

	@Autowired
	public void setMarketDao(MarketDao marketDao) {
		this.marketDao = marketDao;
	}
}
