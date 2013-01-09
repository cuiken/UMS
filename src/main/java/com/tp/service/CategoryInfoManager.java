package com.tp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tp.dao.CategoryInfoDao;
import com.tp.entity.CategoryInfo;

@Component
@Transactional
public class CategoryInfoManager {

	private CategoryInfoDao categoryInfoDao;

	public void saveInfo(CategoryInfo entity) {
		categoryInfoDao.save(entity);
	}

	public void deleteInfo(Long id) {
		categoryInfoDao.delete(id);
	}

	public CategoryInfo getInfo(Long id) {
		return categoryInfoDao.get(id);
	}
	
	public List<CategoryInfo> getInfosBylanguage(String language){
		return categoryInfoDao.findBy("description", language);
	}

	@Autowired
	public void setCategoryInfoDao(CategoryInfoDao categoryInfoDao) {
		this.categoryInfoDao = categoryInfoDao;
	}
}
