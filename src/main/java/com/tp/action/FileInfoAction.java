package com.tp.action;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.tp.entity.FileInfo;
import com.tp.entity.ThemeFile;
import com.tp.service.FileManager;

@Namespace("/file")
@Results({ @Result(name = CRUDActionSupport.RELOAD, location = "file-info.action", params = { "themeId", "${themeId}" }, type = "redirect") })
public class FileInfoAction extends CRUDActionSupport<FileInfo> {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long themeId;
	private FileInfo entity;

	private FileManager fileManager;

	@Override
	@RequiresPermissions("file:edit")
	public String delete() throws Exception {
		fileManager.deleteFileInfo(id);
		return RELOAD;
	}

	@Override
	@RequiresPermissions("file:edit")
	public String input() throws Exception {

		return INPUT;
	}

	@Override
	@RequiresPermissions("file:view")
	public String list() throws Exception {

		return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id == null) {
			entity = new FileInfo();
		} else {
			entity = fileManager.getFileInfo(id);
		}

	}

	@Override
	@RequiresPermissions("file:edit")
	public String save() throws Exception {
		ThemeFile file = fileManager.getThemeFile(themeId);
		if (fileManager.isFileInfoUnique(themeId, entity.getLanguage())) {
			entity.setTheme(file);
			fileManager.saveFileInfo(entity);
			addActionMessage("保存成功");
		} else {
			addActionMessage("改语言信息已存在");
		}

		return RELOAD;
	}

	@Override
	public FileInfo getModel() {

		return entity;
	}

	@Autowired
	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getThemeId() {
		return themeId;
	}

	public void setThemeId(Long themeId) {
		this.themeId = themeId;
	}

	public List<FileInfo> getAllInfo() {
		ThemeFile file = fileManager.getThemeFile(themeId);
		return file.getFileInfo();
	}

}
