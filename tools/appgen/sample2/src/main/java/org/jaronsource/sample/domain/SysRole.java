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
@Table(name="sys_role")
public class SysRole implements IEntity<Integer> {
	
	private static final long serialVersionUID = 270783556L;
	
	/** 主键 */
	@Id
	@GeneratedValue
	@Column(name="role_id")
	private Integer roleId;
	
	/** 角色名 */
	@Column(name="role_name")
	private String roleName;
	
	/** 描述 */
	@Column(name="role_remarks")
	private String roleRemarks;
	
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	public Integer getRoleId() {
		return roleId;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleRemarks(String roleRemarks) {
		this.roleRemarks = roleRemarks;
	}
	
	public String getRoleRemarks() {
		return roleRemarks;
	}
	
	@Override
	@Transient
	public boolean isNew() {
		return EntityUtils.isNew(this.roleId);
	}
}
