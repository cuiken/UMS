package com.tp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tp.dao.ShelfFileLinkDao;
import com.tp.dao.ThemeFileDao;
import com.tp.entity.Shelf;
import com.tp.entity.ShelfFileLink;
import com.tp.entity.ThemeFile;

@Component
@Transactional
public class ShelfFileLinkManager {

	private ShelfFileLinkDao sflDao;
	private ThemeFileDao fileDao;

	public ShelfFileLink get(Long id) {
		return sflDao.get(id);
	}

	public void delete(Long id) {
		sflDao.delete(id);
	}

	public void save(ShelfFileLink entity) {
		sflDao.save(entity);
	}

	public void deleteByShelf(Long sid) {
		sflDao.deleteByShelf(sid);
	}

	public ShelfFileLink get(Long fid, Long sid) {
		return sflDao.get(fid, sid);
	}

	public void mergeCheckedIds(Shelf shelf, List<Long> checkedIds) {
		List<ShelfFileLink> links = shelf.getShelfFile();
		if (checkedIds == null) {
			this.deleteByShelf(shelf.getId());
			return;
		}
		List<Long> orderIds = Lists.newArrayList();
		orderIds.addAll(checkedIds);
		for (ShelfFileLink link : links) {
			Long id = link.getTheme().getId();
			if (!checkedIds.contains(id)) {
				this.delete(link.getId());
			} else {
				checkedIds.remove(id);
			}
		}
		for (Long id : checkedIds) {
			ThemeFile file = fileDao.get(id);
			ShelfFileLink link = new ShelfFileLink();
			link.setShelf(shelf);
			link.setTheme(file);
			this.save(link);

		}
		int i = 0;
		for (Long id : orderIds) {
			ShelfFileLink link = this.get(id, shelf.getId());
			link.setSort(++i);
			this.save(link);
		}
	}

	@Autowired
	public void setSflDao(ShelfFileLinkDao sflDao) {
		this.sflDao = sflDao;
	}

	@Autowired
	public void setFileDao(ThemeFileDao fileDao) {
		this.fileDao = fileDao;
	}
}
