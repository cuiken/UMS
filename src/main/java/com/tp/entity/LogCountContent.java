package com.tp.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.collect.Lists;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LogCountContent extends IdEntity {

	private String logDate;
	private String themeName;
	private Long totalVisit;
	private Long visitByAd;
	private Long visitByStore;
	private Long totalDown;
	private Long downByStore;
	private Date createTime;
	private List<LogContentMarket> downByPerMarket = Lists.newArrayList();

	public String getLogDate() {
		return logDate;
	}

	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public Long getTotalVisit() {
		return totalVisit;
	}

	public void setTotalVisit(Long totalVisit) {
		this.totalVisit = totalVisit;
	}

	public Long getVisitByAd() {
		return visitByAd;
	}

	public void setVisitByAd(Long visitByAd) {
		this.visitByAd = visitByAd;
	}

	public Long getVisitByStore() {
		return visitByStore;
	}

	public void setVisitByStore(Long visitByStore) {
		this.visitByStore = visitByStore;
	}

	public Long getTotalDown() {
		return totalDown;
	}

	public void setTotalDown(Long totalDown) {
		this.totalDown = totalDown;
	}

	public Long getDownByStore() {
		return downByStore;
	}

	public void setDownByStore(Long downByStore) {
		this.downByStore = downByStore;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@OneToMany(mappedBy = "logContent", fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, orphanRemoval = true)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<LogContentMarket> getDownByPerMarket() {
		return downByPerMarket;
	}

	public void setDownByPerMarket(List<LogContentMarket> downByPerMarket) {
		this.downByPerMarket = downByPerMarket;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}
}
