package com.tpadsz.navigator.entity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Navigator {

	@XmlElement
	public Top getTop() {
		return top;
	}

	public void setTop(Top top) {
		this.top = top;
	}

	@XmlElement(name = "Center-Left")
	public CenterLeft getLeft() {
		return left;
	}

	public void setLeft(CenterLeft left) {
		this.left = left;
	}

	@XmlElement(name = "Center-Right")
	public CenterRight getRight() {
		return right;
	}

	public void setRight(CenterRight right) {
		this.right = right;
	}

	@XmlElement
	public Bottom getBottom() {
		return bottom;
	}

	public void setBottom(Bottom bottom) {
		this.bottom = bottom;
	}

	private Top top;
	private CenterLeft left;
	private CenterRight right;
	private Bottom bottom;

	private static JAXBContext navigatorContext = null;
	private static Unmarshaller navigatorUnmarshaller = null;
	private static Marshaller navigatorMarshaller = null;
	static {
		try {
			navigatorContext = JAXBContext.newInstance(Navigator.class);
			navigatorMarshaller = navigatorContext.createMarshaller();
			navigatorUnmarshaller = navigatorContext.createUnmarshaller();

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Navigator unmarshall(InputStream inStream)
			throws JAXBException {
		Navigator ret = null;
		if (navigatorContext == null || navigatorMarshaller == null
				|| navigatorUnmarshaller == null) {
			return null;
		}

		ret = (Navigator) navigatorUnmarshaller.unmarshal(inStream);

		return ret;
	}

	public static String marshall(Navigator nav) {
		if (navigatorContext == null || navigatorMarshaller == null
				|| navigatorUnmarshaller == null) {
			return null;
		}
		String ret = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			navigatorMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
			navigatorMarshaller.marshal(nav, bos);
			bos.flush();
			ret = new String(bos.toByteArray(), "UTF-8");
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}
}
