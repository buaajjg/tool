package com.znph.core.common.exception;

import com.znph.core.common.message.CodeMessageIdentity;

public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 7283191803130530250L;
	
	private CodeMessageIdentity codeMessageIdentity;
	private Object[] args;

	public BaseException(CodeMessageIdentity codeMessageIdentity) {
		this.codeMessageIdentity = codeMessageIdentity;
	}

	public BaseException(CodeMessageIdentity codeMessageIdentity, Object[] args) {
		this.codeMessageIdentity = codeMessageIdentity;
		this.args = args;
	}

	public CodeMessageIdentity getCodeMessageIdentity() {
		return codeMessageIdentity;
	}

	public Object[] getArgs() {
		return args;
	}

}
