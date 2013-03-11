package com.ccesun.framework.plugins.security.domain;

public class SecurityUserAdapter implements ISecurityUser {

	private static final long serialVersionUID = -8787560538535168197L;

	protected String userName;
	protected String realName;
	protected String passwd;
	protected boolean available;
	
	@Override
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Override
	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	@Override
	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

}
