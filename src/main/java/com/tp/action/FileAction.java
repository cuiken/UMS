package com.tp.action;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.tp.dao.HibernateUtils;
import com.tp.entity.Category;
import com.tp.entity.FileInfo;
import com.tp.entity.Store;
import com.tp.entity.ThemeFile;
import com.tp.entity.ThemeThirdURL;
import com.tp.orm.Page;
import com.tp.orm.PropertyFilter;
import com.tp.orm.PageRequest.Sort;
import com.tp.service.CategoryManager;
import com.tp.service.FileInfoObservable;
import com.tp.service.FileManager;
import com.tp.utils.Constants;
import com.tp.utils.DateFormatUtils;
import com.tp.utils.FileUtils;
import com.tp.utils.Struts2Utils;

@Namespace("/file")
@Results({ @Result(name = CRUDActionSupport.RELOAD, location = "file.action", params = { "page.pageNo",
		"${page.pageNo}" }, type = "redirect") })
public class FileAction extends CRUDActionSupport<ThemeFile> {

	private static final long serialVersionUID = 1L;
	private ThemeFile entity;
	private Long id;
	private List<Long> checkedCategoryIds;
	private List<Long> checkedStoreIds;
	private Page<ThemeFile> page = new Page<ThemeFile>();
	private List<FileInfo> fileInfo;
	private FileManager fileManager;
	private CategoryManager categoryManager;

	private List<Integer> sliders = Lists.newArrayList();
	private File file;

	@Override
	@RequiresPermissions("file:edit")
	public String delete() throws Exception {
		fileManager.deleteThemeFile(id);
		addActionMessage("删除成功");
		return RELOAD;
	}

	@Override
	@RequiresPermissions("file:edit")
	public String input() throws Exception {
		checkedCategoryIds = entity.getCheckedCategoryIds();
		checkedStoreIds = entity.getCheckedStoreIds();
		return INPUT;
	}

	@Override
	@RequiresPermissions("file:view")
	public String list() throws Exception {

		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());
		if (!page.isOrderBySetted()) {
			page.setOrderBy("createTime");
			page.setOrderDir(Sort.DESC);
		}
		page = fileManager.searchThemeFile(page, filters);
		sliders = page.getSlider(10);
		return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id == null) {
			entity = new ThemeFile();
		} else {
			entity = fileManager.getThemeFile(id);
		}

	}

	@Override
	@RequiresPermissions("file:edit")
	public String save() throws Exception {
		HibernateUtils.mergeByCheckedIds(entity.getCategories(), checkedCategoryIds, Category.class);
		HibernateUtils.mergeByCheckedIds(entity.getStores(), checkedStoreIds, Store.class);
		List<File> files = copyNewFile(file, entity.getApkPath());

		entity.setModifyTime(DateFormatUtils.convert(new Date()));
		fileManager.saveFiles(files, entity);
		for (FileInfo info : entity.getFileInfo()) {
			info.setTheme(entity);
			FileInfoObservable observer = new FileInfoObservable();
			observer.setFileManager(fileManager);
			observer.saveFileInfo(info);
		}
		saveThirdURL(entity);
		addActionMessage("保存成功");
		return RELOAD;
	}

	private ThemeThirdURL saveThirdURL(ThemeFile theme) {
		String cmURL = Struts2Utils.getParameter("cmURL");
		String cuURL = Struts2Utils.getParameter("cuURL");
		String ctURL = Struts2Utils.getParameter("ctURL");
		if (cmURL.isEmpty() && cuURL.isEmpty() && ctURL.isEmpty())
			return null;
		List<ThemeThirdURL> thirds = entity.getThirdURLs();
		ThemeThirdURL third;
		if (thirds == null || thirds.isEmpty()) {
			third = new ThemeThirdURL();
		} else {
			third = thirds.get(0);
		}
		third.setCmURL(cmURL);
		third.setCtURL(ctURL);
		third.setCuURL(cuURL);
		third.setTheme(theme);
		fileManager.saveThirdURL(third);
		return third;
	}

	private List<File> copyNewFile(File newTheme, String oldPath) throws Exception {
		List<File> files = Lists.newArrayList();
		if (newTheme != null) {
			files = FileUtils.unZip(newTheme, Constants.LOCKER_STORAGE);
			if(oldPath==null)
				return files;
			String path = oldPath;
			String[] ps = StringUtils.split(path, File.separator);
			if (ps.length > 0) {
				String folderName = ps[0];
				File themebase = new File(Constants.LOCKER_STORAGE, folderName);

				org.apache.commons.io.FileUtils.deleteDirectory(themebase);

			}
		}
		return files;
	}

	public String checkTitle() throws Exception {

		String oldTitle = new String(Struts2Utils.getParameter("oldTitle").getBytes("iso-8859-1"), "utf-8");
		String newTitle = new String(Struts2Utils.getParameter("title").getBytes("iso-8859-1"), "utf-8");
		if (fileManager.isFileTitleUnique(newTitle, oldTitle)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;
	}

	@Override
	public ThemeFile getModel() {

		return entity;
	}

	public List<Category> getAllCategoryList() {
		return categoryManager.getCategories();
	}

	public List<Store> getAllStore() {
		return categoryManager.getAllStore();
	}

	@Autowired
	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}

	@Autowired
	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

	public Page<ThemeFile> getPage() {
		return page;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Long> getCheckedCategoryIds() {
		return checkedCategoryIds;
	}

	public void setCheckedCategoryIds(List<Long> checkedCategoryIds) {
		this.checkedCategoryIds = checkedCategoryIds;
	}

	public List<Long> getCheckedStoreIds() {
		return checkedStoreIds;
	}

	public void setCheckedStoreIds(List<Long> checkedStoreIds) {
		this.checkedStoreIds = checkedStoreIds;
	}

	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(List<FileInfo> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public List<Integer> getSliders() {
		return sliders;
	}

}
