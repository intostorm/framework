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
@Table(name="sys_role_func")
public class SysRoleFunc implements IEntity<Integer> {
	
	private static final long serialVersionUID = 1775429012L;
	
	/** 主键 */
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	/** 角色ID */
	@Column(name="role_id")
	private Integer roleId;
	
	/** 功能ID */
	@Column(name="func_id")
	private String funcId;
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	public Integer getRoleId() {
		return roleId;
	}
	
	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}
	
	public String getFuncId() {
		return funcId;
	}
	
	@Override
	@Transient
	public boolean isNew() {
		return EntityUtils.isNew(this.id);
	}
}
