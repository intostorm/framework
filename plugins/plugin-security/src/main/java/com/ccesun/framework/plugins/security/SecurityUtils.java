package com.ccesun.framework.plugins.security;

import java.util.Collection;

import com.ccesun.framework.plugins.security.domain.IPermission;
import com.ccesun.framework.plugins.security.domain.SecurityToken;
import com.ccesun.framework.util.StringUtils;

public class SecurityUtils {

	public static boolean hasURIPermission(SecurityToken token, String requestURI) {
		
		if (StringUtils.isBlank(requestURI))
			return true; 
		
		Collection<IPermission> permissions = token.getPermissions();
		
		for (IPermission permission : permissions) {
			if (requestURI.startsWith(permission.getUrl()))
				return true;
		}
		
		return false;
	}
	
	public static boolean hasPermission(SecurityToken token, String permCode) {
		
		if (StringUtils.isBlank(permCode))
			return true;
		
		Collection<IPermission> permissions = token.getPermissions();
		
		for (IPermission permission : permissions) {
			if (permCode.equals(permission.getCode()))
				return true;
		}
		
		return false;
	}
}
