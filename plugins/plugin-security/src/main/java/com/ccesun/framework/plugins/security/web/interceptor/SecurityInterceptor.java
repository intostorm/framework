package com.ccesun.framework.plugins.security.web.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ccesun.framework.plugins.security.SecurityTokenHolder;
import com.ccesun.framework.plugins.security.SecurityUtils;
import com.ccesun.framework.plugins.security.domain.SecurityToken;

/**
 * <interceptors>
 *		<beans:bean class="com.ccesun.framework.plugins.security.web.interceptor.SecurityInterceptor">
 *			<beans:property name="loginUrl" value="/userInfo/doLogin" />
 *			<beans:property name="excludesPath">
 *				<beans:list>
 *					<beans:value type="java.lang.String">/userInfo/doLogin</beans:value>
 *					<beans:value type="java.lang.String">/area/loadNodes</beans:value>
 *					<beans:value type="java.lang.String">/rsGrid/ajaxFindByAreaCode</beans:value>
 *				</beans:list>
 *			</beans:property>
 *		</beans:bean>
 *	</interceptors>
 * @author Jaron
 *
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {
	
	//private final Log log = LogFactory.getLog(SecurityInterceptor.class);

	private List<String> excludesPath;
	
	private String loginUrl = "/login";
	
	private String noPermUrl = "/noPermUrl";
	
	protected boolean containInList(List<String> pathList, String path){
		if(pathList == null || pathList.isEmpty()){
			return false;
		}
		for(String specPath : pathList){
			if(StringUtils.startsWith(path, specPath)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		if(!(handler instanceof HandlerMethod)){
			return true; 
		} 
		
		SecurityToken token = SecurityTokenHolder.getSecurityToken();
		
		boolean userLogin = token != null;
		String contextPath = request.getContextPath();
		String path = request.getRequestURI();
		path = path.substring(contextPath.length());
		
		if(excludesPath != null && containInList(excludesPath, path)){
			return true;
		}
		
		//用户未登录
		if(!userLogin){
			response.sendRedirect(contextPath + loginUrl);
			return false;
		}
		
		//用户已登录
		if (SecurityUtils.hasURIPermission(token, path))
			return true;
		
		response.sendRedirect(contextPath + noPermUrl);
		return false;
		
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public List<String> getExcludesPath() {
		return excludesPath;
	}

	public void setExcludesPath(List<String> excludesPath) {
		this.excludesPath = excludesPath;
	}

	public String getNoPermUrl() {
		return noPermUrl;
	}

	public void setNoPermUrl(String noPermUrl) {
		this.noPermUrl = noPermUrl;
	}

}
