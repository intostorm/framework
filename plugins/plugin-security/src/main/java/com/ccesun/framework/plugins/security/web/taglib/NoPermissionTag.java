package com.ccesun.framework.plugins.security.web.taglib;

import javax.servlet.jsp.JspException;

import com.ccesun.framework.plugins.security.SecurityTokenHolder;
import com.ccesun.framework.plugins.security.SecurityUtils;
import com.ccesun.framework.plugins.security.domain.SecurityToken;

public class NoPermissionTag extends BasePermissionTag {

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
			return EVAL_BODY_INCLUDE;
		}

		boolean hasPermission = SecurityUtils.hasPermission(token, permCode);
		
		return hasPermission ? SKIP_BODY : EVAL_BODY_INCLUDE;
	}
}
