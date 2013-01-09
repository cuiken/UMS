package com.tp.entity;

public enum DownloadType {

	HTTP("0"), MARKET("1");

	private String value;

	DownloadType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
