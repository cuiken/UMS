package com.tp.action.nav;

import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.tp.entity.nav.SearchLog;
import com.tp.service.nav.SearchLogService;
import com.tp.utils.Struts2Utils;

public class SearchLogAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private static final String BAIDU = "http://m.baidu.com/s?from=1000660a&word=";
	private static final String ZHIDAO = "http://wapiknow.baidu.com/index?from=1000660a&word=";
	private String type;
	private String word;
	private SearchLogService searchLogService;

	public String search() throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, String> users = (Map<String, String>) Struts2Utils.getSessionAttribute("users");
		String imei = users.get("imei");
		if (StringUtils.isBlank(imei)){
			imei=Struts2Utils.getParameter("imei");
		}

		SearchLog entity = new SearchLog();
		entity.setImei(imei);
		entity.setKeyWord(word);
		entity.setSearchType(type);
		searchLogService.save(entity);
		if (type.equals("baidu"))
			Struts2Utils.getResponse().sendRedirect(BAIDU + URLEncoder.encode(word, "UTF-8"));
		else
			Struts2Utils.getResponse().sendRedirect(ZHIDAO + URLEncoder.encode(word, "UTF-8"));
		return null;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Autowired
	public void setSearchLogService(SearchLogService searchLogService) {
		this.searchLogService = searchLogService;
	}
}
