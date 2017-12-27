package com.znph.core.common.property;

import java.util.Properties;

public class PropertyHolder {

	private static Properties properties = new Properties();

	public static void putAll(Properties properties) {
		PropertyHolder.properties.putAll(properties);
	}

	public static void put(String key, String value) {
		PropertyHolder.properties.put(key, value);
	}
	
	public static String get(String key) {
		String value = System.getenv(key);
		if (value == null) {
			value = System.getProperty(key);
		}
		if (value == null) {
			value = properties.getProperty(key);
		}
		return value;
	}

	public static String get(String key, String defaultValue) {
		String value = get(key);
		return value != null ? value : defaultValue;
	}

	public static boolean isOn(String property) {
		String prop = get(property);
		return "on".equalsIgnoreCase(prop);
	}

	public static boolean isTrue(String property) {
		String bool = get(property);
		return Boolean.valueOf(bool);
	}

}
