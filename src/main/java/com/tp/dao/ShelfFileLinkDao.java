package com.tp.dao;

import org.springframework.stereotype.Component;

import com.tp.entity.ShelfFileLink;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class ShelfFileLinkDao extends HibernateDao<ShelfFileLink, Long> {

	private static final String DELETE_BY_SHELF = "delete from ShelfFileLink sfl where sfl.shelf.id=?";
	private static final String QUERY_BY_SHELF_AND_FILE = "select sfl from ShelfFileLink sfl where sfl.theme.id=? and sfl.shelf.id=?";

	public void deleteByShelf(Long sid) {
		createQuery(DELETE_BY_SHELF, sid).executeUpdate();
	}

	public ShelfFileLink get(Long fid, Long sid) {
		return findUnique(QUERY_BY_SHELF_AND_FILE, fid, sid);
	}
}
