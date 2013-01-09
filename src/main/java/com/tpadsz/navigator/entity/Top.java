package com.tpadsz.navigator.entity;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class Top implements IPageArea {
	private String template;

	/* (non-Javadoc)
	 * @see com.tpadsz.navigator.entity.IPageArea#getTemplate()
	 */
	@XmlAttribute(name = "template", required = true)
	public String getTemplate() {
		return template;
	}

	/* (non-Javadoc)
	 * @see com.tpadsz.navigator.entity.IPageArea#setTemplate(java.lang.String)
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

	ArrayList<Button> buttons;

	/* (non-Javadoc)
	 * @see com.tpadsz.navigator.entity.IPageArea#getButtons()
	 */
	@XmlElementWrapper(name = "Buttons")
	@XmlElement
	public ArrayList<Button> getButtons() {
		return buttons;
	}

	/* (non-Javadoc)
	 * @see com.tpadsz.navigator.entity.IPageArea#setButtons(java.util.ArrayList)
	 */
	public void setButtons(ArrayList<Button> buttons) {
		this.buttons = buttons;
	}

}
