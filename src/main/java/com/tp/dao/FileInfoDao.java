package com.tp.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tp.entity.FileInfo;
import com.tp.entity.Shelf;
import com.tp.orm.Page;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class FileInfoDao extends HibernateDao<FileInfo, Long> {

	private static final String QUERY_BY_FILE_ID_AND_LANGUAGE = "select info from FileInfo info where info.theme.id=? and info.language=?";

	private static final String QUERY_BY_SHELF_AND_STORE_AND_LANGUAGE = "select distinct fsi from FileInfo fsi join fsi.theme.shelfFiles s where s.shelf.value=? and s.shelf.store.id=? and fsi.language=?  order by s.sort";
	private static final String QUERY_BY_TOPIC_ID_AND_LANGUAGE = "select distinct fsi from FileInfo fsi join fsi.theme.topicFiles tf join fsi.theme.stores st where tf.topic.id=? and fsi.language=? and st.id=? order by tf.sort";
	private static final String QUERY_BY_CATEGORY_AND_STORE_AND_LANGUAGE = "select distinct fsi from FileInfo fsi join fsi.theme.categories c join fsi.theme.shelfFiles s join fsi.theme.stores st where c.id=? and st.id=? and fsi.language=? order by s.sort";
//
//	private static final String QUERY_NEWEST = "select fsi from FileInfo fsi where fsi.language=? order by fsi.theme.createTime desc";
//
//	private static final String QUERY_BY_DTYPE = "select fsi from FileInfo fsi where fsi.language=? and fsi.theme.dtype='1' order by fsi.theme.createTime desc";

	public FileInfo findByFileIdAndLanguage(Long fid, String language) {
		return findUnique(QUERY_BY_FILE_ID_AND_LANGUAGE, fid, language);
	}

//	@SuppressWarnings("unchecked")
//	public List<FileInfo> getFileInfoByStoreAndLanguage(Long sid, String language) {
//		return createQuery(QUERY_BY_SHELF_AND_STORE_AND_LANGUAGE, Shelf.Type.RECOMMEND.getValue(), sid, language)
//				.list();
//	}

	public Page<FileInfo> searchStoreInfoByTopic(final Page<FileInfo> page, Long topicId, String language) {
		return findPage(page, QUERY_BY_TOPIC_ID_AND_LANGUAGE, topicId, language, 2L);
	}

	/**
	 * 查找指定货架上文件的语言信息
	 */
	public Page<FileInfo> searchStoreInfoInShelf(final Page<FileInfo> page, String shelf, Long store, String lang) {
		return findPage(page, QUERY_BY_SHELF_AND_STORE_AND_LANGUAGE, shelf, store, lang);
	}

	/**
	 * 分类查找商店文件语言描述
	 */
	public Page<FileInfo> searchByCategoryAndStore(final Page<FileInfo> page, Long cid, Long sid, String lang) {
		return findPage(page, QUERY_BY_CATEGORY_AND_STORE_AND_LANGUAGE, cid, sid, lang);
	}

//	public Page<FileInfo> searchNewestByStore(final Page<FileInfo> page, Long sid, String language) {
//		return findPage(page, QUERY_NEWEST, sid, language);
//	}
//
//	public Page<FileInfo> searchDiyTemplate(final Page<FileInfo> page, Long sid, String language) {
//		return findPage(page, QUERY_BY_DTYPE, sid, language);
//	}
}
