package com.tp.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tp.entity.FileMarketValue;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class FileMarketDao extends HibernateDao<FileMarketValue, Long> {
	private static final String QUERY_BY_THEME_MARKET = "select fm from FileMarketValue fm where fm.theme.id=? and fm.market.id=?";

	@SuppressWarnings("unchecked")
	public List<FileMarketValue> getFileMarketInfo(Long themeId, Long marketId) {
		return createQuery(QUERY_BY_THEME_MARKET, themeId, marketId).list();
	}
}
