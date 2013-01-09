package com.tp.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 文件在市场上的信息实体
 * 
 * @author ken
 * 
 */
@Entity
@Table(name = "f_store_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FileStoreInfo extends IdEntity {

	private String title;
	private String shortDescription;
	private String longDescription;
	private String author;
	private Long price;
	private String language;

	private Long fiId;

	private ThemeFile theme;
	private Store store;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_id")
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	public ThemeFile getTheme() {
		return theme;
	}

	public void setTheme(ThemeFile theme) {
		this.theme = theme;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Long getFiId() {
		return fiId;
	}

	public void setFiId(Long fiId) {
		this.fiId = fiId;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		FileStoreInfo that = (FileStoreInfo) obj;
		return that.getId().equals(this.getId()) && that.getLanguage().equals(this.getLanguage());
	}

	@Override
	public int hashCode() {
		int result = 37;
		result = result * 17 + id.hashCode();
		result = result * 17 + title.hashCode();
		result = result * 17 + shortDescription.hashCode();
		result = result * 17 + longDescription.hashCode();
		result = result * 17 + author.hashCode();
		result = result * 17 + language.hashCode();
		return result;
	}

}
