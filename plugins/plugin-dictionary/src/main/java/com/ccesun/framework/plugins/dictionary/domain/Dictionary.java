package com.ccesun.framework.plugins.dictionary.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ccesun.framework.core.dao.support.IEntity;

@Entity
@Table(name="SYS_DICT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Dictionary implements IEntity<Integer> {
	
	private static final long serialVersionUID = 627178618L;
	
	@Id
	@GeneratedValue
	@Column(name="RECORD_ID")
	private Integer recordId;
	
	@Column(name="DICT_TYPE")
	private String dictType;
	
	@Column(name="DICT_KEY")
	private String dictKey;
	
	@Column(name="DICT_VALUE0")
	private String dictValue0;
	
	@Column(name="DICT_VALUE1")
	private String dictValue1;
	
	@Column(name="DICT_VALUE2")
	private String dictValue2;
	
	@Column(name="DICT_VALUE3")
	private String dictValue3;

	@Column(name="PARENT_KEY")
	private String parentKey;
	
	public Integer getRecordId() {
		return recordId;
	}
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	public String getDictType() {
		return dictType;
	}
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	public String getDictKey() {
		return dictKey;
	}
	public void setDictKey(String dictKey) {
		this.dictKey = dictKey;
	}
	public String getDictValue() {
		return dictValue0;
	}
	public String getDictValue0() {
		return dictValue0;
	}
	public void setDictValue0(String dictValue0) {
		this.dictValue0 = dictValue0;
	}
	public String getDictValue1() {
		return dictValue1;
	}
	public void setDictValue1(String dictValue1) {
		this.dictValue1 = dictValue1;
	}
	public String getDictValue2() {
		return dictValue2;
	}
	public void setDictValue2(String dictValue2) {
		this.dictValue2 = dictValue2;
	}
	public String getDictValue3() {
		return dictValue3;
	}
	public void setDictValue3(String dictValue3) {
		this.dictValue3 = dictValue3;
	}
	public String getParentKey() {
		return parentKey;
	}
	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}
	@Override
	@Transient
	public boolean isNew() {
		return this.recordId == null;
	}
}
