package com.ccesun.framework.plugins.security.domain;

public class PermissionAdapter implements IPermission {

	private static final long serialVersionUID = -6987988424816894688L;

	private String code;
	
	private String name;
	
	private String groupCode;
	
	private String parentCode;
	
	private String url;
	
	private Integer order;
	
	private Boolean readable;
	
	private Boolean editable;

	@Override
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Override
	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	@Override
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Boolean getReadable() {
		return readable;
	}

	public void setReadable(Boolean readable) {
		this.readable = readable;
	}

	@Override
	public Boolean getEditable() {
		return this.editable;
	}

}
