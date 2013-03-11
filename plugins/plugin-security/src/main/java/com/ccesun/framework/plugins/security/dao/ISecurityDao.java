package com.ccesun.framework.plugins.security.dao;

import java.util.Collection;

import com.ccesun.framework.plugins.security.domain.IPermission;
import com.ccesun.framework.plugins.security.domain.ISecurityUser;

public interface ISecurityDao {

	ISecurityUser getSecurityUser(String... username);
	
	Collection<IPermission> loadPermissions(ISecurityUser securityUser);
}
