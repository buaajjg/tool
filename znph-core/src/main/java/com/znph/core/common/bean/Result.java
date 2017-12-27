package com.znph.core.common.bean;

public class Result {

	private static Result create() {
		return new Result();
	}

	private static Result create(Object data) {
		return new Result(data);
	}
	
	public static Result judge(Boolean bl) {
		if(bl == null || bl == false) {
			return no();
		}
		return ok();
	}

	public static Result no() {
		return create().failure();
	}

	public static Result failure(String message) {
		return create().failure().message(message);
	}

	public static Result failure(String message, String code) {
		return failure(message).code(code);
	}

	public static Result ok() {
		return create().success();
	}

	public static Result ok(Object data) {
		return create(data).success();
	}

	public static Result success(String message) {
		return ok().message(message);
	}

	public static Result success(String message, Object data) {
		return ok(data).message(message);
	}

	public static Result success(String message, String code, Object data) {
		return success(message, data).code(code);
	}

	private String message;
	private Object data;
	private String code = "0";

	private Result() {
	}

	private Result(Object data) {
		this.data = data;
	}

	public Result success() {
		return this;
	}

	public Result failure() {
		this.code = "1";
		return this;
	}

	public Result code(String code) {
		this.code = code;
		return this;
	}

	public Result message(String message) {
		this.message = message;
		return this;
	}

	public Result data(Object data) {	
		this.data = data;
		return this;
	}
	// new PrintStream(bos)

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

	public String getCode() {
		return code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Result clearData() {
		this.data = null;
		return this;
	}

}
