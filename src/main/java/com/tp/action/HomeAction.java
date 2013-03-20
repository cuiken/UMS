package com.tp.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tp.dao.log.LogJdbcDao;
import com.tp.entity.*;
import com.tp.orm.PropertyFilter;
import com.tp.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.tp.dao.log.LogCountContentDao;
import com.tp.orm.Page;
import com.tp.utils.Constants;
import com.tp.utils.ServletUtils;
import com.tp.utils.Struts2Utils;

@Results({ @Result(name = "reload", location = "home.action", type = "redirect") })
public class HomeAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());

	private CategoryManager categoryManager;
	private CategoryInfoManager categoryInfoManager;
	private FileManager fileManager;
	private MarketManager marketManager;
    private AdvertisementService advertisementService;

	private LogCountContentDao countContentDao;
    private LogJdbcDao logJdbcDao;

	private Page<FileStoreInfo> hottestPage = new Page<FileStoreInfo>();
    private Page<Advertisement> advertisementPage=new Page<Advertisement>();

	private Page<FileStoreInfo> newestPage = new Page<FileStoreInfo>();
	private Page<FileStoreInfo> catePage = new Page<FileStoreInfo>();

	private Long id;
    private Long pageNo=1L;
	private String totalDown;
	private FileStoreInfo info;

	private List<CategoryInfo> cateInfos;
	private Long categoryId;

	private String categoryName;
	private String language;
	private List<Category> categories;

    private String bars;

    private List<Map<String,Object>> sorts;

	@Override
	public String execute() throws Exception {

		return list();
	}

	/**
	 * 商店首页显示列表
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {

		HttpSession session = Struts2Utils.getSession();

		language = (String) session.getAttribute(Constants.PARA_LANGUAGE);
		Long storeId = chooseStoreId(session);

		hottestPage.setPageSize(16);
		hottestPage = fileManager.searchStoreInfoInShelf(hottestPage, Shelf.Type.HOTTEST, storeId, language);

		newestPage = fileManager.searchStoreInfoInShelf(newestPage, Shelf.Type.NEWEST, storeId, language);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());
        filters.add(new PropertyFilter("EQS_dtype", "store"));
        filters.add(new PropertyFilter("EQS_store", Constants.ST_LOCK));
        filters.add(new PropertyFilter("EQL_status","1"));
        advertisementPage=advertisementService.searchAdvertisement(advertisementPage,filters);
        bars=json(advertisementPage.getResult());
		return SUCCESS;
	}

    private String json(List<Advertisement> ads){
        StringBuilder buffer=new StringBuilder();
        buffer.append("[");
        for(Advertisement ad:ads){
            buffer.append("{");
            buffer.append("\"pic\":\""+Constants.getDomain()+"/image.action?path="+ad.getImgLink()+"\"");
            buffer.append(",");
            buffer.append("\"href\":\""+ad.getLink()+"\"");
            buffer.append(",");
            buffer.append("\"ext\":"+true);
            buffer.append("}");
            buffer.append(",");
        }
        String json=StringUtils.substringBeforeLast(buffer.toString(),",");
        json+="]";
        return json;
    }

	public String game() throws Exception {
		HttpSession session = Struts2Utils.getSession();

		language = (String) session.getAttribute(Constants.PARA_LANGUAGE);
		Long storeId = chooseStoreId(session);
		newestPage = fileManager.searchStoreInfoInShelf(newestPage, Shelf.Type.GAME, storeId, language);
		return "game";
	}

    public String star() throws Exception{
        HttpSession session = Struts2Utils.getSession();

        language = (String) session.getAttribute(Constants.PARA_LANGUAGE);
        Long storeId = chooseStoreId(session);
        newestPage = fileManager.searchStoreInfoInShelf(newestPage, Shelf.Type.STAR, storeId, language);
        categoryName=Shelf.Type.STAR.getDisplayName();
        return "star";
    }

    public String gostar() throws Exception{
        return "gostar";
    }

    @Deprecated
	public String love() throws Exception {

		return "love";
	}

	public String newest() throws Exception {
		HttpSession session = Struts2Utils.getSession();

		language = (String) session.getAttribute(Constants.PARA_LANGUAGE);
		Long storeId = chooseStoreId(session);
		newestPage = fileManager.searchByStore(newestPage, storeId, language);
		return "newest";
	}

    public String hottest() throws Exception{
        HttpSession session = Struts2Utils.getSession();

        language = (String) session.getAttribute(Constants.PARA_LANGUAGE);
        Long storeId = chooseStoreId(session);
        sorts = logJdbcDao.countThemeFileDownload(language,storeId,pageNo);
        return "hottest";
    }

	public String diy() throws Exception {
		HttpSession session = Struts2Utils.getSession();

		language = (String) session.getAttribute(Constants.PARA_LANGUAGE);
		Long storeId = chooseStoreId(session);
		newestPage = fileManager.searchDiyTemplate(newestPage, storeId, language);
		return "diy";
	}

	public String category() throws Exception {
		categories = categoryManager.getCategoriesInStore();
		return "category";
	}

	private Long chooseStoreId(HttpSession session) throws Exception {
		String storeType = (String) session.getAttribute(Constants.PARA_STORE_TYPE);
		Long storeId = (Long) session.getAttribute(Constants.ID_LOCK);
		if (StringUtils.isBlank(storeType)) {
			storeType = Constants.ST_LOCK;
		}
		if (storeId != null) {
			return storeId;
		} else {
			try {
				storeId = categoryManager.getStoreByValue(storeType).getId();
			} catch (Exception e) {
				logger.warn("商店{}不存在，请求错误～", storeType);
				Struts2Utils.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND, "parametter is incorrect.");
			}
			session.setAttribute(Constants.ID_LOCK, storeId);
			return storeId;
		}

	}

	/**
	 * 输出5个广告xml
	 * @return
	 * @throws Exception
	 */
	public String adXml() throws Exception {

        HttpServletRequest request=Struts2Utils.getRequest();
        HttpServletResponse response=Struts2Utils.getResponse();
        request.getRequestDispatcher("/poll/advertisement!generateXml.action?st=lock").forward(request,response);
		return null;
	}

	public String details() throws Exception {
		try {

			HttpSession session = Struts2Utils.getSession();

			language = (String) session.getAttribute(Constants.PARA_LANGUAGE);
			Long storeId = chooseStoreId(session);
			info = fileManager.getStoreInfoBy(storeId, id, language);
			if (info == null)
				return "reload";

			Category cate = info.getTheme().getCategories().get(0);
			List<CategoryInfo> cateInfos = cate.getInfos();
			for (CategoryInfo ci : cateInfos) {
				if (ci.getDescription().equals(language)) {
					categoryName = ci.getName();
					break;
				}
			}
			setDownloadType(session, cate.getDescription());
			long count = countContentDao.queryTotalDownload(info.getTheme().getTitle());
			totalDown = convert(count);
			catePage = fileManager.searchInfoByCategoryAndStore(catePage, cate.getId(), storeId, language);
			List<FileStoreInfo> fileinfos = catePage.getResult();
			fileinfos.remove(info);
			Collections.shuffle(fileinfos);
			if (fileinfos.size() > 3) {
				fileinfos = fileinfos.subList(0, 3);
			}
			catePage.setResult(fileinfos);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "reload";
		}
		return "details";
	}

	private String convert(long count) {
		count = count * 10;

		if (count < 10000) {
			if (count < 1000) {
				return "1000以下";
			} else {
				String number = StringUtils.substring(String.valueOf(count), 0, 1);
				for (int i = 1; i < 10; i++) {
					if (Integer.valueOf(number).equals(i)) {
						return i + "000+";
					}
				}
			}
		} else {
			String number = StringUtils.substring(String.valueOf(count), 0, 1);
			String tenThousandPosition = StringUtils.substring(String.valueOf(count), 0,
					String.valueOf(count).length() - 4);

			for (int i = 1; i < 10; i++) {
				if (Integer.valueOf(number).equals(i)) {

					return tenThousandPosition + "万+";
				}
			}

		}
		return "";
	}

	private void setDownloadType(HttpSession session, String category) throws UnsupportedEncodingException {

		String fromMarket = (String) session.getAttribute(Constants.PARA_FROM_MARKET);
		String downType = (String) session.getAttribute(Constants.PARA_DOWNLOAD_METHOD);
		StringBuilder httpBuffer = new StringBuilder();
		if (category.equalsIgnoreCase("other")) {
			httpBuffer.append("browerhttp://" + StringUtils.remove(Constants.getDomain(), "http://") + "/");
		}
		httpBuffer.append("file-download.action?id=");
		httpBuffer.append(info.getTheme().getId());
		httpBuffer.append("&inputPath=");
		if (StringUtils.isNotBlank(info.getTheme().getUxPath())) {
			httpBuffer.append(URLEncoder.encode(info.getTheme().getUxPath(), "utf-8"));
		} else {
			httpBuffer.append(URLEncoder.encode(info.getTheme().getApkPath(), "utf-8"));
		}

		httpBuffer.append("&title=" + URLEncoder.encode(info.getTitle(), "utf-8"));
		httpBuffer.append(URLEncoder.encode("|", "utf-8")).append(
				URLEncoder.encode(info.getTheme().getTitle(), "utf-8"));
		httpBuffer.append("&");
		if (downType.equals(DownloadType.MARKET.getValue())) {
			marketDownload(fromMarket, httpBuffer.toString());
		} else {
			info.getTheme().setDownloadURL(httpBuffer.toString());
		}
	}

	private void marketDownload(String fromMarket, String http) {
		Market market = marketManager.findByPkName(fromMarket);
		if (market == null || market.getMarketKey().isEmpty()) {
			info.getTheme().setDownloadURL(http);
		} else {
			fileInMarket(market, http);
		}
	}

	private void fileInMarket(Market market, String http) {
		List<ThemeFile> files = market.getThemes();
		if (files.contains(info.getTheme())) {
			String uri = market.getMarketKey() + info.getTheme().getMarketURL();
			if (market.getPkName().equals(Constants.LENVOL_STORE)) {
				uri += ("&versioncode=" + info.getTheme().getVersion());
			}
			if (market.getPkName().equals(Constants.OPPO_NEARME)) {
				List<FileMarketValue> fvs = info.getTheme().getMarketValues();
				for (FileMarketValue fm : fvs) {
					if (market.getId().equals(fm.getMarket().getId())) {
						uri += "&" + (fm.getKeyName() + "=" + fm.getKeyValue());
					}
				}
			}
			info.getTheme().setDownloadURL(uri);
		} else {
			info.getTheme().setDownloadURL(http);
		}
	}

	public String more() throws Exception {

		HttpSession session = Struts2Utils.getSession();
		HttpServletRequest request = Struts2Utils.getRequest();
		language = (String) session.getAttribute(Constants.PARA_LANGUAGE);

		Long storeId = chooseStoreId(session);
		categoryId = Long.valueOf(Struts2Utils.getParameter("cid"));

		//		cateInfos = categoryInfoManager.getInfosBylanguage(language);
		try {
			categoryName = categoryManager.getCategory(categoryId).getName();
		} catch (Exception e) {
			String userAgent = request.getHeader("User-Agent");
			String ip = ServletUtils.getIpAddr(request);
			logger.warn(
					"com.tp.entity.Category:#{}不存在,ip:" + ip + ",User-Agent:" + userAgent + " param:"
							+ session.getAttribute(Constants.QUERY_STRING), categoryId);
			Struts2Utils.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND, "parametter is incorrect.");
			return null;
		}
		catePage = fileManager.searchInfoByCategoryAndStore(catePage, categoryId, storeId, language);

		return "more";
	}

	@Autowired
	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}

	@Autowired
	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

	@Autowired
	public void setMarketManager(MarketManager marketManager) {
		this.marketManager = marketManager;
	}

	@Autowired
	public void setCategoryInfoManager(CategoryInfoManager categoryInfoManager) {
		this.categoryInfoManager = categoryInfoManager;
	}

	public Page<FileStoreInfo> getHottestPage() {
		return hottestPage;
	}

	public Page<FileStoreInfo> getNewestPage() {
		return newestPage;
	}

	public Page<FileStoreInfo> getCatePage() {
		return catePage;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FileStoreInfo getInfo() {
		return info;
	}

	public void setInfo(FileStoreInfo info) {
		this.info = info;
	}

    public Page<Advertisement> getAdvertisementPage(){
        return  advertisementPage;
    }

	public List<CategoryInfo> getCateInfos() {
		return cateInfos;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public String getLanguage() {
		return language;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public String getTotalDown() {
		return totalDown;
	}

    public String getBars(){
        return bars;
    }

    public List<Map<String,Object>> getSorts(){
        return sorts;
    }

    public Long getPageNo(){
        return pageNo;
    }

    public void setPageNo(Long pageNo){
        this.pageNo=pageNo;
    }

    public Long getNextPage(){
        return pageNo+1;
    }

	@Autowired
	public void setCountContentDao(LogCountContentDao countContentDao) {
		this.countContentDao = countContentDao;
	}

    @Autowired
    public void setAdvertisementService(AdvertisementService advertisementService){
        this.advertisementService=advertisementService;
    }

    @Autowired
    public void setLogJdbcDao(LogJdbcDao logJdbcDao){
        this.logJdbcDao=logJdbcDao;
    }
}
