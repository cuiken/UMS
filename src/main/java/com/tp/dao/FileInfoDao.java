package com.tp.dao;

import org.springframework.stereotype.Component;

import com.tp.entity.FileInfo;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class FileInfoDao extends HibernateDao<FileInfo, Long> {

	private static final String Q_BY_FID_AND_LANGUAGE = "select info from FileInfo info where info.theme.id=? and info.language=?";

	public FileInfo findByFileIdAndLanguage(Long fid, String language) {
		return findUnique(Q_BY_FID_AND_LANGUAGE, fid, language);
	}
}
