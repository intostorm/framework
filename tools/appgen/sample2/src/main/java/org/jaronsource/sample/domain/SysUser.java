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
@Table(name="sys_user")
public class SysUser implements IEntity<Integer> {
	
	private static final long serialVersionUID = 1144636899L;
	
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
	private Integer isAvailable;
	
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
	
	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	public Integer getIsAvailable() {
		return isAvailable;
	}
	
	@Override
	@Transient
	public boolean isNew() {
		return EntityUtils.isNew(this.userId);
	}
}
