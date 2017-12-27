package com.znph.core.common.message;

import java.text.MessageFormat;

public class CodeMessage {
	
	private final String code;
	private final String message;

	public CodeMessage(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
	public String getMessage(Object... args) {
		return MessageFormat.format(message, args);
	}

}
