package org.jaronsource.sample.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Transient;

import com.ccesun.framework.core.dao.support.IEntity;
import com.ccesun.framework.core.dao.support.EntityUtils;

@Entity
@Table(name="sys_dict")
public class SysDict implements IEntity<Integer> {
	
	private static final long serialVersionUID = 432696317L;
	
	/** 主键 */
	@Id
	@GeneratedValue
	@Column(name="record_id")
	private Integer recordId;
	
	/** 类型 */
	@Column(name="dict_type")
	private String dictType;
	
	/** 键 */
	@Column(name="dict_key")
	private String dictKey;
	
	/** 值0 */
	@Column(name="dict_value0")
	private String dictValue0;
	
	/** 值1 */
	@Column(name="dict_value1")
	private String dictValue1;
	
	/** 值2 */
	@Column(name="dict_value2")
	private String dictValue2;
	
	/** 值3 */
	@Column(name="dict_value3")
	private String dictValue3;
	
	/** 父级KEY */
	@Column(name="parent_key")
	private String parentKey;
	
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	
	public Integer getRecordId() {
		return recordId;
	}
	
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	
	public String getDictType() {
		return dictType;
	}
	
	public void setDictKey(String dictKey) {
		this.dictKey = dictKey;
	}
	
	public String getDictKey() {
		return dictKey;
	}
	
	public void setDictValue0(String dictValue0) {
		this.dictValue0 = dictValue0;
	}
	
	public String getDictValue0() {
		return dictValue0;
	}
	
	public void setDictValue1(String dictValue1) {
		this.dictValue1 = dictValue1;
	}
	
	public String getDictValue1() {
		return dictValue1;
	}
	
	public void setDictValue2(String dictValue2) {
		this.dictValue2 = dictValue2;
	}
	
	public String getDictValue2() {
		return dictValue2;
	}
	
	public void setDictValue3(String dictValue3) {
		this.dictValue3 = dictValue3;
	}
	
	public String getDictValue3() {
		return dictValue3;
	}
	
	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}
	
	public String getParentKey() {
		return parentKey;
	}
	
	@Override
	@Transient
	public boolean isNew() {
		return EntityUtils.isNew(this.recordId);
	}
}
