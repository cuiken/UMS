package com.tp.entity.log;

import javax.persistence.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.tp.entity.IdEntity;

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
	private long totalInstall;
	private long installWithfm;
	private long installNonfm;
    private long installUser;
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

	public long getTotalInstall() {
		return totalInstall;
	}

	public void setTotalInstall(long totalInstall) {
		this.totalInstall = totalInstall;
	}

	public long getInstallWithfm() {
		return installWithfm;
	}

	public void setInstallWithfm(long installWithfm) {
		this.installWithfm = installWithfm;
	}

	public long getInstallNonfm() {
		return installNonfm;
	}

	public void setInstallNonfm(long installNonfm) {
		this.installNonfm = installNonfm;
	}

    public  long getInstallUser(){
        return installUser;
    }

    public void setInstallUser(long installUser){
        this.installUser=installUser;
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
