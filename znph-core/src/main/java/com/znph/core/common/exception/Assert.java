package com.znph.core.common.exception;

import com.znph.core.common.message.CodeMessageIdentity;
import com.znph.core.common.util.Strings;

public class Assert {

	public static void notNull(Object object, CodeMessageIdentity codeMessage) {
		if (object == null) {
			throw new BaseException(codeMessage);
		}
	}

	public static void isNull(Object object, CodeMessageIdentity codeMessage) {
		if (object != null) {
			throw new BaseException(codeMessage);
		}
	}

	public static void notNull(Object object, CodeMessageIdentity codeMessage, Object... args) {
		if (object == null) {
			throw new BaseException(codeMessage, args);
		}
	}

	public static void isNull(Object object, CodeMessageIdentity codeMessage, Object... args) {
		if (object != null) {
			throw new BaseException(codeMessage, args);
		}
	}

	public static void notBlank(String content, CodeMessageIdentity codeMessage) {
		if (Strings.isBlank(content)) {
			throw new BaseException(codeMessage);
		}
	}

	public static void notBlank(String content, CodeMessageIdentity codeMessage, Object... args) {
		if (Strings.isBlank(content)) {
			throw new BaseException(codeMessage, args);
		}
	}

	public static void isBlank(String content, CodeMessageIdentity codeMessage) {
		if (!Strings.isBlank(content)) {
			throw new BaseException(codeMessage);
		}
	}

	public static void isBlank(String content, CodeMessageIdentity codeMessage, Object... args) {
		if (!Strings.isBlank(content)) {
			throw new BaseException(codeMessage, args);
		}
	}

	public static void isTrue(Boolean expression, CodeMessageIdentity codeMessage) {
		if (expression!=null && !expression) {
			throw new BaseException(codeMessage);
		}
	}
	
	public static void isTrue(Boolean expression, CodeMessageIdentity codeMessage, Object... args) {
		if (expression!=null && !expression) {
			throw new BaseException(codeMessage, args);
		}
	}
	
	public static void isFalse(Boolean expression, CodeMessageIdentity codeMessage) {
		if (expression!=null && expression) {
			throw new BaseException(codeMessage);
		}
	}
	
	public static void isFalse(Boolean expression, CodeMessageIdentity codeMessage, Object... args) {
		if (expression!=null && expression) {
			throw new BaseException(codeMessage, args);
		}
	}

}
