package com.gammax.ci.gammax.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestProperties {
	static final Logger log = LogManager.getLogger(TestProperties.class);
	static Properties props;

	/***
	 * Get a Property
	 * 
	 * @param key property name
	 * @return property value
	 * @author Arafat
	 */

	public static String get(String key) {
		try {
			if (null == props) {
				props = new Properties();

				InputStream is = ClassLoader.getSystemResourceAsStream("data.properties");
				props.load(is);
				is.close();
			}
			String value;
			if ((value = props.getProperty(key)) != null) {
				return value;
			} else {
				throw new Exception("Property " + key + " Not Found");
			}
		} catch (IOException e) {
			log.error("IOException occurred while loading the property file " + e);
		} catch (Exception e) {
			log.error("Property " + key + " Not Found");
		}
		return null;
	}

	/***
	 * Get a Property
	 * 
	 * @param key      property name
	 * @param filePath Properties Name
	 * @return property value
	 * @author Arafat
	 */

	public static String get(String key, String filePath) {
		try {
			if (null == props) {
				props = new Properties();

				InputStream is = ClassLoader.getSystemResourceAsStream(filePath);
				props.load(is);
				is.close();
			}
			String value;
			if ((value = props.getProperty(key)) != null) {
				return value;
			} else {
				throw new Exception("Property " + key + " Not Found");
			}
		} catch (IOException e) {
			log.error("IOException occurred while loading the property file " + e);
		} catch (Exception e) {
			log.error("Property " + key + " Not Found");
		}
		return null;
	}

}
