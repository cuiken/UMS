package com.tp.dto;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class LogDTO {

	private String isNew;
	private String imei;
	private String imsi;
	private String language;
	private String doType;
	private String clientVersion;
	private String contentVersion;
	private String resolution;
	private String netEnv;
	private String operators;
	private String appName;
	private String fromMarket;
	private String downType;
	private String storeType;
	private String clientType;
	private String model;
	private String autoSwitch;
	private String safetyLock;
	private String from;
	private String bcid;
	private String url;

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public String getImei() {
		return StringUtils.substring(imei, 0, 20);
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return StringUtils.substring(imsi, 0, 20);
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getLanguage() {
		return StringUtils.substring(language, 0, 10);
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getDoType() {
		return StringUtils.substring(doType, 0, 35);
	}

	public void setDoType(String doType) {
		this.doType = doType;
	}

	public String getClientVersion() {
		return StringUtils.substring(clientVersion, 0, 35);
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getContentVersion() {
		return StringUtils.substring(contentVersion, 0, 35);
	}

	public void setContentVersion(String contentVersion) {
		this.contentVersion = contentVersion;
	}

	public String getResolution() {
		return StringUtils.substring(resolution, 0, 35);
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getNetEnv() {
		return StringUtils.substring(netEnv, 0, 35);
	}

	public void setNetEnv(String netEnv) {
		this.netEnv = netEnv;
	}

	public String getOperators() {
		return StringUtils.substring(operators, 0, 35);
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	public String getAppName() {
		return StringUtils.substring(appName, 0, 35);
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getFromMarket() {
		return StringUtils.substring(fromMarket, 0, 255);
	}

	public void setFromMarket(String fromMarket) {
		this.fromMarket = fromMarket;
	}

	public String getDownType() {
		return downType;
	}

	public void setDownType(String downType) {
		this.downType = downType;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getModel() {
		return StringUtils.substring(model, 0, 35);
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getAutoSwitch() {
		return autoSwitch;
	}

	public void setAutoSwitch(String autoSwitch) {
		this.autoSwitch = autoSwitch;
	}

	public String getSafetyLock() {
		return safetyLock;
	}

	public void setSafetyLock(String safetyLock) {
		this.safetyLock = safetyLock;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getBcid() {
		return bcid;
	}

	public void setBcid(String bcid) {
		this.bcid = bcid;
	}

	public String getUrl() {
		return StringUtils.substring(url, 0, 255);
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}
}
