package com.ccesun.framework.plugins.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ccesun.framework.plugins.security.dao.ISecurityDao;
import com.ccesun.framework.plugins.security.domain.IPermission;
import com.ccesun.framework.plugins.security.domain.ISecurityUser;
import com.ccesun.framework.plugins.security.domain.SecurityToken;
import com.ccesun.framework.util.Base64;

public class SecurityTokenHolder {

	public static final String SECURITY_TOKEN_KEY = "SECURITY_TOKEN_KEY";
	public static final String SECURITY_TOKEN = "SECURITY_TOKEN";
	
	private static Log logger = LogFactory.getLog(SecurityTokenHolder.class);
	/*
	public static SecurityToken getSecurityToken() {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		
		SecurityToken token = (SecurityToken) session.getAttribute(SECURITY_TOKEN_KEY);
		if (token == null) {
		
			Cookie[] cookies = request.getCookies() == null ? new Cookie[] {} : request.getCookies(); 
			
			for (Cookie cookie : cookies) {
				if (SECURITY_TOKEN.equals(cookie.getName())) {
					
					token = new SecurityToken();
					//String cookieValue = cookie.getValue();
					//String jsonTokenStr = new String(Base64.base64ToByteArray(cookieValue));
					//JSONObject jsonToken = JSONObject.fromObject(jsonTokenStr);
					
					//JSONObject jsonSecurityUser = jsonToken.getJSONObject("user");
					//jsonSecurityUser.remove("roles");
					
					Class<?> securityUserClass = getSecurityUserClass();
					
					//try {
					//	BeanUtils.copyProperty(token, "user", JSONObject.toBean(jsonSecurityUser, securityUserClass));
					//} catch (IllegalAccessException e) {
					//} catch (InvocationTargetException e) {
					//}
					
					//JSONArray jsonPermissions = jsonToken.getJSONArray("permissions");
					//Iterator<JSONObject> iter = jsonPermissions.iterator();
					//while (iter.hasNext()) {
					//	JSONObject jsonPermission = iter.next();
					//	IPermission permission = (IPermission) JSONObject.toBean(jsonPermission, permissionClass);
					//	token.addPermission(permission);
					//}
					
					String cookieValue = cookie.getValue();
					String jsonUserStr = new String(Base64.base64ToByteArray(cookieValue));
					JSONObject jsonUser = JSONObject.fromObject(jsonUserStr);
					ISecurityUser user = (ISecurityUser) JSONObject.toBean(jsonUser, securityUserClass);
					
					ServletContext sc = session.getServletContext();
					WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(sc);
					ISecurityDao securityDao = wac.getBean(ISecurityDao.class);
					Collection<IPermission> permissions = securityDao.loadPermissions(user);
					
					token.setUser(user);
					token.setPermissions(permissions);
					
					break;
				}
			}
			
			session.setAttribute(SECURITY_TOKEN_KEY, token);
		}
		
		return token;
	}
	*/
	
	public static SecurityToken getSecurityToken() {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		
		SecurityToken token = (SecurityToken) session.getAttribute(SECURITY_TOKEN_KEY);
		if (token == null) {
		
			Cookie[] cookies = request.getCookies() == null ? new Cookie[] {} : request.getCookies(); 
			
			for (Cookie cookie : cookies) {
				if (SECURITY_TOKEN.equals(cookie.getName())) {
					
					token = new SecurityToken();
					//String cookieValue = cookie.getValue();
					//String jsonTokenStr = new String(Base64.base64ToByteArray(cookieValue));
					//JSONObject jsonToken = JSONObject.fromObject(jsonTokenStr);
					
					//JSONObject jsonSecurityUser = jsonToken.getJSONObject("user");
					//jsonSecurityUser.remove("roles");
					
					//Class<?> securityUserClass = getSecurityUserClass();
					
					//try {
					//	BeanUtils.copyProperty(token, "user", JSONObject.toBean(jsonSecurityUser, securityUserClass));
					//} catch (IllegalAccessException e) {
					//} catch (InvocationTargetException e) {
					//}
					
					//JSONArray jsonPermissions = jsonToken.getJSONArray("permissions");
					//Iterator<JSONObject> iter = jsonPermissions.iterator();
					//while (iter.hasNext()) {
					//	JSONObject jsonPermission = iter.next();
					//	IPermission permission = (IPermission) JSONObject.toBean(jsonPermission, permissionClass);
					//	token.addPermission(permission);
					//}
					
					String cookieValue = cookie.getValue();
					ISecurityUser user = null;
					try {
						ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(Base64.base64ToByteArray(cookieValue)));
						user = (ISecurityUser) ois.readObject();
					} catch (Exception e) {
						if(logger.isDebugEnabled()) {
							logger.debug("User info not found in cookie", e);
						}
					} 
					
					ServletContext sc = session.getServletContext();
					WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(sc);
					ISecurityDao securityDao = wac.getBean(ISecurityDao.class);
					Collection<IPermission> permissions = securityDao.loadPermissions(user);
					
					token.setUser(user);
					token.setPermissions(permissions);
					
					break;
				}
			}
			
			session.setAttribute(SECURITY_TOKEN_KEY, token);
		}
		
		return token;
	}
	
	/*
	private static Class<?> getSecurityUserClass() {
		Class<?> result = null;
		String className = AppContext.getInstance().getString("plugin.security.securityUserClassName");
		if (className != null) {
			try {
				result = Class.forName(className);
			} catch (ClassNotFoundException e) {
			}
		}
		if (result == null)
			result = SecurityUserAdapter.class;
		
		return result;
	}
	*/
	//private static Class<?> getPermissionClass() {
	//	Class<?> result = null;
	//	String className = AppContext.getInstance().getString("plugin.security.permissionClassName");
	//	if (className != null) {
	//		try {
	//			result = Class.forName(className);
	//		} catch (ClassNotFoundException e) {
	//		}
	//	}
	//	if (result == null)
	//		result = PermissionAdapter.class;
		
	//	return result;
	//}

	public static void cleanSecurityToken(HttpServletResponse response) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		request.getSession().setAttribute(SECURITY_TOKEN_KEY, null);
		
		Cookie[] cookies = request.getCookies() == null ? new Cookie[] {} : request.getCookies(); 
		
		for (Cookie cookie : cookies) {
			if (SECURITY_TOKEN.equals(cookie.getName())) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				break;
			}
		}
		
	}
	
	/*
	public static void setSecurityToken(HttpServletResponse response, SecurityToken token, boolean rememberme) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		request.getSession().setAttribute(SECURITY_TOKEN_KEY, token);
		
		if (rememberme) {
			ISecurityUser user = token.getUser();
			String jsonUser = Base64.byteArrayToBase64(JSONObject.fromObject(user).toString().getBytes());
			Cookie cookie = new Cookie(SECURITY_TOKEN, jsonUser);
			cookie.setMaxAge(60 * 60 * 24 * 365);
			response.addCookie(cookie);
		}
	}
	*/
	
	public static void setSecurityToken(HttpServletResponse response, SecurityToken token, boolean rememberme) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		request.getSession().setAttribute(SECURITY_TOKEN_KEY, token);
		
		if (rememberme) {
			ISecurityUser user = token.getUser();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(user);
			} catch (IOException e) {
				if(logger.isDebugEnabled()) {
					logger.debug("could not write user info to cookie", e);
				}
			}
			String jsonUser = Base64.byteArrayToBase64(baos.toByteArray());
			Cookie cookie = new Cookie(SECURITY_TOKEN, jsonUser);
			cookie.setMaxAge(60 * 60 * 24 * 365);
			response.addCookie(cookie);
		}
	}
}
