package com.tp.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dozer.Mapping;

import com.google.common.collect.Lists;

@XmlRootElement(name = "poll")
@XmlType(propOrder = { "type", "name", "description", "link", "showBegin", "showEnd", "showPerday", "showTotal",
		"previews" })
public class PollDTO {

	private String uuid;
	private String type;
	private String name;
	private String description;
	private String link;
	private String showBegin;
	private String showEnd;
	private String showPerday;
	private String showTotal;
	private List<Preview> previews = Lists.newArrayList();

	@XmlAttribute(name = "id")
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Mapping("serType")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Mapping("serDesc")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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

	public String getShowPerday() {
		return showPerday;
	}

	public void setShowPerday(String showPerday) {
		this.showPerday = showPerday;
	}

	public String getShowTotal() {
		return showTotal;
	}

	public void setShowTotal(String showTotal) {
		this.showTotal = showTotal;
	}

	@XmlElementWrapper(name = "previews")
	@XmlElement(name = "preview")
	public List<Preview> getPreviews() {
		return previews;
	}

	public void setPreviews(List<Preview> previews) {
		this.previews = previews;
	}

	public static class Preview {
		private String name;
		private String spec;
		private String link;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSpec() {
			return spec;
		}

		public void setSpec(String spec) {
			this.spec = spec;
		}

		@Mapping("addr")
		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}

	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}
}
