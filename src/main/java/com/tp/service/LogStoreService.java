package com.tp.service;

import java.util.List;
import java.util.Map;

public interface LogStoreService {

	/**
	 * 查询商店访问量
	 */
	public Long storeVisits(String sdate, String edate);

	/**
	 * 分享下载客户端量
	 */
	public Long downloadByShare(String sdate, String edate);

	/**
	 * 内容引导下载客户端量
	 */
	public Long downloadByContent(String sdate, String edate);

	/**
	 * 文件内容总访问量
	 */
	public List<Map<String, Object>> contentVisits(String sdate, String edate);

	/**
	 * 文件内容广告引导访问量
	 */
	public List<Map<String, Object>> contentVisitByAD(String sdate, String edate);

	/**
	 * 文件自有下载量
	 */
	public List<Map<String, Object>> contentDownFromStore(String sdate, String edate);

	/**
	 * 文件对应的market访问量(下载量)
	 */
	public Long contentDownFromMarket(String filePackage, String sdate, String edate);

	/**
	 * 文件在每个市场的下载量
	 */
	public Long contentPerMarketDown(String filePackage,String marketKey,String sdate, String edate);

}
