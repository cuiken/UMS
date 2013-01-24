package com.tp.service;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tp.dao.FileInfoDao;
import com.tp.dao.FileStoreInfoDao;
import com.tp.dao.ThemeFileDao;
import com.tp.dao.ThemeThirdURLDao;
import com.tp.dto.FileDTO;
import com.tp.entity.FileInfo;
import com.tp.entity.FileMarketValue;
import com.tp.entity.FileStoreInfo;
import com.tp.entity.Market;
import com.tp.entity.Shelf;
import com.tp.entity.ThemeFile;
import com.tp.entity.ThemeThirdURL;
import com.tp.mapper.JsonMapper;
import com.tp.orm.Page;
import com.tp.orm.PropertyFilter;
import com.tp.utils.Constants;
import com.tp.utils.FileUtils;

@Component
@Transactional
public class FileManager {

	private FileInfoDao fileInfoDao;
	private ThemeFileDao themeFileDao;
	private FileStoreInfoDao storeInfoDao;
	private ThemeThirdURLDao thirdDao;

	public void saveThirdURL(ThemeThirdURL entity) {
		thirdDao.save(entity);
	}

	public FileInfo getFileInfo(Long id) {
		return fileInfoDao.get(id);
	}

	public List<ThemeFile> getAllThemeFile() {
		return themeFileDao.getAll();
	}

	public Page<ThemeFile> searchFileByShelf(final Page<ThemeFile> page, Shelf.Type stype, Long sid) {
		return themeFileDao.searchFileByShelf(page, stype.getValue(), sid);
	}

	public Page<FileStoreInfo> searchInfoByCategoryAndStore(final Page<FileStoreInfo> page, Long cid, Long sid,
			String lang) {
		return storeInfoDao.searchByCategoryAndStore(page, cid, sid, lang);
	}

	public Page<FileStoreInfo> searchByStore(final Page<FileStoreInfo> page, Long sid, String language) {
		return storeInfoDao.searchNewestByStore(page, sid, language);
	}

	public Page<FileStoreInfo> searchDiyTemplate(final Page<FileStoreInfo> page, Long sid, String language) {
		return storeInfoDao.searchDiyTemplate(page, sid, language);
	}

	/**
	 * 判断该条语言信息是否存在于商店中
	 * @param fiId 
	 * @return
	 */
	public boolean isInfoInStore(Long fiId) {
		return !getStoreInfoByFiId(fiId).isEmpty();
	}

	public List<FileStoreInfo> getStoreInfoByFiId(Long fiId) {
		return storeInfoDao.findBy("fiId", fiId);
	}

	public boolean isFileInfoUnique(Long fid, String language) {
		FileInfo info = fileInfoDao.findByFileIdAndLanguage(fid, language);
		if (info == null)
			return true;
		else
			return false;
	}

	public FileStoreInfo getStoreInfoBy(Long sid, Long fid, String language) {
		return storeInfoDao.get(sid, fid, language);
	}

	public Page<ThemeFile> searchThemeFile(final Page<ThemeFile> page, final List<PropertyFilter> filters) {
		return themeFileDao.findPage(page, filters);
	}

	public Page<ThemeFile> searchThemeFile(final Page<ThemeFile> page, Long categoryId) {
		return themeFileDao.searchFileByCategory(page, categoryId);
	}

	public Page<FileInfo> searchFileInfo(final Page<FileInfo> page, final List<PropertyFilter> filters) {
		return fileInfoDao.findPage(page, filters);
	}

	public Page<FileStoreInfo> searchStoreInfoInShelf(final Page<FileStoreInfo> page, Shelf.Type newest, Long sid,
			String language) {
		return storeInfoDao.searchStoreInfoInShelf(page, newest.getValue(), sid, language);
	}

	public ThemeFile saveFiles(List<File> files, ThemeFile fs, FileInfo info) {
		saveFiles(files, fs);
		saveFileinfo(fs, info);
		return fs;
	}

	public void saveFiles(List<File> files, ThemeFile theme) {

		for (File file : files) {
			String fname = FileUtils.getFileName(file.getName());
			String extension = FileUtils.getExtension(file.getName());
			if (FileUtils.isPreClient(fname)) {
				theme.setPreClientPath(file.getPath());
			} else if (FileUtils.isPreWeb(fname)) {
				theme.setPreWebPath(file.getPath());
			} else if (FileUtils.isAd(fname)) {
				theme.setAdPath(file.getPath());
			} else if (FileUtils.isIcon(fname)) {
				theme.setIconPath(file.getPath());
			} else if (FileUtils.isApk(extension)) {

				theme.setApkSize(FileUtils.getFileSize(file.getPath()));
				theme.setApkPath(file.getPath());
			} else if (FileUtils.isUx(extension)) {
				if (FileUtils.isHUx(fname)) {
					theme.setUxHvga(file.getPath());
				} else if (FileUtils.isWUx(fname)) {
					theme.setUxWvga(file.getPath());
				} else {
					theme.setUxPath(file.getPath());
				}

				theme.setUxSize(FileUtils.getFileSize(file.getPath()));
			}
		}
		saveThemeFile(theme);
	}

	private void saveFileinfo(ThemeFile f, FileInfo info) {
		info.setTheme(f);
		saveFileInfo(info);
	}

	public void saveFileInfo(FileInfo file) {
		fileInfoDao.save(file);
	}

	public ThemeFile getThemeFile(Long id) {
		return themeFileDao.get(id);
	}

	public void saveThemeFile(ThemeFile entity) {
		themeFileDao.save(entity);
	}

	public void deleteFileInfo(Long id) {
		fileInfoDao.delete(id);
	}

	public void deleteThemeFile(Long id) {
		themeFileDao.delete(id);
	}

	public List<ThemeFile> getRemainFiles(List<ThemeFile> allFiles, List<ThemeFile> fileOnShelf) {
		List<ThemeFile> remainFile = allFiles;
		for (ThemeFile fi : fileOnShelf) {

			remainFile.remove(fi);
		}
		return remainFile;
	}

	public FileStoreInfo getStoreInfo(Long id) {
		return storeInfoDao.get(id);
	}

	public void saveStoreInfo(FileStoreInfo entity) {
		storeInfoDao.save(entity);
	}

	public void deleteStoreInfo(Long id) {
		storeInfoDao.delete(id);
	}

	public void deleteStoreInfoByFmId(Long fid) {
		storeInfoDao.deleteByFileInfo(fid);
	}

	public List<FileStoreInfo> getThemeInfoByStore(Long tid, Long sid) {
		return storeInfoDao.getInfoByTheme(tid, sid);
	}

	public String jsonString(List<ThemeFile> themeFiles) {
		List<FileDTO> fileDtos = Lists.newArrayList();

		for (ThemeFile f : themeFiles) {
			FileDTO dto = new FileDTO();
			dto.setId(f.getId());
			dto.setName(f.getTitle());
			fileDtos.add(dto);
		}
		JsonMapper mapper = JsonMapper.buildNormalMapper();
		return mapper.toJson(fileDtos);

	}

	public boolean isFileTitleUnique(String newTitle, String oldTitle) {
		return themeFileDao.isPropertyUnique("title", newTitle, oldTitle);
	}

	public String adXml(List<ThemeFile> themes, String domain, String linkURL) throws Exception {
		StringBuilder buffer = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		if (themes.size() > 5) {
			themes = themes.subList(0, 5);
		}

		buffer.append("<ads>");
		for (ThemeFile theme : themes) {
			Long id = theme.getId();
			String ad = theme.getAdPath();
			if (ad == null || ad.isEmpty()) {
				continue;
			}
			String[] items = StringUtils.split(ad, File.separator);
			String adName = items[items.length - 1];
			String[] exts = StringUtils.split(adName, Constants.DOT_SEPARATOR);
			buffer.append("<ad id=\"" + id + "\"");
			buffer.append(" fileName=\"" + adName + "\"");
			buffer.append(" format=\"" + exts[exts.length - 1] + "\"");
			buffer.append(" version=\"1\"");
			buffer.append(">");
			buffer.append("<linkUrl>" + domain + linkURL + id + "&amp;f=ad</linkUrl>");
			buffer.append("<downloadUrl>" + domain + "/image.action?path=" + URLEncoder.encode(ad, "UTF-8")
					+ "</downloadUrl>");
			buffer.append("</ad>");
		}
		buffer.append("</ads>");
		return buffer.toString();
	}

	public String gadXml(List<ThemeFile> themes, String domain, Market market) throws Exception {
		StringBuilder buffer = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		if (themes.size() > 5) {
			themes = themes.subList(0, 5);
		}

		buffer.append("<ads>");
		for (ThemeFile theme : themes) {
			setDownloadType(market, theme);
			Long id = theme.getId();
			String ad = theme.getAdPath();
			if (ad == null || ad.isEmpty()) {
				continue;
			}
			String[] items = StringUtils.split(ad, File.separator);
			String adName = items[items.length - 1];
			String[] exts = StringUtils.split(adName, Constants.DOT_SEPARATOR);
			buffer.append("<ad id=\"" + id + "\"");
			buffer.append(" fileName=\"" + adName + "\"");
			buffer.append(" format=\"" + exts[exts.length - 1] + "\"");
			buffer.append(" version=\"1\"");
			buffer.append(">");
			buffer.append("<linkUrl>" + theme.getDownloadURL() + "</linkUrl>");
			buffer.append("<downloadUrl>" + domain + "/image.action?path=" + URLEncoder.encode(ad, "UTF-8")
					+ "</downloadUrl>");
			buffer.append("</ad>");
		}
		buffer.append("</ads>");
		return buffer.toString();
	}

	public ThemeFile setDownloadType(Market market, ThemeFile theme) {
		List<ThemeFile> files = market.getThemes();
		if (files.contains(theme)) {
			String uri = market.getMarketKey() + theme.getMarketURL();
			if (market.getPkName().equals(Constants.LENVOL_STORE)) {
				uri += ("&versioncode=" + theme.getVersion());
			}
			if (market.getPkName().equals(Constants.OPPO_NEARME)) {
				List<FileMarketValue> fvs = theme.getMarketValues();
				for (FileMarketValue fm : fvs) {
					uri += (fm.getKeyName() + "=" + fm.getKeyValue());
				}
			}
			theme.setDownloadURL(uri);
		}
		return theme;
	}

	@Autowired
	public void setFileInfoDao(FileInfoDao fileInfoDao) {
		this.fileInfoDao = fileInfoDao;
	}

	@Autowired
	public void setThemeFileDao(ThemeFileDao themeFileDao) {
		this.themeFileDao = themeFileDao;
	}

	@Autowired
	public void setStoreInfoDao(FileStoreInfoDao storeInfoDao) {
		this.storeInfoDao = storeInfoDao;
	}

	@Autowired
	public void setThirdDao(ThemeThirdURLDao thirdDao) {
		this.thirdDao = thirdDao;
	}
}
