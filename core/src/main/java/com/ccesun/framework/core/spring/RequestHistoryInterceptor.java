package com.ccesun.framework.core.spring;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class RequestHistoryInterceptor extends HandlerInterceptorAdapter {
	
	private final Log log = LogFactory.getLog(getClass());
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		if(!(handler instanceof HandlerMethod)){
			return true; 
		} 
		
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		
		RequestHistory typeAnnotation = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), RequestHistory.class);
		
		if (typeAnnotation != null) {
			
			String requestURI = request.getRequestURI();
			
			if (requestURI.startsWith(request.getContextPath()))
				requestURI = requestURI.substring(request.getContextPath().length());
			if (requestURI.endsWith("/"))
				requestURI = requestURI.substring(0, requestURI.length() - 1);
			
			String historyName = requestURI;
			
			Map<String, Object> parameterMap = new HashMap<String, Object>(request.getParameterMap());
			
			com.ccesun.framework.core.requesthistory.RequestHistory.getInstance().addEntry(historyName, requestURI, parameterMap);
			
			if (log.isDebugEnabled())
				log.debug(String.format("%s:%s was added to RequestHistory", historyName, parameterMap));
		}
		
		return true;
	}

}
