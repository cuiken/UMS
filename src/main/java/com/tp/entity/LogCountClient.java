package com.tp.entity;

import javax.persistence.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
public class LogCountClient extends IdEntity {

	private String createTime;
	private long openCount;
	private long totalUser;
	private long openUser;
	private long incrementUser;
	private long totalDownload;
	private long downByContent;
	private long downByShare;
	private long downByOther;
	private long visitStoreCount;
	private long visitStoreUser;
	private long takeTimes;

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public long getOpenCount() {
		return openCount;
	}

	public void setOpenCount(long openCount) {
		this.openCount = openCount;
	}

	public long getTotalUser() {
		return totalUser;
	}

	public void setTotalUser(long totalUser) {
		this.totalUser = totalUser;
	}

	public long getOpenUser() {
		return openUser;
	}

	public void setOpenUser(long openUser) {
		this.openUser = openUser;
	}

	public long getIncrementUser() {
		return incrementUser;
	}

	public void setIncrementUser(long incrementUser) {
		this.incrementUser = incrementUser;
	}

	public long getTotalDownload() {
		return totalDownload;
	}

	public void setTotalDownload(long totalDownload) {
		this.totalDownload = totalDownload;
	}

	public long getDownByContent() {
		return downByContent;
	}

	public void setDownByContent(long downByContent) {
		this.downByContent = downByContent;
	}

	public long getDownByShare() {
		return downByShare;
	}

	public void setDownByShare(long downByShare) {
		this.downByShare = downByShare;
	}

	public long getDownByOther() {
		return downByOther;
	}

	public void setDownByOther(long downByOther) {
		this.downByOther = downByOther;
	}

	public long getVisitStoreCount() {
		return visitStoreCount;
	}

	public void setVisitStoreCount(long visitStoreCount) {
		this.visitStoreCount = visitStoreCount;
	}

	public long getVisitStoreUser() {
		return visitStoreUser;
	}

	public void setVisitStoreUser(long visitStoreUser) {
		this.visitStoreUser = visitStoreUser;
	}

	public long getTakeTimes() {
		return takeTimes;
	}

	public void setTakeTimes(long takeTimes) {
		this.takeTimes = takeTimes;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}
}
