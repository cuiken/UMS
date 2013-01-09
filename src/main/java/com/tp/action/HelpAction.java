package com.tp.action;

import org.apache.struts2.convention.annotation.Namespace;

import com.opensymphony.xwork2.ActionSupport;

@Namespace("/store")
public class HelpAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	public String device() {
		return "device";
	}

	public String inquiry() {
		return "inquiry";
	}

	public String tokusyou() {
		return "tokusyou";
	}
}
