package com.ccesun.framework.core.web.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class BaseController {
	
	protected Log log = LogFactory.getLog(getClass());

	@Autowired
	private MessageSource messageSource;
	
	protected HttpServletRequest getHttpServletRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		return request;
	}
	
	protected HttpSession getHttpSession() {
		return getHttpServletRequest().getSession();
	}
	
	protected String getRealPath(String path) {
		return getHttpServletRequest().getSession().getServletContext().getRealPath(path);
	}
	
	protected boolean isValidId(Integer id){
		return id == null ? false : id > 0;
	}
	
	protected String getMessage(String code, Object[] args, String defaultMessage) {
		return messageSource.getMessage(code, args, defaultMessage, Locale.getDefault());
	}
	
	protected String getMessage(String code, Object[] args) {
		return getMessage(code, args, null);
	}
	
	protected String getMessage(String code) {
		return getMessage(code, null, null);
	}
	
}
