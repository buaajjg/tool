package com.znph.core.common.property;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.znph.core.common.message.CodeMessage;
import com.znph.core.common.message.CodeMessageIdentity;
import com.znph.core.common.util.Collections;


public class CodeMessageHolder {

	private static Map<String, CodeMessage> codeMessageMap = Collections.hashMap();
	
	public static void putAll(Properties properties) {
		Set<Object> keySet = properties.keySet();
		for (Object key : keySet) {
			String code = key.toString();
			if(code.matches("\\d{6}\\.\\w+\\.\\w+")) {
				String numberCode = code.substring(0, 6);
				String stringCode = code.substring(7);
				String value = properties.getProperty(code);
				codeMessageMap.put(stringCode, new CodeMessage(numberCode, value));
			}
		}
	}
	
	public static CodeMessage get(CodeMessageIdentity key) {
		return codeMessageMap.get(key.code());
	}
	
}
