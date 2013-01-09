package com.tpadsz.navigator.entity;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.tpadsz.navigator.entity.xmlAdapter.MyHashMapAdapter;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class Button {

	public Button() {
		super();
	}

	@XmlAttribute
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlAttribute
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@XmlAttribute(name = "class")
	public Integer getClazz() {
		return clazz;
	}

	public void setClazz(Integer clazz) {
		this.clazz = clazz;
	}

	@XmlAttribute
	public Boolean getFocus() {
		return focus;
	}

	public void setFocus(Boolean focus) {
		this.focus = focus;
	}

	@XmlAttribute
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@XmlAttribute
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@XmlAttribute
	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	@XmlAttribute
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@XmlAttribute
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@XmlAttribute
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	@XmlJavaTypeAdapter(MyHashMapAdapter.class)
	@XmlElement(nillable = true)
	public HashMap<String, String> getPictures() {
		return pictures;
	}

	public void setPictures(HashMap<String, String> pictures) {
		this.pictures = pictures;
	}

	@Override
	public String toString() {
		return title.toString();
	}

	public Button(Long id, String action, Integer clazz, Boolean focus,
			String position, String size, String tips, String title,
			String picture) {
		super();
		this.id = id;
		this.action = action;
		this.clazz = clazz;
		this.focus = focus;
		this.position = position;
		this.size = size;
		this.tips = tips;
		this.title = title;
		this.picture = picture;
	}

	private Long id;
	private String action;
	private Integer clazz;
	private Boolean focus;
	private String position;
	private String size;
	private String tips;
	private String title;
	private String value;
	private String picture;
	private HashMap<String, String> pictures;
}
