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
@Table(name="sys_area")
public class SysArea implements IEntity<Integer> {
	
	private static final long serialVersionUID = 609128990L;
	
	/** 主键 */
	@Id
	@GeneratedValue
	@Column(name="record_id")
	private Integer recordId;
	
	/** 编码 */
	@Column(name="areacode")
	private String areacode;
	
	/** 简称 */
	@Column(name="areaname")
	private String areaname;
	
	/** 全称 */
	@Column(name="full_areaname")
	private String fullAreaname;
	
	/** 是否可用 */
	@Column(name="is_available")
	private Integer isAvailable;
	
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	
	public Integer getRecordId() {
		return recordId;
	}
	
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	
	public String getAreacode() {
		return areacode;
	}
	
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	
	public String getAreaname() {
		return areaname;
	}
	
	public void setFullAreaname(String fullAreaname) {
		this.fullAreaname = fullAreaname;
	}
	
	public String getFullAreaname() {
		return fullAreaname;
	}
	
	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	public Integer getIsAvailable() {
		return isAvailable;
	}
	
	@Override
	@Transient
	public boolean isNew() {
		return EntityUtils.isNew(this.recordId);
	}
}
