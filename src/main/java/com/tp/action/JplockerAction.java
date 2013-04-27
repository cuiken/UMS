package com.tp.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.opensymphony.xwork2.ActionSupport;
import com.tp.entity.FileStoreInfo;
import com.tp.entity.Market;
import com.tp.entity.Shelf;
import com.tp.entity.ThemeFile;
import com.tp.orm.Page;
import com.tp.service.CategoryManager;
import com.tp.service.FileManager;
import com.tp.service.MarketManager;
import com.tp.utils.Constants;
import com.tp.utils.Struts2Utils;

@Namespace("/store")
@Results({@Result(name = "reload", location = "jplocker.action", type = "redirect")})
public class JplockerAction extends ActionSupport {

    private static final long serialVersionUID = 1L;
    private Logger logger = LoggerFactory.getLogger(getClass());

    private CategoryManager categoryManager;

    private FileManager fileManager;
    private MarketManager marketManager;

    private Page<FileStoreInfo> hottestPage = new Page<FileStoreInfo>();

    private Page<FileStoreInfo> newestPage = new Page<FileStoreInfo>();
    private Page<FileStoreInfo> catePage = new Page<FileStoreInfo>();

    private FileStoreInfo info;

    private String language;

    @Override
    public String execute() throws Exception {

        return list();
    }

    /**
     * 商店首页显示列表
     *
     * @return
     * @throws Exception
     */
    public String list() throws Exception {

        HttpSession session = Struts2Utils.getSession();

        language = (String) session.getAttribute(Constants.PARA_LANGUAGE);

        Long storeId = chooseStoreId(session);

        hottestPage.setPageSize(100);
        newestPage.setPageSize(100);
        hottestPage = fileManager.searchStoreInfoInShelf(hottestPage, Shelf.Type.HOTTEST.getValue(), storeId, language);

        newestPage = fileManager.searchStoreInfoInShelf(newestPage, Shelf.Type.NEWEST.getValue(), storeId, language);

        Market market = this.getMarket(session);
        List<FileStoreInfo> allFile = Lists.newArrayList();
        allFile.addAll(newestPage.getResult());
        allFile.addAll(hottestPage.getResult());

        for (FileStoreInfo info : allFile) {
            fileManager.setDownloadType(market, info.getTheme());
        }
        return SUCCESS;
    }

    /**
     * 输出5个广告xml
     *
     * @return
     * @throws Exception
     */
    public String adXml() throws Exception {
        HttpSession session = Struts2Utils.getSession();
        Long storeId = chooseStoreId(session);
        ;
        Page<ThemeFile> adPage = new Page<ThemeFile>();
        adPage = fileManager.searchFileByShelf(adPage, Shelf.Type.RECOMMEND, storeId);

        Market market = this.getMarket(session);
        String domain = Constants.getDomain();

        String xml = fileManager.gadXml(adPage.getResult(), domain, market);
        Struts2Utils.renderXml(xml);
        return null;
    }

    private Long chooseStoreId(HttpSession session) throws Exception {

        String storeType = Struts2Utils.getParameter(Constants.PARA_STORE_TYPE);
        if (StringUtils.isBlank(storeType)) {
            storeType = Constants.JP_LOCKER;
        }
        Long storeId = 0L;
        try {
            storeId = categoryManager.getStoreByValue(storeType).getId();
        } catch (Exception e) {
            logger.warn("商店{}不存在，请求错误～", storeType);
            Struts2Utils.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND, "parametter is incorrect.");
        }
        return storeId;

    }

    private Market getMarket(HttpSession session) {
        String fromMarket = (String) session.getAttribute(Constants.PARA_FROM_MARKET);
        if (fromMarket == null || fromMarket.isEmpty()) {
            fromMarket = Constants.MARKET_GOOGLE;
        }
        return marketManager.findByPkName(fromMarket);
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

    public Page<FileStoreInfo> getHottestPage() {
        return hottestPage;
    }

    public Page<FileStoreInfo> getNewestPage() {
        return newestPage;
    }

    public Page<FileStoreInfo> getCatePage() {
        return catePage;
    }

    public FileStoreInfo getInfo() {
        return info;
    }

    public void setInfo(FileStoreInfo info) {
        this.info = info;
    }

    public String getLanguage() {
        return language;
    }
}
