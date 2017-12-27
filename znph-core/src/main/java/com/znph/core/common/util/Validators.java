package com.znph.core.common.util;

public class Validators {

	public static final String REGEX_URL = "<a\\s+href=\"([^\"]*)[^>]*>(.*?)</a>";
	public static final String REGEX_EMAIL = "(\\w+\\.\\w+)*@\\w+\\.[a-zA-Z]+";
	public static final String REGEX_DIGIT = "\\d+";
	public static final String REGEX_NUMBER = "^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:\\.\\d+)?$";
	public static final String REGEX_MAC = "^([0-9a-fA-F]{2})(([0-9a-fA-F]{2}){5})$";
	public static final String REGEX_IP = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
	public static final String REGEX_CHINESE = "[\u4e00-\u9fa5]";
	public static final String Phone = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
	
	public static boolean phone(String source) {
		return source.matches(Phone);
	}

	public static boolean email(String source) {
		return source.matches(REGEX_EMAIL);
	}
	
	public static boolean mac(String source) {
		return source.matches(REGEX_MAC);
	}
	
	public static boolean digit(String source) {
		return source.matches(REGEX_DIGIT);
	}
	
	public static boolean ip(String source) {
		return source.matches(REGEX_IP);
	}
	
	public static boolean number(String source) {
		return source.matches(REGEX_NUMBER);
	}
	
	public static boolean chinese(String source) {
		return source.matches(REGEX_CHINESE);
	}
	
}
