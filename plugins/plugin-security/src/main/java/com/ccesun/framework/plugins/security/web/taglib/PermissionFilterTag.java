package com.ccesun.framework.plugins.security.web.taglib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.jsp.JspException;

import com.ccesun.framework.plugins.security.SecurityTokenHolder;
import com.ccesun.framework.plugins.security.domain.IPermission;
import com.ccesun.framework.plugins.security.domain.SecurityToken;
import com.ccesun.framework.util.StringUtils;

public class PermissionFilterTag extends BasePermissionTag implements Comparator<IPermission> {

	private static final long serialVersionUID = 2703218148455048750L;

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
	
	@Override
	public int doStartTag() throws JspException {
		LinkedList<IPermission> result = new LinkedList<IPermission>();
		
		if(StringUtils.isBlank(this.var)){
			this.var = "permissions";
		}
		
		this.pageContext.setAttribute(this.var, result);
		
		SecurityToken token = SecurityTokenHolder.getSecurityToken();
		if (token == null) {
			return SKIP_BODY;
		}
		
		Collection<IPermission> permissions = token.getPermissions();
		List<IPermission> tmpResult = new ArrayList<IPermission>(permissions);
		
		if (!StringUtils.isBlank(groupCode)) {
			List<IPermission> tmpResult2 = new ArrayList<IPermission>();
			for (IPermission permission : tmpResult) {
				if (groupCode.equals(permission.getGroupCode())) {
					tmpResult2.add(permission);
				}
			}
			Collections.sort(tmpResult2, this);
			tmpResult = tmpResult2;
		}
		
		if (!StringUtils.isBlank(parentCode)) {
			List<IPermission> tmpResult2 = new ArrayList<IPermission>();
			for (IPermission permission : tmpResult) {
				if (parentCode.equals(permission.getParentCode())) {
					tmpResult2.add(permission);
				}
			}
			Collections.sort(tmpResult, this);
			tmpResult = tmpResult2;
		}
		
		pageContext.setAttribute(var, tmpResult);
		
		return SKIP_BODY;
	}

	@Override
	public int compare(IPermission o1, IPermission o2) {
		if (o1.getOrder() == null && o2.getOrder() == null)
			return 0;
		if (o1.getOrder() != null && o2.getOrder() == null)
			return 1;
		if (o1.getOrder() == null && o2.getOrder() != null)
			return -1;
		else
			return o1.getOrder().compareTo(o2.getOrder());
	}
}
