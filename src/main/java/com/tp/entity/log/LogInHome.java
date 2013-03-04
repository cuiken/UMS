package com.tp.entity.log;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.tp.entity.IdEntity;

@Entity
@Table(name = "log_f_store2")
public class LogInHome extends IdEntity {

	private String imei;
	private String imsi;
	private String storeType;
	private String downType;
	private String appName;
	private String language;
	private String clientVersion;
	private String resolution;
	private String fromMarket;
	private String requestMethod;
	private String requestParams;
	private String createTime;

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public String getDownType() {
		return downType;
	}

	public void setDownType(String downType) {
		this.downType = downType;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getFromMarket() {
		return fromMarket;
	}

	public void setFromMarket(String fromMarket) {
		this.fromMarket = fromMarket;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(String requestParams) {
		this.requestParams = requestParams;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}
}
