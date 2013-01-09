package com.tp.utils;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

public class Constants {

	public final static String LOCKER_STORAGE = Config.getProperty("locker.storage");
	public final static String NAV_STORAGE = Config.getProperty("nav.storage");
	public final static String CLIENT_STORAGE = Config.getProperty("client.storeage");
	public final static String INDEX_STORAGE = Config.getProperty("index.log");
	public final static String POLL_STORAGE = Config.getProperty("poll.storeage");
	public final static String CATEGROY_STORAGE = Config.getProperty("category.storage");

	public final static String[] IMG_EXTENSION = { "jpg", "jpeg", "gif", "bmp", "png" };

	public static final String ENCODE_UTF_8 = "UTF-8";

	public static final String DOT_SEPARATOR = ".";

	public static final String ST_LOCK = "lock";
	public static final String JP_LOCKER = "jplocker";
	public static final String DM_LOCKER = "dmlocker";

	public static final String ID_LOCK = "lockId";
	public static final String ID_JPLOCKER = "jplockerId";

	//============客户端参数===========================================================//
	public static final String PARA_LANGUAGE = "l";
	public static final String PARA_DOWNLOAD_METHOD = "dm";
	public static final String PARA_FROM_MARKET = "fm";
	public static final String PARA_IMEI = "imei";
	public static final String PARA_IMSI = "imsi";
	public static final String PARA_STORE_TYPE = "st";
	public static final String PARA_CLIENT_VERSION = "v";
	public static final String PARA_CONTENT_VERSION = "cv";
	public static final String PARA_CLIENT_TYPE = "ct";
	public static final String PARA_RESOLUTION = "r";
	public static final String PARA_OPERATORS = "op";
	public static final String PARA_NET_ENVIRONMENT = "net";
	public static final String PARA_AUTO_SWITCH = "as";
	public static final String PARA_SAFETYLOCK = "sl";
	public static final String PARA_MACHINE_MODEL = "model";
	public static final String PARA_DO_TYPE = "do";
	public static final String PARA_APP_NAME = "app";
	public static final String PARA_BCID = "bcid";
	public static final String PARA_URL = "url";
	public static final String PARA_FROM = "f";

	//=======================================================================================//

	public static final String QUERY_STRING = "queryString";
	public static final String METHOD_AD_XML = "adXml";
	public static final String METHOD_GET_CLIENT = "getClient";
	public static final String METHOD_EXECUTE = "execute";

	public static final String LOCKER_STANDARD = "lock";
	public static final String LOCKER_JP = "jplocker";
	public static final String LOCKER_DM = "dm";

	public static final String LENVOL_STORE = "com.lenovo.leos.appstore";
	public static final String OPPO_NEARME = "com.oppo.market";
	public static final String MARKET_GOOGLE = "com.android.vending";

	public enum Language {
		ZH("zh"), EN("en"), JP("jp"), JA("ja");
		private String value;

		Language(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public static String getDomain() {
		HttpServletRequest request = Struts2Utils.getRequest();
		StringBuilder buffer = new StringBuilder();
		buffer.append("http://").append(request.getServerName()).append(":").append(request.getLocalPort())
				.append(request.getContextPath());
		return buffer.toString();
	}

	public static String getLocal() {
		Locale local = ServletActionContext.getContext().getLocale();
		return local.getLanguage();
	}

	public enum Operator {
		/**
		 * 联通
		 */
		LT("lt"),
		/**
		 * 电信
		 */
		DX("dx"),
		/**
		 * 移动
		 */
		YD("yd");
		private String value;

		Operator(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
}
