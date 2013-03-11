package com.ccesun.framework.tools.codegen;

public class Material {

	private String domainName;
	private String tableName;
	
	public Material() {}
	
	public Material(String domainName, String tableName) {
		this.domainName = domainName;
		this.tableName = tableName;
	}
	
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
