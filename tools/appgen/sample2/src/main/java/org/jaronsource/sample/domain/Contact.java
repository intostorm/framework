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
@Table(name="contact")
public class Contact implements IEntity<Integer> {
	
	private static final long serialVersionUID = 731090789L;
	
	/** 主键 */
	@Id
	@GeneratedValue
	@Column(name="record_id")
	private Integer recordId;
	
	/** 姓名 */
	@Column(name="name")
	private String name;
	
	/** 性别 */
	@Column(name="sex")
	private String sex;
	
	/** 电话 */
	@Column(name="phone")
	private String phone;
	
	/** 地区 */
	@Column(name="areacode")
	private String areacode;
	
	/** 地址 */
	@Column(name="address")
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
		return EntityUtils.isNew(this.recordId);
	}
}
