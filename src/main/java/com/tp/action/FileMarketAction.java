package com.tp.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.tp.entity.FileMarketValue;
import com.tp.entity.Market;
import com.tp.entity.ThemeFile;
import com.tp.service.FileManager;
import com.tp.service.FileMarketManager;
import com.tp.service.MarketManager;

@Namespace("/category")
@Results({ @Result(name = CRUDActionSupport.RELOAD, location = "file-market.action", params = { "themeId",
		"${themeId}", "marketId", "${marketId}" }, type = "redirect") })
public class FileMarketAction extends CRUDActionSupport<FileMarketValue> {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long themeId;
	private Long marketId;
	private FileMarketManager fileMarketManager;
	private FileManager fileManager;
	private MarketManager marketManager;
	private FileMarketValue entity;
	private List<FileMarketValue> fileMarketValues;

	@Override
	public FileMarketValue getModel() {

		return entity;
	}

	@Override
	public String list() throws Exception {
		fileMarketValues = fileMarketManager.getByFileAndMarket(themeId, marketId);
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {

		return INPUT;
	}

	@Override
	public String save() throws Exception {
		Market market = marketManager.get(marketId);
		ThemeFile theme = fileManager.getThemeFile(themeId);
		entity.setMarket(market);
		entity.setTheme(theme);
		fileMarketManager.save(entity);
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		fileMarketManager.delete(id);
		return RELOAD;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id == null) {
			entity = new FileMarketValue();
		} else {
			entity = fileMarketManager.get(id);
		}

	}

	@Autowired
	public void setFileMarketManager(FileMarketManager fileMarketManager) {
		this.fileMarketManager = fileMarketManager;
	}

	@Autowired
	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}

	@Autowired
	public void setMarketManager(MarketManager marketManager) {
		this.marketManager = marketManager;
	}

	public List<FileMarketValue> getFileMarketValues() {
		return fileMarketValues;
	}

	public Long getThemeId() {
		return themeId;
	}

	public void setThemeId(Long themeId) {
		this.themeId = themeId;
	}

	public Long getMarketId() {
		return marketId;
	}

	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
