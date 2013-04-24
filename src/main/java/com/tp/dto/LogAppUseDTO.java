package com.tp.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dozer.Mapping;

/**
 * User: ken.cui
 * Date: 13-4-24
 * Time: 上午10:25
 */
public class LogAppUseDTO {
    private String mac;
    private String imei;
    private String imsi;
    private String v;
    private String op;
    private String fm;
    private String f;
    private String net;
    private String l;
    private String st;
    private String app;
    private String r;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

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


    @Mapping("version")

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getFm() {
        return fm;
    }

    public void setFm(String fm) {
        this.fm = fm;
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

    @Mapping("language")
    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }


    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    @Mapping("comeFrom")
    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
