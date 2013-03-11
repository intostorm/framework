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
@Table(name="sys_user_role")
public class SysUserRole implements IEntity<Integer> {
	
	private static final long serialVersionUID = 1416429536L;
	
	/** 主键 */
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	/** 用户ID */
	@Column(name="user_id")
	private Integer userId;
	
	/** 角色ID */
	@Column(name="role_id")
	private Integer roleId;
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	public Integer getRoleId() {
		return roleId;
	}
	
	@Override
	@Transient
	public boolean isNew() {
		return EntityUtils.isNew(this.id);
	}
}
