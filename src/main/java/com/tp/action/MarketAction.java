package com.tp.action;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.tp.dao.HibernateUtils;
import com.tp.entity.Market;
import com.tp.entity.ThemeFile;
import com.tp.service.FileManager;
import com.tp.service.MarketManager;
import com.tp.utils.Struts2Utils;

@Namespace("/category")
@Results({
		@Result(name = CRUDActionSupport.RELOAD, location = "market.action", type = "redirect"),
		@Result(name = "appear", location = "market!manage.action", params = { "checkedMarket", "${checkedMarket}" }, type = "redirect") })
public class MarketAction extends CRUDActionSupport<Market> {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Market entity;
	private List<Market> markets;
	private MarketManager marketManager;
	private FileManager fileManager;

	private Long checkedMarket;

	private List<Long> checkedFileIds;

	private List<ThemeFile> remainFiles = Lists.newArrayList();
	private List<ThemeFile> inMarketFiles = Lists.newArrayList();

	@Override
	@RequiresPermissions("store:edit")
	public String delete() throws Exception {
		marketManager.delete(id);
		return RELOAD;
	}

	@Override
	@RequiresPermissions("store:edit")
	public String input() throws Exception {

		return INPUT;
	}

	@Override
	@RequiresPermissions("store:view")
	public String list() throws Exception {
		markets = marketManager.getAll();
		return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id == null) {
			entity = new Market();
		} else {
			entity = marketManager.get(id);
		}

	}

	@Override
	@RequiresPermissions("store:edit")
	public String save() throws Exception {
		marketManager.save(entity);
		return RELOAD;
	}

	@RequiresPermissions("store:edit")
	public String appearOnMarket() throws Exception {
		entity = marketManager.get(checkedMarket);
		HibernateUtils.mergeByCheckedIds(entity.getThemes(), checkedFileIds, ThemeFile.class);
		marketManager.save(entity);
		addActionMessage("保存成功");
		return "appear";
	}

	public String checkMarketName() throws Exception {
		String oldName = new String(Struts2Utils.getParameter("oldName").getBytes("iso-8859-1"), "utf-8");
		String newName = new String(Struts2Utils.getParameter("name").getBytes("iso-8859-1"), "utf-8");
		if (marketManager.isNameUnique(newName, oldName)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;
	}

	@RequiresPermissions("store:edit")
	public String manage() throws Exception {

		return "manage";
	}

	public String fileInMarket() throws Exception {
		entity = marketManager.get(id);
		inMarketFiles = entity.getThemes();
		String json = fileManager.jsonString(inMarketFiles);
		Struts2Utils.renderJson(json);
		return null;
	}

	public String remainFile() throws Exception {
		entity = marketManager.get(id);
		inMarketFiles = entity.getThemes();
		List<ThemeFile> allThemeFiles = fileManager.getAllThemeFile();
		remainFiles = fileManager.getRemainFiles(allThemeFiles, inMarketFiles);
		String json = fileManager.jsonString(remainFiles);
		Struts2Utils.renderJson(json);
		return null;
	}

	@Override
	public Market getModel() {

		return entity;
	}

	public List<Market> getAllMarkets() {
		return marketManager.getAll();
	}

	public List<Market> getMarkets() {
		return markets;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ThemeFile> getRemainFiles() {
		return remainFiles;
	}

	public List<ThemeFile> getInMarketFiles() {
		return inMarketFiles;
	}

	public List<Long> getCheckedFileIds() {
		return checkedFileIds;
	}

	public void setCheckedFileIds(List<Long> checkedFileIds) {
		this.checkedFileIds = checkedFileIds;
	}

	public Long getCheckedMarket() {
		return checkedMarket;
	}

	public void setCheckedMarket(Long checkedMarket) {
		this.checkedMarket = checkedMarket;
	}

	@Autowired
	public void setMarketManager(MarketManager marketManager) {
		this.marketManager = marketManager;
	}

	@Autowired
	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}
}
