package com.tp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "f_third_url")
public class ThemeThirdURL extends IdEntity {

	private String cmURL; //移动
	private String cuURL; //联通
	private String ctURL; //电信

	private ThemeFile theme;

	@URL
	@Column(name = "c_mobile")
	public String getCmURL() {
		return cmURL;
	}

	public void setCmURL(String cmURL) {
		this.cmURL = cmURL;
	}

	@URL
	@Column(name = "c_unicome")
	public String getCuURL() {
		return cuURL;
	}

	public void setCuURL(String cuURL) {
		this.cuURL = cuURL;
	}

	@URL
	@Column(name = "c_tele")
	public String getCtURL() {
		return ctURL;
	}

	public void setCtURL(String ctURL) {
		this.ctURL = ctURL;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "theme_id")
	public ThemeFile getTheme() {
		return theme;
	}

	public void setTheme(ThemeFile theme) {
		this.theme = theme;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}
}
