package com.ccesun.framework.core.web.taglib;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class MethodInvokorTag extends TagSupport {

	private static final long serialVersionUID = 8077035430436042219L;
	
	private static final Log logger = LogFactory.getLog(MethodInvokorTag.class);
	
	private String className;
	private String methodName;
	private String var;
    
	ThreadLocal<List<MIParam>> miParamThreadLocal = new ThreadLocal<List<MIParam>>();
	
    public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public int doStartTag() throws JspException {
		miParamThreadLocal.set(null);
		return EVAL_BODY_INCLUDE;
	}
	
	public void addParam(MIParam miParam) {
		List<MIParam> params = miParamThreadLocal.get();
		if (params == null) {
			params = new ArrayList<MIParam>();
			miParamThreadLocal.set(params);
		}
		params.add(miParam);
	}
	
	public int doEndTag() throws JspException {
		ServletContext sc = pageContext.getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(sc);
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new JspException(String.format("class with className: %s not found", className));
		}
		
		Object service = wac.getBean(clazz);
		if (service == null)
			throw new JspException(String.format("service with className: %s not found", className));
		
		Class<?>[] paramTypes = getParamTypes();
		Object[] paramValues = getParamValues();
		Method targetMethod = ReflectionUtils.findMethod(clazz, methodName, paramTypes);
		Object result = null;
		try {
			result = ReflectionUtils.invokeMethod(targetMethod, service, paramValues);
		} catch (Exception e) { 
			if (logger.isDebugEnabled())
				logger.debug("Invode service method error", e);
		}
        pageContext.setAttribute(var, result);
        return SKIP_BODY;
    }

	private Class<?>[] getParamTypes() {
		List<MIParam> params = miParamThreadLocal.get();
		params = params == null ? new ArrayList<MIParam>() : params;
		MIParam[] paramArray = params.toArray(new MIParam[0]);
		Class<?>[] result = new Class<?>[paramArray.length];
		for (int i = 0; i < paramArray.length; i++) {
			try {
				result[i] = Class.forName(paramArray[i].getType());
			} catch (ClassNotFoundException e) {
			}
		}
		return result;
	}

	private Object[] getParamValues() {
		List<MIParam> params = miParamThreadLocal.get();
		params = params == null ? new ArrayList<MIParam>() : params;
		MIParam[] paramArray = params.toArray(new MIParam[0]);
		Object[] result = new Object[paramArray.length];
		for (int i = 0; i < paramArray.length; i++) {
			result[i] = paramArray[i].getValue();
		}
		return result;
	}
}