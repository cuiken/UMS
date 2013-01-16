package com.tp.search;

import static com.tp.utils.Constants.METHOD_DETAILS;
import static com.tp.utils.Constants.METHOD_EXECUTE;
import static com.tp.utils.Constants.METHOD_GET_CLIENT;

import org.apache.lucene.search.IndexSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogSearch {

	private ISearch logSearch;

	/**
	 * 查询商店访问量
	 */
	public Long storeVisits(IndexSearcher searcher, String sdate, String edate) throws Exception {

		return logSearch.countByMethod(searcher, METHOD_EXECUTE, sdate, edate);
	}

	/**
	 * 分享下载客户端量
	 */
	public Long downloadByShare(IndexSearcher searcher, String sdate, String edate) throws Exception {
		String param = "share";
		return logSearch.countByMethodAndParams(searcher, METHOD_GET_CLIENT, sdate, edate, param);

	}

	/**
	 * 从内容下载客户端量
	 */
	public Long downloadByContent(IndexSearcher searcher, String sdate, String edate) throws Exception {
		String param = "cv";
		return logSearch.countByMethodAndParams(searcher, METHOD_GET_CLIENT, sdate, edate, param);

	}

	/**
	 * 文件内容总访问量
	 */
	public Long contentVisits(IndexSearcher searcher, String fid, String sdate, String edate) throws Exception {

		return logSearch.countByMethodAndParams(searcher, METHOD_DETAILS, sdate, edate, fid);
	}

	/**
	 * 文件内容广告引导访问量
	 */

	public Long contentVisitByAD(IndexSearcher searcher, String fid, String sdate, String edate) throws Exception {

		String p1 = "ad";
		String p2 = fid;
		return logSearch.countByMethodAndParams(searcher, METHOD_DETAILS, sdate, edate, p1, p2);
	}

	/**
	 * 文件自由下载量
	 */
	public Long contentDownFromStore(IndexSearcher searcher, String fid, String sdate, String edate) throws Exception {
		String method = "file-download.action";

		return logSearch.countByMethodAndParams(searcher, method, sdate, edate, fid);
	}

	/**
	 * 文件对应的market访问量(下载量)
	 */
	public Long contentDownFromMarket(IndexSearcher searcher, String filePackage, String sdate, String edate)
			throws Exception {

		return logSearch.countByParam(searcher, sdate, edate, filePackage);
	}

	/**
	 * 文件在每个市场的下载量
	 */
	public Long contentPerMarketDown(IndexSearcher searcher, String sdate, String edate, String... params)
			throws Exception {

		return logSearch.countByParam(searcher, sdate, edate, params);

	}

	public Long countGetClientByContent(IndexSearcher searcher, String sdate, String edate, String ...params)
			throws Exception {
		return logSearch.countByMethodAndParamsIK(searcher, METHOD_GET_CLIENT, sdate, edate, params);
	}

	@Autowired
	public void setLogSearch(ISearch logSearch) {
		this.logSearch = logSearch;
	}
}
