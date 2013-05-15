package com.tp.action;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.tp.entity.Shelf;
import com.tp.entity.ShelfFileLink;
import com.tp.entity.Store;
import com.tp.entity.ThemeFile;
import com.tp.service.CategoryManager;
import com.tp.service.FileManager;
import com.tp.service.ShelfFileLinkManager;
import com.tp.utils.Struts2Utils;

@Namespace("/category")
@Results({
		@Result(name = CRUDActionSupport.RELOAD, location = "shelf.action", type = "redirect"),
		@Result(name = "shelf-manage", location = "shelf!manage.action", params = { "id", "${selectId}" }, type = "redirect") })
public class ShelfAction extends CRUDActionSupport<Shelf> {

	private static final long serialVersionUID = 1L;
	private static final String MANAGE = "manage";
	private static final long noSelect = 0L;
	private Long id;
	private Shelf entity;
	private Long sid;
	private List<Long> checkedFileIds;
	private Long selectId;
	private CategoryManager categoryManager;
	private FileManager fileManager;
	private ShelfFileLinkManager shelfFileLinkManager;

	@Override
	@RequiresPermissions("store:edit")
	public String delete() throws Exception {
		categoryManager.deleteShelf(id);
		addActionMessage("删除成功");
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

		return SUCCESS;
	}

	public String filterShelf() throws Exception {
		Store store = categoryManager.getStore(sid);
		List<Shelf> shelfs = store.getShelfs();
		String json = categoryManager.jsonString(shelfs);
		Struts2Utils.renderJson(json);
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id == null) {
			entity = new Shelf();
		} else {
			entity = categoryManager.getShelf(id);
		}

	}

	@Override
	@RequiresPermissions("store:edit")
	public String save() throws Exception {
        if(id==null){
            Store store = categoryManager.getStore(sid);
            entity.setStore(store);
        }
		categoryManager.saveShelf(entity);
		addActionMessage("保存成功");
		return RELOAD;
	}

	@RequiresPermissions("store:edit")
	public String saveFile() {
		if (selectId == noSelect)
			return "";
		entity = categoryManager.getShelf(selectId);
		categoryManager.merge(entity, checkedFileIds);
		shelfFileLinkManager.mergeCheckedIds(entity, checkedFileIds);

		addActionMessage("保存成功");
		return "shelf-manage";
	}

	public String manage() throws Exception {

		return MANAGE;
	}

	public String getRemainFile() {
		if (id == noSelect)
			return "";

		Shelf shelf = categoryManager.getShelf(id);
		List<ThemeFile> onShelfFiles = getOnshelfThemes(shelf);
		List<ThemeFile> allFiles = Lists.newArrayList();
		allFiles.addAll(shelf.getStore().getThemes());

		List<ThemeFile> remainFiles = fileManager.getRemainFiles(allFiles, onShelfFiles);
		String json = fileManager.jsonString(remainFiles);
		Struts2Utils.renderJson(json);
		return null;
	}

	private List<ThemeFile> getOnshelfThemes(Shelf shelf) {
		List<ThemeFile> onShelfs = Lists.newArrayList();
		List<ShelfFileLink> shelfFiles = shelf.getShelfFile();
		for (ShelfFileLink sf : shelfFiles) {
			onShelfs.add(sf.getTheme());
		}
		return onShelfs;
	}

	public String getOnShelfFile() {
		if (id == noSelect)
			return "";
		Shelf shelf = categoryManager.getShelf(id);
		List<ThemeFile> onShelfFiles = getOnshelfThemes(shelf);
		String json = fileManager.jsonString(onShelfFiles);
		Struts2Utils.renderJson(json);
		return null;
	}

	public void prepareManage() throws Exception {
		prepareModel();
	}

	@Override
	public Shelf getModel() {

		return entity;
	}

	@Autowired
	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

	@Autowired
	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}

	@Autowired
	public void setShelfFileLinkManager(ShelfFileLinkManager shelfFileLinkManager) {
		this.shelfFileLinkManager = shelfFileLinkManager;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public List<Store> getAllStores() {
		return categoryManager.getAllStore();
	}

	public List<Long> getCheckedFileIds() {
		return checkedFileIds;
	}

	public void setCheckedFileIds(List<Long> checkedFileIds) {
		this.checkedFileIds = checkedFileIds;
	}

	public Long getSelectId() {
		return selectId;
	}

	public void setSelectId(Long selectId) {
		this.selectId = selectId;
	}
}
