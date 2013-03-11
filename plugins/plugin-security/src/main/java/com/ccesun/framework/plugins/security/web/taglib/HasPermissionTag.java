package com.ccesun.framework.plugins.security.web.taglib;

import javax.servlet.jsp.JspException;

import com.ccesun.framework.plugins.security.SecurityTokenHolder;
import com.ccesun.framework.plugins.security.SecurityUtils;
import com.ccesun.framework.plugins.security.domain.SecurityToken;

public class HasPermissionTag extends BasePermissionTag {

	private static final long serialVersionUID = 2314845771987747293L;
	
	private String permCode;

	public String getPermCode() {
		return permCode;
	}

	public void setPermCode(String permCode) {
		this.permCode = permCode;
	}

	@Override
	public int doStartTag() throws JspException {
		
		SecurityToken token = SecurityTokenHolder.getSecurityToken();
		if (token == null) {
			return SKIP_BODY;
		}
		
		boolean hasPermission = SecurityUtils.hasPermission(token, permCode);
		
		return hasPermission ? EVAL_BODY_INCLUDE : SKIP_BODY;
	}
}
