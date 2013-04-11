package com.ccesun.sample.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.lucene.document.Field.Index;
import org.hibernate.validator.constraints.NotBlank;

import com.ccesun.framework.core.dao.support.IEntity;
import com.ccesun.framework.plugins.dictionary.DictValue;
import com.ccesun.framework.plugins.search.SearchableBean;
import com.ccesun.framework.plugins.search.SearchableField;

@Entity
@Table(name="contact")
@SearchableBean
public class Contact implements IEntity<Integer> {
	
	private static final long serialVersionUID = 1992475237L;
	
	/** 主键 */
	@Id
	@GeneratedValue
	@Column(name="RECORD_ID")
	private Integer recordId;
	
	/** 姓名 */
	@Column(name="NAME")
	@NotBlank(message="{field.contact.name}")
	@SearchableField(index=Index.ANALYZED)
	private String name;
	
	/** 性别 */
	@DictValue(type="XINGB")
	@Column(name="SEX")
	private String sex;
	
	/** 电话 */
	@Column(name="PHONE")
	@SearchableField(index=Index.ANALYZED)
	private String phone;
	
	/** 地区 */
	@Column(name="AREACODE")
	private String areacode;
	
	/** 地址 */
	@Column(name="ADDRESS")
	private String address;
	
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	
	public Integer getRecordId() {
		return recordId;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getSex() {
		return sex;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	
	public String getAreacode() {
		return areacode;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return address;
	}
	
	@Override
	@Transient
	public boolean isNew() {
		return this.recordId == null;
	}
}
