package com.tp.service;

import java.util.List;
import java.util.Map;

import com.tp.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.IndexSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.tp.dao.MarketDao;
import com.tp.dao.ThemeFileDao;
import com.tp.dao.log.LogInHomeDao;
import com.tp.dao.log.LogJdbcDao;
import com.tp.entity.Market;
import com.tp.entity.ThemeFile;
import com.tp.entity.log.LogContentMarket;
import com.tp.entity.log.LogCountClient;
import com.tp.entity.log.LogCountClientInstallPerMarket;
import com.tp.entity.log.LogCountClientInstallWithContent;
import com.tp.entity.log.LogCountContent;
import com.tp.entity.log.LogCountGetClient;
import com.tp.entity.log.LogCountUnzip;
import com.tp.search.LogSearch;
import com.tp.utils.Constants;

@Component
@Transactional
public class ReportService {

	private LogJdbcDao logJdbcDao;
	private LogService logService;

	private LogSearch logSearch;
	private ThemeFileDao themeDao;
	private MarketDao marketDao;
	private LogInHomeDao logHomeDao;

	public void createClientInstallPermarketReport(String sdate, String edate) {
		List<Map<String, Object>> resultMaps = logJdbcDao.countClientInstallPerMarket(sdate, edate);
		for (Map<String, Object> result : resultMaps) {
			LogCountClientInstallPerMarket entity = new LogCountClientInstallPerMarket();
			entity.setDate(sdate);
			entity.setInstalled((Long) result.get("installed"));
			entity.setMarketName((String) result.get("from_market"));
            entity.setDistinctInstalled((Long)result.get("discount"));
			logService.saveClientInstall(entity);
		}
	}

	public void createClientInstallWithContentReport(String sdate, String edate) {
		List<Map<String, Object>> resultMaps = logJdbcDao.countInstallWithContentPerMarket(sdate, edate);
		for (Map<String, Object> result : resultMaps) {
			LogCountClientInstallWithContent entity = new LogCountClientInstallWithContent();
			entity.setDate(sdate);
			entity.setInstalled((Long) result.get("installed"));
			entity.setAppName((String) result.get("app_name"));
			entity.setMarketName((String) result.get("from_market"));
			logService.saveClientInstallWithContent(entity);
		}
	}

	public void createContentReport(IndexSearcher searcher, String sdate, String edate) throws Exception {
		List<ThemeFile> themes = themeDao.getByStore("lock");
		List<Market> markets = marketDao.getAll();
		for (ThemeFile theme : themes) {
			LogCountContent lcct = new LogCountContent();

			String fid = String.valueOf(theme.getId());
			long totalVisit = logSearch.contentVisits(searcher, fid, sdate, edate);
			long visitByAD = logSearch.contentVisitByAD(searcher, fid, sdate, edate);
			long marketDown = logSearch.contentDownFromMarket(searcher, theme.getMarketURL(), sdate, edate);
			long selfDown = logSearch.contentDownFromStore(searcher, fid, sdate, edate);

			lcct.setLogDate(sdate);
			lcct.setThemeName(theme.getTitle());
			lcct.setTotalVisit(totalVisit);
			lcct.setTotalDown(marketDown + selfDown);
			lcct.setVisitByAd(visitByAD);
			lcct.setVisitByStore(totalVisit - visitByAD);
			lcct.setDownByStore(selfDown);
			logService.saveCountContent(lcct);
			perMarketDown(searcher, theme, lcct, markets, sdate, edate);
		}
	}

	public void createCountContentUnzipReport(String sdate, String edate) {
		List<Map<String, Object>> resultMaps = logJdbcDao.countContentUnzip(sdate, edate);

		for (Map<String, Object> result : resultMaps) {
			LogCountUnzip entity = new LogCountUnzip();
			entity.setAppName((String) result.get("app_name"));
			entity.setUnzip((Long) result.get("unzip"));
			entity.setMarketName((String) result.get("market"));
			entity.setCreateTime(sdate);

			logService.saveCountContentUnzip(entity);

		}
	}

	public void createClientReport(IndexSearcher searcher, String sdate, String edate) throws Exception {
		LogCountClient client = new LogCountClient();
		long start = System.currentTimeMillis();
		long downTotal = logHomeDao.countClientDownload(Constants.METHOD_GET_CLIENT, sdate, edate);
		long downByContent = logHomeDao.countClientDownByContent(Constants.METHOD_GET_CLIENT, "%cv:%", sdate, edate);
		long downByShare = logSearch.downloadByShare(searcher, sdate, edate);
		long totalUser = logService.countTotalUser(edate);
		long perTotalUser = 0L;

		LogCountClient perCount = logService.getLogClientCountByDate(DateUtil.getPerDate(sdate));
		if (perCount != null) {
			perTotalUser = perCount.getTotalUser();
		}

		Map<String, Object> install = getClientInstall(sdate, edate);
		client.setCreateTime(sdate);
		client.setDownByContent(downByContent);
		client.setDownByShare(downByShare);
		client.setTotalDownload(downTotal);
		client.setDownByOther(downTotal - downByContent - downByShare);
		client.setVisitStoreCount(logSearch.storeVisits(searcher, sdate, edate));
		client.setVisitStoreUser(logService.countVisitUser(sdate, edate));
		client.setOpenCount(logService.countUse(sdate, edate));
		client.setTotalUser(totalUser);
		client.setIncrementUser(totalUser - perTotalUser);
		client.setOpenUser(logService.countOpenUser(sdate, edate));
		client.setTotalInstall((Long) install.get("all"));
		client.setInstallNonfm((Long) install.get("nofm"));
		client.setInstallWithfm((Long) install.get("fm"));
        client.setInstallUser((Long) install.get("installUser"));
		long end = System.currentTimeMillis();
		client.setTakeTimes(end - start);
		logService.saveCountClient(client);
	}

	public void createGetClientPerMarketReport(String sdate, String edate) {
		List<Map<String, Object>> getClients = logHomeDao.countGetClientPerMarket(sdate, edate);
		for (Map<String, Object> content : getClients) {
			LogCountGetClient entity = new LogCountGetClient();
			entity.setAppName((String) content.get("app_name"));
			entity.setMarketName((String) content.get("market"));
			entity.setDownload((Long) content.get("get_client"));
			entity.setCreateTime(sdate);
			logService.saveCountGetClient(entity);
		}
	}

	private Map<String, Object> getClientInstall(String sdate, String edate) {
		List<Map<String, Object>> installs = logJdbcDao.countClientInstallPerMarket(sdate, edate);
		Map<String, Object> results = Maps.newHashMap();
		long fm = 0;
		long nofm = 0;
		long all = 0;
        long installUser=0;
		for (Map<String, Object> content : installs) {
			String fromMarket = (String) content.get("from_market");
			Long installed = (Long) content.get("installed");
            Long discount=(Long)content.get("discount");
			all += installed;
            installUser+=discount;
			if (fromMarket != null && !fromMarket.isEmpty()) {
				fm += installed;
			} else {
				nofm += installed;
			}
		}
		results.put("all", all);
		results.put("fm", fm);
		results.put("nofm", nofm);
        results.put("installUser",installUser);
		return results;
	}

	private void perMarketDown(IndexSearcher searcher, ThemeFile theme, LogCountContent lcc, List<Market> markets,
			String sdate, String edate) throws Exception {

		for (Market market : markets) {
			if (market.getThemes().contains(theme)) {
				LogContentMarket ccMarket = new LogContentMarket();

				String marketKey = market.getMarketKey();
				marketKey = escape(marketKey);
				if (marketKey.equals("marketclient")) {
					marketKey = market.getPkName();
				}
				String fpack = theme.getMarketURL();
				long perMarketDown = logSearch.contentPerMarketDown(searcher, sdate, edate, marketKey, fpack);
				ccMarket.setMarketName(market.getName());
				ccMarket.setTotalDown(perMarketDown);
				ccMarket.setLogContent(lcc);
				logService.saveContentPerMarket(ccMarket);
			}
		}
	}

	private String escape(String marketKey) {
		if (marketKey != null && !marketKey.isEmpty()) {
			String[] strs = StringUtils.split(marketKey, ":");
			if (strs.length > 0) {
				return strs[0];
			}
		}
		return "";

	}

	@Autowired
	public void setLogJdbcDao(LogJdbcDao logJdbcDao) {
		this.logJdbcDao = logJdbcDao;
	}

	@Autowired
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Autowired
	public void setLogSearch(LogSearch logSearch) {
		this.logSearch = logSearch;
	}

	@Autowired
	public void setThemeDao(ThemeFileDao themeDao) {
		this.themeDao = themeDao;
	}

	@Autowired
	public void setMarketDao(MarketDao marketDao) {
		this.marketDao = marketDao;
	}

	@Autowired
	public void setLogHomeDao(LogInHomeDao logHomeDao) {
		this.logHomeDao = logHomeDao;
	}

}
