package com.ccesun.framework.plugins.security.web.taglib;

import javax.servlet.jsp.JspException;

import com.ccesun.framework.plugins.security.SecurityTokenHolder;
import com.ccesun.framework.plugins.security.SecurityUtils;
import com.ccesun.framework.plugins.security.domain.SecurityToken;

public class NoPermissionTag extends BasePermissionTag {

	private static final long serialVersionUID = 2314845771987747293L;
	
	private String permId;

	public String getPermId() {
		return permId;
	}

	public void setPermId(String permId) {
		this.permId = permId;
	}

	@Override
	public int doStartTag() throws JspException {
		SecurityToken token = SecurityTokenHolder.getSecurityToken();
		if (token == null) {
			return EVAL_BODY_INCLUDE;
		}

		boolean hasPermission = SecurityUtils.hasPermission(token, permId);
		
		return hasPermission ? SKIP_BODY : EVAL_BODY_INCLUDE;
	}
}
