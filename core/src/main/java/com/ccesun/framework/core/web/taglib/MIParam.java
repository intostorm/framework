package com.ccesun.framework.core.web.taglib;

public class MIParam {

	private String type;
	private Object value;
	
	public MIParam() {}
	
	public MIParam(String type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
}
