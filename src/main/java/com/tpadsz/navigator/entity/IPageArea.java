package com.tpadsz.navigator.entity;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public interface IPageArea {

	@XmlAttribute(name = "template", required = true)
	public abstract String getTemplate();

	public abstract void setTemplate(String template);

	@XmlElementWrapper(name = "Buttons")
	@XmlElement
	public abstract ArrayList<Button> getButtons();

	public abstract void setButtons(ArrayList<Button> buttons);

}