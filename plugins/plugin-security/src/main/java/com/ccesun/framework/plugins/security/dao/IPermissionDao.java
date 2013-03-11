package com.ccesun.framework.plugins.security.dao;

import java.util.Collection;

import com.ccesun.framework.plugins.security.domain.IPermission;

public interface IPermissionDao {

	IPermission findPermissionByCode(String code);
	
	Collection<IPermission> findPermissionsByGroupCodeAndParentCode(String groupCode, String parentCode);
}
