package com.ccesun.framework.plugins.security.web.taglib;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ccesun.framework.plugins.security.dao.IPermissionDao;
import com.ccesun.framework.plugins.security.domain.IPermission;

public class PermissionTag extends TagSupport {

	private static final long serialVersionUID = 3498319010712403041L;

    private String code;
    
    private String var;
    
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		ServletContext sc = request.getSession().getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(sc);
		
		IPermissionDao permissionDao = wac.getBean(IPermissionDao.class);
		IPermission permission = permissionDao.findPermissionByCode(getCode());
		
		pageContext.setAttribute(var, permission);
		return SKIP_BODY;
    }

}
