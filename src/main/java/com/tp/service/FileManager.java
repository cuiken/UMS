package com.tp.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.tp.dao.MarketDao;
import com.tp.dto.HtmlDTO;
import com.tp.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tp.dao.FileInfoDao;
import com.tp.dao.ThemeFileDao;
import com.tp.dao.ThemeThirdURLDao;
import com.tp.dto.FileDTO;
import com.tp.mapper.JsonMapper;
import com.tp.orm.Page;
import com.tp.orm.PropertyFilter;
import com.tp.utils.Constants;
import com.tp.utils.FileUtils;

import javax.servlet.http.HttpSession;

@Component
@Transactional
public class FileManager {

	private FileInfoDao fileInfoDao;
	private ThemeFileDao themeFileDao;
	private ThemeThirdURLDao thirdDao;
    private MarketDao marketDao;

	public void saveThirdURL(ThemeThirdURL entity) {
		thirdDao.save(entity);
	}

	public FileInfo getFileInfo(Long id) {
		return fileInfoDao.get(id);
	}
	
	public FileInfo getFileInfoByFileAndLanguage(Long fid,String language){
		return fileInfoDao.findByFileIdAndLanguage(fid, language);
	}

	public List<ThemeFile> getAllThemeFile() {
		return themeFileDao.getAll();
	}

	public Page<ThemeFile> searchFileByShelf(final Page<ThemeFile> page, Shelf.Type stype, Long sid) {
		return themeFileDao.searchFileByShelf(page, stype.getValue(), sid);
	}

	public Page<FileInfo> searchInfoByCategoryAndStore(final Page<FileInfo> page, Long cid, Long sid, String lang) {
		return fileInfoDao.searchByCategoryAndStore(page, cid, sid, lang);
	}

//	public Page<FileInfo> searchByStore(final Page<FileInfo> page, Long sid, String language) {
//		return fileInfoDao.searchNewestByStore(page, sid, language);
//	}
//
//	public Page<FileInfo> searchDiyTemplate(final Page<FileInfo> page, Long sid, String language) {
//		return fileInfoDao.searchDiyTemplate(page, sid, language);
//	}

	public boolean isFileInfoUnique(Long fid, String language) {
		FileInfo info = fileInfoDao.findByFileIdAndLanguage(fid, language);
		if (info == null)
			return true;
		else
			return false;
	}

	public Page<ThemeFile> searchThemeFile(final Page<ThemeFile> page, final List<PropertyFilter> filters) {
		return themeFileDao.findPage(page, filters);
	}

//	public Page<ThemeFile> searchThemeFile(final Page<ThemeFile> page, Long categoryId) {
//		return themeFileDao.searchFileByCategory(page, categoryId);
//	}
//
//	public Page<FileInfo> searchFileInfo(final Page<FileInfo> page, final List<PropertyFilter> filters) {
//		return fileInfoDao.findPage(page, filters);
//	}

	public Page<FileInfo> searchStoreInfoInShelf(final Page<FileInfo> page, String shelf, Long sid, String language) {
		return fileInfoDao.searchStoreInfoInShelf(page, shelf, sid, language);
	}

	public Page<FileInfo> searchTopicFile(final Page<FileInfo> page, Long topicId, String language) {
		return fileInfoDao.searchStoreInfoByTopic(page, topicId, language);
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

    public String getHottestJson(List<Map<String, Object>> contents,String queryString){
        List<HtmlDTO> dtos=Lists.newArrayList();
        for (Map<String, Object> theme : contents) {
            HtmlDTO dto=new HtmlDTO();
            dto.setId((Integer)theme.get("f_id"));
            dto.setIsnew((Integer) theme.get("isnew"));
            dto.setIshot((Integer) theme.get("ishot"));
            dto.setIconPath((String) theme.get("iconPath"));
            dto.setTitle((String) theme.get("title"));
            dto.setShortDescription((String) theme.get("shortDescription"));
            dto.setQueryString(queryString);
            dto.setResourceBundle(Constants.getResourceBundle());
            dto.setUrl("file-download.action?inputPath=" + theme.get("apk_path") + "&title=" + theme.get("title")
                    + "|" + theme.get("title"));
            dtos.add(dto);
        }
        return list2Json(dtos);
    }

    public String getFileInfoPageJson(List<FileInfo> infos,HttpSession session) throws Exception{
        String queryString = (String) session.getAttribute("queryString");
        List<HtmlDTO> dtos=Lists.newArrayList();
        for (FileInfo info : infos) {
            ThemeFile theme=info.getTheme();
            HtmlDTO dto=new HtmlDTO();
            dto.setId(theme.getId().intValue());
            dto.setIsnew(theme.getIsnew().intValue());
            dto.setIshot(theme.getIshot().intValue());
            dto.setIconPath(theme.getIconPath());
            dto.setTitle(info.getTitle());
            dto.setShortDescription(info.getShortDescription());
            dto.setQueryString(queryString);
            dto.setResourceBundle(Constants.getResourceBundle());
            setDownloadType(session, info.getTheme().getCategories().get(0).getDescription(), info);
            dto.setUrl(theme.getDownloadURL());
            dtos.add(dto);
        }
        return list2Json(dtos);
    }

    private String list2Json(List<HtmlDTO> dtos){
        StringBuilder buffer = new StringBuilder();
        buffer.append("{");
        if (dtos.size() < 10) {
            buffer.append("\"code\":900");
        } else {
            buffer.append("\"code\":200");
        }
        buffer.append(",\"data\":\"");
        for(HtmlDTO dto : dtos){
            loopHtml(buffer,dto);
        }
        buffer.append("\"}");
        return buffer.toString();
    }

    private void loopHtml(StringBuilder buffer, HtmlDTO html) {

        buffer.append("<li>");
        buffer.append("<div class=\\\"icon\\\"><img src=\\\"/UMS/image.action?path=" + html.getIconPath()
                + "\\\"></div>");
        buffer.append(" <div class=\\\"y-split\\\"></div>");
        buffer.append(" <div class=\\\"info\\\">" + "        <p class=\\\"title\\\">" + html.getTitle() + "</p>"
                + "        <p class=\\\"txt\\\">" + html.getShortDescription() + "</p>"
                + "        <div class=\\\"y-split right\\\"></div>" + "        <div class=\\\"down-btn\\\">"
                + "        <a href=\\\"#\\\" onclick=\\\"goDownload('" + html.getId() + "','" + html.getUrl()
                + "')\\\">" + "            <img src=\\\"static/images/2.0/down.png\\\">" + "            <span>"
                + html.getResourceBundle().getString("home.down") + "</span>" + "        </a>" + "        </div>"
                + "    </div>");
        if (html.getIshot() == 1) {
            buffer.append("<span class=\\\"icon_n\\\"></span>");
        } else if (html.getIsnew() == 1) {
            buffer.append("<span class=\\\"icon_n icon_n_new\\\"></span>");
        }

        buffer.append("<a href=\\\"home!details.action?id=" + html.getId() + "&" + html.getQueryString()
                + "\\\" class=\\\"down-area\\\"></a>");
        buffer.append("</li>");
    }

    public void setDownloadType(HttpSession session, String category, FileInfo fsi)
            throws UnsupportedEncodingException {

        String fromMarket = (String) session.getAttribute(Constants.PARA_FROM_MARKET);
        String downType = (String) session.getAttribute(Constants.PARA_DOWNLOAD_METHOD);
        StringBuilder httpBuffer = new StringBuilder();
        if (category.contains("other")) {
            httpBuffer.append("browerhttp://" + StringUtils.remove(Constants.getDomain(), "http://") + "/");
        }
        httpBuffer.append("file-download.action?id=");
        httpBuffer.append(fsi.getTheme().getId());
        httpBuffer.append("&inputPath=");
        if (fsi.getTheme().getDtype().equals("1")) {
            httpBuffer.append(URLEncoder.encode(fsi.getTheme().getUxPath(), "utf-8"));
        } else {
            httpBuffer.append(URLEncoder.encode(fsi.getTheme().getApkPath(), "utf-8"));
        }

        httpBuffer.append("&title=" + URLEncoder.encode(fsi.getTitle(), "utf-8"));
        httpBuffer.append(URLEncoder.encode("|", "utf-8"))
                .append(URLEncoder.encode(fsi.getTheme().getTitle(), "utf-8"));
        httpBuffer.append("&");
        if (downType.equals(DownloadType.MARKET.getValue())) {
            marketDownload(fromMarket, httpBuffer.toString(), fsi);
        } else {
            fsi.getTheme().setDownloadURL(httpBuffer.toString());
        }
    }

    private void marketDownload(String fromMarket, String http, FileInfo fsi) {
        Market market = marketDao.findUniqueBy("pkName", fromMarket);
        if (market == null || market.getMarketKey().isEmpty()) {
            fsi.getTheme().setDownloadURL(http);
        } else {
            fileInMarket(market, http, fsi);
        }
    }

    private void fileInMarket(Market market, String http, FileInfo fsi) {
        List<ThemeFile> files = market.getThemes();
        if (files.contains(fsi.getTheme())) {
            String uri = market.getMarketKey() + fsi.getTheme().getMarketURL();
            if (market.getPkName().equals(Constants.LENVOL_STORE)) {
                uri += ("&versioncode=" + fsi.getTheme().getVersion());
            }
            if (market.getPkName().equals(Constants.OPPO_NEARME)) {
                List<FileMarketValue> fvs = fsi.getTheme().getMarketValues();
                for (FileMarketValue fm : fvs) {
                    if (market.getId().equals(fm.getMarket().getId())) {
                        uri += "&" + (fm.getKeyName() + "=" + fm.getKeyValue());
                    }
                }
            }
            fsi.getTheme().setDownloadURL(uri);
        } else {
            fsi.getTheme().setDownloadURL(http);
        }
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

	public List<FileInfo> shuffInfos(List<FileInfo> originInfos) {
		List<FileInfo> correct = Lists.newArrayList();
		Collections.shuffle(originInfos);
		FileInfo firstWeight = getWeightInfo(originInfos);
		correct.add(firstWeight);
		originInfos.remove(firstWeight);
		FileInfo secondWeight = getWeightInfo(originInfos);
		correct.add(secondWeight);
		return correct;
	}

	private FileInfo getWeightInfo(List<FileInfo> infos) {
		int random = getRandom(getSum(infos));
		long weight = 0;
		for (FileInfo info : infos) {
			weight += info.getTheme().getPercent();
			if (weight >= random) {
				return info;
			}
		}
		return null;
	}

	private int getSum(List<FileInfo> infos) {
		int sum = 0;
		for (FileInfo info : infos) {
			sum += info.getTheme().getPercent();
		}
		return sum;
	}

	private int getRandom(int seed) {
		return (int) Math.round(Math.random() * seed);
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
	public void setThirdDao(ThemeThirdURLDao thirdDao) {
		this.thirdDao = thirdDao;
	}

    @Autowired
    public void setMarketDao(MarketDao marketDao) {
        this.marketDao = marketDao;
    }
}
