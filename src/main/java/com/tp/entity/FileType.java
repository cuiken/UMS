package com.tp.entity;

public enum FileType {

	ZIP("zip"),
	UX("ux"),
	APK("apk"),
	PREVIEW_CLIENT("pre_c"),
	PREVIEW_WEB("pre_w"),
	ICON("icon_"),
	AD("ad_"),
	UX_W("_w"),
	UX_H("_h");
	
	private String value;

	FileType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
