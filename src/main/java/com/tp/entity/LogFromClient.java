package com.tp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "log_f_client")
public class LogFromClient extends IdEntity {

	private String imei;
	private String imsi;
	private String storeType;
	private String downType;
	private String language;
	private String clientVersion;
	private String clientType;
	private String resolution;
	private String fromMarket;
	private String autoSwitch;
	private String safetyLock;
	private String netEnv;
	private String operators;
	private String model;
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

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
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

	public String getNetEnv() {
		return netEnv;
	}

	public void setNetEnv(String netEnv) {
		this.netEnv = netEnv;
	}

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
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
