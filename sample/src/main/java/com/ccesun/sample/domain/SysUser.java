package com.ccesun.sample.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ccesun.framework.core.dao.support.EntityUtils;
import com.ccesun.framework.core.dao.support.IEntity;
import com.ccesun.framework.plugins.security.domain.ISecurityUser;

@Entity
@Table(name="sys_user")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class SysUser implements IEntity<Integer>, ISecurityUser {
	
	private static final long serialVersionUID = 1833351600L;
	
	/** 主键 */
	@Id
	@GeneratedValue
	@Column(name="user_id")
	private Integer userId;
	
	/** 用户名 */
	@Column(name="user_name")
	private String userName;
	
	/** 姓名 */
	@Column(name="real_name")
	private String realName;
	
	/** 密码 */
	@Column(name="passwd")
	private String passwd;
	
	/** 是否可用 */
	@Column(name="is_available")
	private Boolean available;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "sys_user_role",
	    joinColumns = @JoinColumn(name = "user_id"),
	    inverseJoinColumns =  @JoinColumn(name = "role_id"))
	private List<SysRole> roles;
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public String getRealName() {
		return realName;
	}
	
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	public String getPasswd() {
		return passwd;
	}
	
	@Override
	@Transient
	public boolean isNew() {
		return EntityUtils.isNew(this.userId);
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	@Override
	public boolean isAvailable() {
		return available == null ? false : available.booleanValue();
	}

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	
}
