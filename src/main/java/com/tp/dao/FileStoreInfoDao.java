package com.tp.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tp.entity.FileStoreInfo;
import com.tp.entity.Shelf;
import com.tp.orm.Page;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class FileStoreInfoDao extends HibernateDao<FileStoreInfo, Long> {

	private static final String DELETE_BY_THEME_AND_STORE = "delete from FileStoreInfo fsi where fsi.theme.id=? and fsi.store.id=?";
	private static final String DELETE_BY_FID = "delete from FileStoreInfo fsi where fsi.fiId=?";

	private static final String QUERY_BY_THEME_STORE = "select fsi from FileStoreInfo fsi where fsi.theme.id=? and fsi.store.id=?";

	private static final String Q_BY_ = "select fsi from FileStoreInfo fsi where fsi.store.id=? and fsi.theme.id=? and fsi.language=?";

	private static final String Q_SHELF_THEME_STORE = "select distinct fsi from FileStoreInfo fsi join fsi.theme.shelfFiles s where s.shelf.value=? and s.shelf.store.id=? and fsi.store.id=? and fsi.language=?  order by s.sort";
	private static final String Q_BY_CATEGORY_AND_STORE = "select distinct fsi from FileStoreInfo fsi join fsi.theme.categories c join fsi.theme.shelfFiles s where c.id=? and fsi.store.id=? and fsi.language=? order by s.sort";
	//	private static final String Q_BY_STORE_AND_LANGUAGE = "select fsi from FileStoreInfo fsi where fsi.store.id=? and fsi.language=?";

	private static final String Q_NEWEST = "select fsi from FileStoreInfo fsi where fsi.store.id=? and fsi.language=? order by fsi.theme.createTime desc";

	private static final String Q_DIY_TEMPLATE = "select fsi from FileStoreInfo fsi where fsi.store.id=? and fsi.language=? and fsi.theme.dtype='1' order by fsi.theme.createTime desc";

	public void deleteByThemeAndStore(Long fid, Long sid) {
		createQuery(DELETE_BY_THEME_AND_STORE, fid, sid).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<FileStoreInfo> getInfoByTheme(Long themeId, Long storeId) {
		return createQuery(QUERY_BY_THEME_STORE, themeId, storeId).list();
	}

	public void deleteByFileInfo(Long fid) {
		createQuery(DELETE_BY_FID, fid).executeUpdate();
	}

	public FileStoreInfo get(Long sid, Long fid, String language) {
		return findUnique(Q_BY_, sid, fid, language);
	}

	@SuppressWarnings("unchecked")
	public List<FileStoreInfo> getFileInfoByStoreAndLanguage(Long sid, String language) {
		return createQuery(Q_SHELF_THEME_STORE, Shelf.Type.RECOMMEND.getValue(), sid, sid, language).list();
	}

	/**
	 * 查找指定货架上文件的语言信息
	 */
	public Page<FileStoreInfo> searchStoreInfoInShelf(final Page<FileStoreInfo> page, String st, Long sid, String lang) {
		return findPage(page, Q_SHELF_THEME_STORE, st, sid, sid, lang);
	}

	/**
	 * 分类查找商店文件语言描述
	 */
	public Page<FileStoreInfo> searchByCategoryAndStore(final Page<FileStoreInfo> page, Long cid, Long sid, String lang) {
		return findPage(page, Q_BY_CATEGORY_AND_STORE, cid, sid, lang);
	}

	public Page<FileStoreInfo> searchNewestByStore(final Page<FileStoreInfo> page, Long sid, String language) {
		return findPage(page, Q_NEWEST, sid, language);
	}

	public Page<FileStoreInfo> searchDiyTemplate(final Page<FileStoreInfo> page, Long sid, String language) {
		return findPage(page, Q_DIY_TEMPLATE, sid, language);
	}
}
