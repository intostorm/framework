package com.ccesun.framework.plugins.area.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ccesun.framework.core.dao.support.IEntity;

@Entity
@Table(name = "SYS_AREA")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Area implements IEntity<String> {

	private static final long serialVersionUID = -2532169399973048725L;
	
	@Id
	@GeneratedValue
	@Column(name = "RECORD_ID")
	private Integer recordId;
	
	@Column(name = "AREACODE", length = 15)
	private String areacode;
	
	@Column(name = "AREANAME", length = 50)
	private String areaName;
	
	@Column(name = "FULL_AREANAME", length = 255)
	private String fullAreaName;
	
	@Column(name = "IS_AVAILABLE")
	private boolean available;
	
	public Area() {}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public String getAreacode() {
		return this.areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getFullAreaName() {
		return fullAreaName;
	}

	public void setFullAreaName(String fullAreaName) {
		this.fullAreaName = fullAreaName;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public boolean isNew() {
		return this.recordId == null;
	}

}