package com.tp.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "f_polling")
public class Polling extends IdEntity {

	private String uuid;
	private String name;
	private String serType;
	private String link;
	private String serDesc;
	private String showBegin;
	private String showEnd;
	private Long showTotal;
	private Long showPerday;
	private String store;
	private Long status;
	private Date createTime;

	private List<PollingPreview> previews;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSerType() {
		return serType;
	}

	public void setSerType(String serType) {
		this.serType = serType;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getSerDesc() {
		return serDesc;
	}

	public void setSerDesc(String serDesc) {
		this.serDesc = serDesc;
	}

	public String getShowBegin() {
		return showBegin;
	}

	public void setShowBegin(String showBegin) {
		this.showBegin = showBegin;
	}

	public String getShowEnd() {
		return showEnd;
	}

	public void setShowEnd(String showEnd) {
		this.showEnd = showEnd;
	}

	public Long getShowTotal() {
		return showTotal;
	}

	public void setShowTotal(Long showTotal) {
		this.showTotal = showTotal;
	}

	public Long getShowPerday() {
		return showPerday;
	}

	public void setShowPerday(Long showPerday) {
		this.showPerday = showPerday;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@OneToMany(mappedBy = "polling", fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, orphanRemoval = true)
	public List<PollingPreview> getPreviews() {
		return previews;
	}

	public void setPreviews(List<PollingPreview> previews) {
		this.previews = previews;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}
}
