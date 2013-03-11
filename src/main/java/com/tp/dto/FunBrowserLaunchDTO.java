package com.tp.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dozer.Mapping;

public class FunBrowserLaunchDTO {

	private String imei;
	private String imsi;
	private String l;
	private String v;
	private String launcher;
	private String model;
	private String op;
	private String r;
	private String net;

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	@Mapping("language")
	public String getL() {
		return l;
	}

	public void setL(String l) {
		this.l = l;
	}

	@Mapping("version")
	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public String getLauncher() {
		return launcher;
	}

	public void setLauncher(String launcher) {
		this.launcher = launcher;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	@Mapping("resolution")
	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

	public String getNet() {
		return net;
	}

	public void setNet(String net) {
		this.net = net;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}
}
