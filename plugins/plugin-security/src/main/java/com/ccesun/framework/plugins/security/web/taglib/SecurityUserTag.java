package com.ccesun.framework.plugins.security.web.taglib;

import javax.servlet.jsp.JspException;

import com.ccesun.framework.plugins.security.SecurityTokenHolder;
import com.ccesun.framework.plugins.security.domain.SecurityToken;

public class SecurityUserTag extends BasePermissionTag {

	private static final long serialVersionUID = -1299072346487791374L;
	
	private String var = "SECURITY_USER";
	
	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	@Override
	public int doStartTag() throws JspException {
		
		SecurityToken token = SecurityTokenHolder.getSecurityToken();
		if (token != null)
			pageContext.setAttribute(var, token.getUser());
		return SKIP_BODY;
	}
}
