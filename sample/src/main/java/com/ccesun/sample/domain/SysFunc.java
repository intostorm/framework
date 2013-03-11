package com.ccesun.sample.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ccesun.framework.core.dao.support.EntityUtils;
import com.ccesun.framework.core.dao.support.IEntity;
import com.ccesun.framework.plugins.security.domain.IPermission;

@Entity
@Table(name="sys_func")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class SysFunc implements IEntity<Integer>, IPermission {
	
	private static final long serialVersionUID = 1788783324L;
	
	/** 主键 */
	@Id
	@GeneratedValue
	@Column(name="func_id")
	private Integer funcId;
	
	/** 功能名 */
	@Column(name="func_name")
	private String funcName;
	
	/** 功能地址 */
	@Column(name="func_url")
	private String funcUrl;
	
	/** 描述 */
	@Column(name="func_remarks")
	private String funcRemarks;
	
	/** 功能组编码 */
	@Column(name="func_groupcode")
	private String funcGroupCode;
	
	/** 功能组编码 */
	@Column(name="func_order")
	private Integer funcOrder;
	
	/** 父功能 */
	@ManyToOne
	@JoinColumn(name="parent_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private SysFunc parent;
	
	public Integer getFuncId() {
		return funcId;
	}

	public void setFuncId(Integer funcId) {
		this.funcId = funcId;
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
	
	public String getFuncGroupCode() {
		return funcGroupCode;
	}

	public void setFuncGroupCode(String funcGroupCode) {
		this.funcGroupCode = funcGroupCode;
	}

	public SysFunc getParent() {
		return parent;
	}

	public void setParent(SysFunc parent) {
		this.parent = parent;
	}

	public Integer getFuncOrder() {
		return funcOrder;
	}

	public void setFuncOrder(Integer funcOrder) {
		this.funcOrder = funcOrder;
	}
	
	@Override
	public String getCode() {
		return funcId == null ? null : funcId.toString();
	}

	@Override
	public String getName() {
		return funcName;
	}

	@Override
	public String getGroupCode() {
		return funcGroupCode;
	}

	@Override
	public String getParentCode() {
		return parent == null ? null : parent.getCode();
	}

	@Override
	public String getUrl() {
		return this.funcUrl;
	}
	
	@Override
	public Integer getOrder() {
		return this.funcOrder;
	}

	@Override
	@Transient
	public boolean isNew() {
		return EntityUtils.isNew(this.funcId);
	}

}
