package com.tp.entity.log;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.tp.entity.IdEntity;

@Entity
@Table(name = "log_f_poll")
public class LogForPoll extends IdEntity {

	private String bcid;
	private String appName;
	private String url;
	private String imei;
	private String imsi;
	private String language;
	private String clientVersion;
	private String fromMarket;
	private String resolution;
	private String netEnv;
	private String operators;
	private String doType;
	private Date createTime;

	public String getBcid() {
		return bcid;
	}

	public void setBcid(String bcid) {
		this.bcid = bcid;
	}

	@Column(name = "app")
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Column(name = "version")
	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getFromMarket() {
		return fromMarket;
	}

	public void setFromMarket(String fromMarket) {
		this.fromMarket = fromMarket;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getNetEnv() {
		return netEnv;
	}

	public void setNetEnv(String netEnv) {
		this.netEnv = netEnv;
	}

	@Column(name = "operator")
	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	public String getDoType() {
		return doType;
	}

	public void setDoType(String doType) {
		this.doType = doType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}
}
