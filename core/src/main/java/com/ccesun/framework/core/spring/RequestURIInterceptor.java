package com.ccesun.framework.core.spring;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class RequestURIInterceptor extends HandlerInterceptorAdapter {
	
	private final Log logger = LogFactory.getLog(getClass());
	
	public static final String KEY_REQUESTURI = "REQUEST_URI";
	public static final String KEY_REQUESTURI_QUERYSTR = "REQUEST_URI_QUERYSTR";
	public static final String KEY_RELATIVE_REQUESTURI = "RELATIVE_REQUESTURI";
	public static final String KEY_RELATIVE_REQUESTURI_QUERYSTR = "RELATIVE_REQUESTURI_QUERYSTR";
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		if(!(handler instanceof HandlerMethod)){
			return true; 
		} 
		
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Controller - %s#%s", handlerMethod.getBean().getClass().getName(), handlerMethod.getMethod().getName()));
		}
		
		String requestURI = request.getRequestURI();
		String queryStr = request.getQueryString() == null ? "" : "?" + URLDecoder.decode(request.getQueryString(), "UTF-8");
		
		//if (requestURI.startsWith(request.getContextPath()))
		//	requestURI = requestURI.substring(request.getContextPath().length());
		if (requestURI.endsWith("/"))
			requestURI = requestURI.substring(0, requestURI.length() - 1);
		
		String contextPath = request.getContextPath();
		String relativeRequestURI = requestURI.substring(contextPath.length());
		
		request.setAttribute(KEY_REQUESTURI, requestURI);
		request.setAttribute(KEY_REQUESTURI_QUERYSTR, requestURI + queryStr);
		request.setAttribute(KEY_RELATIVE_REQUESTURI, relativeRequestURI);
		request.setAttribute(KEY_RELATIVE_REQUESTURI_QUERYSTR, relativeRequestURI + queryStr);
		
		return true;
	}

}
