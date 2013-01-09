package com.tp.action;

import java.io.File;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.tp.entity.ClientFile;
import com.tp.entity.ClientType;
import com.tp.entity.Market;
import com.tp.service.CategoryManager;
import com.tp.service.ClientFileManager;
import com.tp.service.MarketManager;

@Namespace("/file")
@Results({ @Result(name = CRUDActionSupport.RELOAD, location = "funlocker-client.action", type = "redirect") })
public class FunlockerClientAction extends CRUDActionSupport<ClientFile> {

	private static final long serialVersionUID = 1L;

	private Long id;
	private ClientFile entity;
	private List<ClientFile> clientFiles;
	private ClientFileManager clientFileManager;
	private MarketManager marketManager;
	private CategoryManager categoryManager;

	private File file;
	private String uploadFileName;

	@Override
	public ClientFile getModel() {

		return entity;
	}

	@Override
	public String list() throws Exception {
		clientFiles = clientFileManager.getAll();
		return SUCCESS;
	}

	@Override
	@RequiresPermissions("file:edit")
	public String input() throws Exception {

		return INPUT;
	}

	@Override
	@RequiresPermissions("file:edit")
	public String save() throws Exception {

		clientFileManager.save(entity);
		return RELOAD;
	}

	@Override
	@RequiresPermissions("file:edit")
	public String delete() throws Exception {
		clientFileManager.delete(id);
		return RELOAD;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id == null) {
			entity = new ClientFile();
		} else {
			entity = clientFileManager.get(id);
		}

	}

	public List<ClientFile> getClientFiles() {
		return clientFiles;
	}

	public List<Market> getAllMarkets() {
		return marketManager.getAll();
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ClientType> getTypes() {
		return categoryManager.getClientTypes();
	}

	@Autowired
	public void setClientFileManager(ClientFileManager clientFileManager) {
		this.clientFileManager = clientFileManager;
	}

	@Autowired
	public void setMarketManager(MarketManager marketManager) {
		this.marketManager = marketManager;
	}

	@Autowired
	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

}
