package com.tp.entity.log;

import javax.persistence.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.tp.entity.IdEntity;

@Entity
public class LogCountUnzip extends IdEntity {

	private String appName;
	private Long unzip;
	private String marketName;
	private String createTime;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Long getUnzip() {
		return unzip;
	}

	public void setUnzip(Long unzip) {
		this.unzip = unzip;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
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
