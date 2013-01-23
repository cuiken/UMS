package com.tp.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tp.entity.ThemeFile;
import com.tp.orm.Page;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class ThemeFileDao extends HibernateDao<ThemeFile, Long> {
	private static final String QUERY_FILE_BY_CATEGORY = "select f from ThemeFile f join f.categories c where c.id=?";
	private static final String Q_BY_SHELF = "select f from ThemeFile f join f.shelfFiles s where s.shelf.value=? and s.shelf.store.id=? order by s.sort";
	private static final String Q_BY_STORE = "select f from ThemeFile f join f.stores s where s.value=?";

	public Page<ThemeFile> searchFileByCategory(final Page<ThemeFile> page, Long categoryId) {

		return findPage(page, QUERY_FILE_BY_CATEGORY, categoryId);
	}

	public Page<ThemeFile> searchFileByShelf(final Page<ThemeFile> page, String shelfType, Long sid) {
		return findPage(page, Q_BY_SHELF, shelfType, sid);
	}

	@SuppressWarnings("unchecked")
	public List<ThemeFile> getByStore(String store) {
		return createQuery(Q_BY_STORE, store).list();
	}
}
