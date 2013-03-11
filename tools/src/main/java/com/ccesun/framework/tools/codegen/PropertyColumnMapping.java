package com.ccesun.framework.tools.codegen;

public class PropertyColumnMapping {
	
	private MaterialDetail materialDetail;
	
	private String propertyName;
	private String propertyType;
	
	private String columnName;
	private String columnType;
	private String columnRemarks;
	
	private boolean pk;
	
	private boolean fk;
	
	public MaterialDetail getMaterialDetail() {
		return materialDetail;
	}

	public void setMaterialDetail(MaterialDetail materialDetail) {
		this.materialDetail = materialDetail;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnRemarks() {
		return columnRemarks;
	}

	public void setColumnRemarks(String columnRemarks) {
		this.columnRemarks = columnRemarks;
	}

	public boolean isPk() {
		return pk;
	}

	public void setPk(boolean pk) {
		this.pk = pk;
	}

	public boolean isFk() {
		return fk;
	}

	public void setFk(boolean fk) {
		this.fk = fk;
	}

	//public PropertyAndColumn getFkPac() {
	//	return fkPac;
	//}

	//public void setFkPac(PropertyAndColumn fkPac) {
	//	this.fkPac = fkPac;
	//}

}
