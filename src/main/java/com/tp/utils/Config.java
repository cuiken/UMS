package com.tp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {

	private static final Logger logger = LoggerFactory.getLogger(Config.class);

	private static Properties prop = null;
	private static String propFileName = "/mopita.properties";

	private synchronized static void loadProperties() {
		prop = new Properties();
		try {
			InputStream input = Config.class.getResourceAsStream(propFileName);
			prop.load(input);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public synchronized static String getProperty(String propName) {
		if (prop == null)
			loadProperties();
		String value = prop.getProperty(propName);
		if (value == null)
			logger.warn(propName + " --- 资源文件中没有找到对应的key-value");
		return value;
	}

	public static String getPropFileName() {
		return propFileName;
	}

	public static void setPropFileName(String propFileName) {
		Config.propFileName = propFileName;
	}

}
