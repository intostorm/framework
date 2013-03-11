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
@Table(name="sys_func")
public class SysFunc implements IEntity<String> {
	
	private static final long serialVersionUID = 799616660L;
	
	/** 主键 */
	@Id
	@GeneratedValue
	@Column(name="func_id")
	private String funcId;
	
	/** 功能名 */
	@Column(name="func_name")
	private String funcName;
	
	/** 功能地址 */
	@Column(name="func_url")
	private String funcUrl;
	
	/** 描述 */
	@Column(name="func_remarks")
	private String funcRemarks;
	
	/** 功能级别 */
	@Column(name="func_level")
	private Integer funcLevel;
	
	/** 父功能 */
	@Column(name="parent_id")
	private Integer parentId;
	
	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}
	
	public String getFuncId() {
		return funcId;
	}
	
	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}
	
	public String getFuncName() {
		return funcName;
	}
	
	public void setFuncUrl(String funcUrl) {
		this.funcUrl = funcUrl;
	}
	
	public String getFuncUrl() {
		return funcUrl;
	}
	
	public void setFuncRemarks(String funcRemarks) {
		this.funcRemarks = funcRemarks;
	}
	
	public String getFuncRemarks() {
		return funcRemarks;
	}
	
	public void setFuncLevel(Integer funcLevel) {
		this.funcLevel = funcLevel;
	}
	
	public Integer getFuncLevel() {
		return funcLevel;
	}
	
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public Integer getParentId() {
		return parentId;
	}
	
	@Override
	@Transient
	public boolean isNew() {
		return EntityUtils.isNew(this.funcId);
	}
}
