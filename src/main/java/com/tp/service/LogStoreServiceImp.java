package com.tp.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tp.dao.MarketDao;
import com.tp.entity.log.LogForStore;
import com.tp.utils.Struts2Utils;

@Component
@Transactional
public class LogStoreServiceImp implements LogStoreService{

	private static final String FILE_DOWNLOAD="file-download";
	private static final String MARKET_DOWNLOAD="market_download";
	
	private MarketDao marketDao;
	
	@Override
	public Long storeVisits(String sdate, String edate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long downloadByShare(String sdate, String edate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long downloadByContent(String sdate, String edate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> contentVisits(String sdate, String edate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> contentVisitByAD(String sdate, String edate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> contentDownFromStore(String sdate, String edate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long contentDownFromMarket(String filePackage, String sdate, String edate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long contentPerMarketDown(String filePackage, String marketKey, String sdate, String edate) {
		// TODO Auto-generated method stub
		return null;
	}

	private void marketOrDown(String url){
		LogForStore log=new LogForStore();
		if(StringUtils.startsWith(url, FILE_DOWNLOAD)){
			String id=Struts2Utils.getParameter("id");
			log.setRequestMethod(FILE_DOWNLOAD);
			log.setFileId(id);
		}else{
			log.setRequestMethod(MARKET_DOWNLOAD);
			
		}
	}
}
