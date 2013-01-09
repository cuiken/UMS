package com.tp.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "f_market_info")
public class FileMarketValue extends IdEntity {

	private String keyName;
	private String keyValue;
	private ThemeFile theme;
	private Market market;

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "theme_id")
	public ThemeFile getTheme() {
		return theme;
	}

	public void setTheme(ThemeFile theme) {
		this.theme = theme;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "market_id")
	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}
}
