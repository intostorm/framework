package com.ccesun.framework.core.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class RequestURIInterceptor extends HandlerInterceptorAdapter {
	
	private final Log logger = LogFactory.getLog(getClass());
	
	public static final String KEY_REQUESTURI = "REQUEST_URI";
	public static final String KEY_RELATIVE_REQUESTURI = "RELATIVE_REQUESTURI";
	
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
		
		//if (requestURI.startsWith(request.getContextPath()))
		//	requestURI = requestURI.substring(request.getContextPath().length());
		if (requestURI.endsWith("/"))
			requestURI = requestURI.substring(0, requestURI.length() - 1);
		
		request.setAttribute(KEY_REQUESTURI, requestURI);
		
		String contextPath = request.getContextPath();
		String relativeRequestURI = requestURI.substring(contextPath.length());
		
		request.setAttribute(KEY_RELATIVE_REQUESTURI, relativeRequestURI);
		
		return true;
	}

}
