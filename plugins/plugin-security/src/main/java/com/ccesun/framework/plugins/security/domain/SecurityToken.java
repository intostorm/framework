package com.ccesun.framework.plugins.security.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class SecurityToken implements Serializable {

	private static final long serialVersionUID = -992044511058508969L;

	private ISecurityUser user;
	
	private Collection<IPermission> permissions = new ArrayList<IPermission>();

	public SecurityToken() {}
	
	public SecurityToken(ISecurityUser user, Collection<IPermission> permissions) {
		this.user = user;
		this.permissions = permissions;
	}

	public void setUser(ISecurityUser user) {
		this.user = user;
	}
	
	public ISecurityUser getUser() {
		return user;
	}

	public void setPermissions(Collection<IPermission> permissions) {
		this.permissions = permissions;
	}
	
	public Collection<IPermission> getPermissions() {
		return permissions;
	}
	
	public void addPermission(IPermission permission) {
		this.permissions.add(permission);
	}
}
