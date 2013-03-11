package com.ccesun.framework.core.web;

import java.io.Serializable;

public class AjaxResult implements Serializable {

	private static final long serialVersionUID = 860010297181778128L;

	private boolean success = Boolean.TRUE;
	
	private String message;
	
	private Object data;
	
	/**
	 * 如果success为true,则data存入data中,否则data当做error字符串存入
	 * @param success
	 * @param data
	 */
	public AjaxResult(boolean success, Object data) {
		this.success = success;
		if(this.success){
			this.data = data;
		}else{
			this.message = data == null? "error!" : data.toString();
		}
	}

	public AjaxResult(Object data) {
		super();
		this.success = Boolean.TRUE;
		this.data = data;
	}

	public AjaxResult(boolean success, String message, Object data) {
		super();
		this.success = success;
		this.message = message;
		this.data = data;
	}
	
	public static AjaxResult success(Object data){
		return new AjaxResult(data);
	}
	
	public static AjaxResult fail(String error){
		return new AjaxResult(Boolean.FALSE, error);
	}
	

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
