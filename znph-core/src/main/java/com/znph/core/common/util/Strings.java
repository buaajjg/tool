package com.znph.core.common.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Strings {
	
	public static boolean in(String key, String... strings){
		return equalsAny(key, strings);
	}
	
	public static boolean equalsAny(String key, String... strings){
		for (String temp : strings) {
			if(temp.equalsIgnoreCase(key)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean equalsAnyIgnoreCase(String key, String... strings){
		for (String temp : strings) {
			if(temp.equals(key)) {
				return true;
			}
		}
		return false;
	}

	public static String bytesToBinary(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		int v;
		for (int j = 0; j < bytes.length; j++) {
			v = bytes[j];
			sb.append(Integer.toBinaryString(v));
		}
		return sb.toString();
	}

	public static String bytesToHex(byte[] bytes) {
		char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] hexChars = new char[bytes.length * 2];
		int v;
		for (int j = 0; j < bytes.length; j++) {
			v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
	
	
	public static byte[] hexToBytes(String hexString) {
		byte[] bytes = hexString.getBytes();
        int iLen = bytes.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(bytes, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
	}

	public static String find(String content, String regex) {
		return find(content, regex, 0);
	}

	public static String find(String content, String regex, int groupId) {
		Matcher m = Pattern.compile(regex, Pattern.DOTALL).matcher(content);
		if (m.find()) {
			return m.group(groupId);
		}
		return null;
	}

	public static List<String> finds(String content, String regex) {
		return finds(content, regex, 0);
	}

	public static List<String> finds(String content, String regex, int groupId) {
		List<String> list = new ArrayList<String>();
		Matcher m = Pattern.compile(regex, Pattern.DOTALL).matcher(content);
		while (m.find()) {
			list.add(m.group(groupId));
		}
		return list;
	}

	public static String[] findFirst(String content, String regex) {
		Matcher m = Pattern.compile(regex, Pattern.DOTALL).matcher(content);
		int groupCount = m.groupCount() + 1;
		String[] array = new String[groupCount];
		if (m.find()) {
			for (int i = 0; i < groupCount; i++) {
				array[i] = m.group(i);
			}
		}
		return array;
	}

	public static List<String[]> findAll(String content, String regex) {
		Matcher m = Pattern.compile(regex, Pattern.DOTALL).matcher(content);
		int groupCount = m.groupCount() + 1;
		List<String[]> list = new ArrayList<String[]>();
		while (m.find()) {
			String[] array = new String[groupCount];
			for (int i = 0; i < groupCount; i++) {
				array[i] = m.group(i);
			}
			list.add(array);
		}
		return list;
	}

	public static String insertSeparator(String text, int length, String separator) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while (i < text.length() - length) {
			sb.append(text.substring(i, i + length)).append(separator);
			i += length;
		}
		sb.append(text.substring(i, text.length()));
		return sb.toString();
	}

	public static boolean isEmpty(String content) {
		return content == null || content.trim().length() == 0;
	}
	
	public static boolean isBlank(String content) {
		return content == null || content.trim().length() == 0;
	}

	public static boolean isNotBlank(String content) {
		return content != null && content.trim().length() > 0;
	}

	public static String ISOToUTF8(String s) {
		try {
			return new String(s.getBytes("ISO-8859-1"), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String UTF8ToISO(String s) {
		try {
			return new String(s.getBytes("UTF-8"), "ISO-8859-1");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String lowerFirst(String string) {
		if (isNotBlank(string)) {
			if (string.length() > 1) {
				return Character.toLowerCase(string.charAt(0)) + string.substring(1);
			} else {
				return string.toLowerCase();
			}
		}
		return string;
	}

	public static Integer[] splitToIntegerArray(String string) {
		return splitToIntegerArray(string, ",");
	}

	public static Integer[] splitToIntegerArray(String string, String regex) {
		String[] ids = string.split(regex);
		List<Integer> list = new ArrayList<Integer>();
		for (String id : ids) {
			if (id.trim().length() > 0 && !id.equals(regex)) {
				list.add(Integer.parseInt(id.trim()));
			}
		}
		Integer[] array = new Integer[list.size()];
		list.toArray(array);
		return array;
	}

	public static Long[] splitToLongArray(String string) {
		return splitToLongArray(string, ",");
	}

	public static Long[] splitToLongArray(String string, String regex) {
		String[] ids = string.split(regex);
		List<Long> list = new ArrayList<Long>();
		for (String id : ids) {
			if (id.trim().length() > 0 && !id.equals(regex)) {
				list.add(Long.parseLong(id.trim()));
			}
		}
		Long[] array = new Long[list.size()];
		list.toArray(array);
		return array;
	}

	public static String trim(String string, char c) {
		while (string.charAt(0) == c) {
			string = string.substring(1);
		}
		while (string.charAt(string.length() - 1) == c) {
			string = string.substring(0, string.length() - 1);
		}
		return string;
	}

	public static String trim(String string, String reg) {
		Matcher matcher = Pattern.compile("\\A[" + reg + "]+", Pattern.DOTALL).matcher(string);
		if (matcher.find()) {
			int end = matcher.end();
			string = string.substring(end);
		}
		matcher.usePattern(Pattern.compile("[" + reg + "]+\\z", Pattern.DOTALL));
		matcher.reset(string);
		if (matcher.find()) {
			int start = matcher.start();
			string = string.substring(0, start);
		}
		return string;
	}

	public static String unqualify(String qualifiedName) {
		int loc = qualifiedName.lastIndexOf(".");
		return (loc < 0) ? qualifiedName : qualifiedName.substring(loc + 1);
	}

	public static String upperFirst(String string) {
		if (isNotBlank(string)) {
			if (string.length() > 1) {
				return Character.toUpperCase(string.charAt(0)) + string.substring(1);
			} else {
				return string.toUpperCase();
			}
		}
		return string;
	}

	public static String urlEncode(String string) {
		return urlEncode(string, "UTF-8");
	}

	public static String urlEncode(String string, String charset) {
		try {
			return URLEncoder.encode(string, charset);
		} catch (Exception e) {
			return string;
		}
	}

	public static String urlDecode(String string) {
		return urlDecode(string, "UTF-8");
	}

	public static String urlDecode(String string, String charset) {
		try {
			return URLDecoder.decode(string, charset);
		} catch (Exception e) {
			return string;
		}
	}

	public static String toUnicode(String utfString) {
		StringBuffer unicode = new StringBuffer();
		for (int i = 0; i < utfString.length(); i++) {
			char c = utfString.charAt(i);
			unicode.append("\\u" + Integer.toHexString(c));
		}
		return unicode.toString();
	}

	public static String fromUnicode(String s) {
    	Pattern reUnicode = Pattern.compile("\\\\u([0-9a-zA-Z]{4})");
        Matcher m = reUnicode.matcher(s);
        StringBuffer sb = new StringBuffer(s.length());
        while (m.find()) {
            m.appendReplacement(sb,Character.toString((char) Integer.parseInt(m.group(1), 16)));
        }
        m.appendTail(sb);
        return sb.toString();
	}

	public static String properties(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		for (String key : map.keySet()) {
			sb.append(key).append("=").append(map.get(key)).append("\n");
		}
		return sb.toString();
	}

	public static Map<String, String> properties(String content) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		String[] lines = content.split("\n");
		for (String line : lines) {
			line = line.trim();
			int index = line.indexOf("=");
			if (index > -1) {
				map.put(line.substring(0, index), line.substring(index + 1));
			}
		}
		return map;
	}

	public static String params(Map<String, ? extends Object> params) {
		StringBuffer sb = new StringBuffer();
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				sb.append(key).append("=").append(params.get(key)).append("&");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public static String toString(List<? extends Object> list, String split) {
		StringBuffer sb = new StringBuffer();
		if (list != null && list.size() != 0) {
			for (Object object : list) {
				sb.append(object.toString()).append(split);
			}
			sb.deleteCharAt(sb.length() - split.length());
		}
		return sb.toString();
	}

	public static String toString(Object[] array, String split) {
		StringBuffer sb = new StringBuffer();
		if (array != null && array.length != 0) {
			for (Object object : array) {
				sb.append(object.toString()).append(split);
			}
			sb.deleteCharAt(sb.length() - split.length());
		}
		return sb.toString();
	}
	
}
