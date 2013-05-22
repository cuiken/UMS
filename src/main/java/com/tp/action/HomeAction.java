package com.tp.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.common.collect.Lists;
import com.tp.dao.log.LogJdbcDao;
import com.tp.entity.*;
import com.tp.orm.PageRequest;
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
    private FileStoreInfo gameInfo;
    private FileStoreInfo appInfo;

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
		hottestPage = fileManager.searchStoreInfoInShelf(hottestPage, Shelf.Type.HOTTEST.getValue(), storeId, language);

		newestPage = fileManager.searchStoreInfoInShelf(newestPage, Shelf.Type.NEWEST.getValue(), storeId, language);

        bars=json(getADs("store"));
		return SUCCESS;
	}

    private List<Advertisement> getADs(String type){
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());
        filters.add(new PropertyFilter("EQS_dtype", type));
        filters.add(new PropertyFilter("EQS_store", Constants.ST_LOCK));
        filters.add(new PropertyFilter("EQL_status","1"));
        if(!advertisementPage.isOrderBySetted()){
            advertisementPage.setOrderBy("sort");
            advertisementPage.setOrderDir(PageRequest.Sort.ASC);
        }
        advertisementPage=advertisementService.searchAdvertisement(advertisementPage,filters);
        return advertisementPage.getResult();
    }

    private String json(List<Advertisement> ads){
        StringBuilder buffer=new StringBuilder();
        buffer.append("[");
        for(Advertisement ad:ads){
            buffer.append("{");
            buffer.append("\"pic\":\"http://uichange.com/UMS/files/"+ad.getImgLink()+"\"");
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

    @Deprecated
	public String game() throws Exception {

        return dispatcher("game");
	}

    public String shelf() throws Exception{
        HttpSession session = Struts2Utils.getSession();

        language = (String) session.getAttribute(Constants.PARA_LANGUAGE);
        Long storeId = chooseStoreId(session);
        String sf=Struts2Utils.getParameter("sf");

       Store store= categoryManager.getStore(storeId);
        for(Shelf s:store.getShelfs()){
            if(s.getValue().equals(sf)){
                categoryName=s.getName();
                break;
            }
        }
        newestPage.setPageSize(10);
        newestPage = fileManager.searchStoreInfoInShelf(newestPage, sf, storeId, language);
        return "shelf";
    }

    @Deprecated
    public String star() throws Exception{

        HttpSession session = Struts2Utils.getSession();

        language = (String) session.getAttribute(Constants.PARA_LANGUAGE);
        Long storeId = chooseStoreId(session);
        newestPage = fileManager.searchStoreInfoInShelf(newestPage, Shelf.Type.STAR.getValue(), storeId, language);
        categoryName=Shelf.Type.STAR.getDisplayName();
        return "star";
    }

    private String dispatcher(String type) throws Exception{
        HttpServletRequest request=Struts2Utils.getRequest();
        HttpServletResponse response=Struts2Utils.getResponse();
        request.getRequestDispatcher("/home!shelf.action?sf="+type).forward(request, response);
        return null;
    }

    public String gostar() throws Exception{
        return "gostar";
    }

	public String love() throws Exception {

		return "love";
	}

    public String man() throws Exception{
        return "man";
    }

    public String hottest() throws Exception{
        HttpSession session = Struts2Utils.getSession();

        language = (String) session.getAttribute(Constants.PARA_LANGUAGE);
        Long storeId = chooseStoreId(session);
        sorts = logJdbcDao.countThemeFileDownload(language,storeId,pageNo);
//        bars=json(getADs("store-hot"));
        return "hottest";
    }

    public String jsonMore() throws Exception{
        HttpSession session = Struts2Utils.getSession();

        language = (String) session.getAttribute(Constants.PARA_LANGUAGE);
        Long storeId = chooseStoreId(session);
        sorts = logJdbcDao.countThemeFileDownload(language,storeId,pageNo);
        String json=toJson(sorts);
        json=json.replaceAll("/","\\\\/");
//        json=json.replaceAll("\"","\\\"");
        logger.info(json);
        Struts2Utils.renderJson(json);
        return null;
    }

    private String toJson(List<Map<String,Object>> contents){
        String queryString=(String)Struts2Utils.getSession().getAttribute("queryString");
        Locale locale= getLocale();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("localStrings", locale) ;
        StringBuilder buffer=new StringBuilder();
        buffer.append("{");
        if(contents.size()<10){
            buffer.append("\"code\":900");
        }else{
            buffer.append("\"code\":200");
        }
        buffer.append(",\"data\":\"");
        for(Map<String,Object> theme:contents){
            buffer.append("<li onclick=\\\"location.href='home!details.action?id="+theme.get("f_id")+"&"+queryString+"'\\\">");
            buffer.append("<div class=\\\"icon\\\"><img src=\\\"http://uichange.com/UMS/files/"+theme.get("iconPath")+"\\\"></div>");
            buffer.append(" <div class=\\\"y-split\\\"></div>");
            buffer.append(" <div class=\\\"info\\\">" +
                    "        <p class=\\\"title\\\">"+theme.get("title")+"</p>" +
                    "        <p class=\\\"txt\\\">"+theme.get("shortDescription")+"</p>" +
                    "        <div class=\\\"y-split right\\\"></div>" +
                    "        <div class=\\\"down-btn\\\">" +
                    "            <img src=\\\"static/images/2.0/down.png\\\">" +
                    "            <span>"+resourceBundle.getString("home.down")+"</span>" +
                    "        </div>" +
                    "    </div>");
            buffer.append("</li>");
        }
        buffer.append("\"}");
        return buffer.toString();
    }

    public String category() throws Exception {
		List<Category> lists = categoryManager.getCategories();
        categories= Lists.newArrayList();
        for(Category category:lists){
            if(StringUtils.contains(category.getDescription(),"hidden")){
                continue;
            }
            categories.add(category);
        }
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
			setDownloadType(session, cate.getDescription(),info);
			totalDown = countContentDao.queryTotalDownload(info.getTheme().getTitle(),language);
			catePage = fileManager.searchInfoByCategoryAndStore(catePage, cate.getId(), storeId, language);
			List<FileStoreInfo> fileinfos = catePage.getResult();
			fileinfos.remove(info);
			Collections.shuffle(fileinfos);
			if (fileinfos.size() > 2) {
				fileinfos = fileinfos.subList(0, 2);
			}
			catePage.setResult(fileinfos);
            shuffleGame(storeId,session);
            shuffleApp(storeId,session);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "reload";
		}
		return "details";
	}

    private void shuffleGame(Long storeId,HttpSession session) throws Exception{
        newestPage.setPageSize(100);

        newestPage = fileManager.searchStoreInfoInShelf(newestPage, "game", storeId, language);


        List<FileStoreInfo> fileInfos=newestPage.getResult();
//        fileInfos.addAll(hottestPage.getResult());
        Collections.shuffle(fileInfos);
        if(fileInfos.size()>0){
            gameInfo=fileInfos.get(0);
        }
//        gameInfo.getTheme().setDownloadURL("browerhttp://" + StringUtils.remove(Constants.getDomain(), "http://") + "/file-download.action?id="+gameInfo.getTheme().getId()+"&inputPath="+gameInfo.getTheme().getApkPath());
        setDownloadType(session,gameInfo.getTheme().getCategories().get(0).getDescription(),gameInfo);
    }

    private void shuffleApp(Long storeId,HttpSession session) throws Exception{
        hottestPage.setPageSize(100);
        hottestPage=fileManager.searchStoreInfoInShelf(newestPage, "app", storeId, language);
        List<FileStoreInfo> fileInfos=hottestPage.getResult();
        Collections.shuffle(fileInfos);
        if(fileInfos.size()>0){
            appInfo=fileInfos.get(0);
        }
        setDownloadType(session,appInfo.getTheme().getCategories().get(0).getDescription(),appInfo);
    }

	private void setDownloadType(HttpSession session, String category,FileStoreInfo fsi) throws UnsupportedEncodingException {

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
		httpBuffer.append(URLEncoder.encode("|", "utf-8")).append(
				URLEncoder.encode(fsi.getTheme().getTitle(), "utf-8"));
		httpBuffer.append("&");
		if (downType.equals(DownloadType.MARKET.getValue())) {
			marketDownload(fromMarket, httpBuffer.toString(),fsi);
		} else {
            fsi.getTheme().setDownloadURL(httpBuffer.toString());
		}
	}

	private void marketDownload(String fromMarket, String http,FileStoreInfo fsi) {
		Market market = marketManager.findByPkName(fromMarket);
		if (market == null || market.getMarketKey().isEmpty()) {
			fsi.getTheme().setDownloadURL(http);
		} else {
			fileInMarket(market, http,fsi);
		}
	}

	private void fileInMarket(Market market, String http,FileStoreInfo fsi) {
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

	public String more() throws Exception {

		HttpSession session = Struts2Utils.getSession();
		HttpServletRequest request = Struts2Utils.getRequest();
		language = (String) session.getAttribute(Constants.PARA_LANGUAGE);

		Long storeId = chooseStoreId(session);
        try {
		categoryId = Long.valueOf(Struts2Utils.getParameter("cid"));

		//		cateInfos = categoryInfoManager.getInfosBylanguage(language);

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
        catePage.setPageSize(10);
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

    public FileStoreInfo getGameInfo() {
        return gameInfo;
    }

    public FileStoreInfo getAppInfo() {
        return appInfo;
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
