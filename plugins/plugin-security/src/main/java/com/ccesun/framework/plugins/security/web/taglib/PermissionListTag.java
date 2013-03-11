package com.ccesun.framework.plugins.security.web.taglib;

import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ccesun.framework.plugins.security.dao.IPermissionDao;
import com.ccesun.framework.plugins.security.domain.IPermission;

public class PermissionListTag extends TagSupport {

	private static final long serialVersionUID = 3498319010712403041L;

    private String groupCode;
    
    private String parentCode;
    
    private String var;
    
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
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
		Collection<IPermission> permissionList = permissionDao.findPermissionsByGroupCodeAndParentCode(groupCode, parentCode);
		
		pageContext.setAttribute(var, permissionList);
		return SKIP_BODY;
    }

}
