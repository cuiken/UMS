package com.tp.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dozer.Mapping;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ad")
public class AdDTO {

    private String id;
    private String fileName;
    private String format;
    private String version;
    private String linkUrl;
    private String downloadUrl;

    @XmlAttribute()
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute
    @Mapping("imgName")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @XmlAttribute
    @Mapping("imgExt")
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @XmlAttribute
    public String getVersion() {

        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Mapping("link")
    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    @Mapping("imgLink")
    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this);
    }
}
