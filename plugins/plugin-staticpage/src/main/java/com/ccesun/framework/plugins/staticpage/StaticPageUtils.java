package com.ccesun.framework.plugins.staticpage;

import org.apache.commons.lang.StringUtils;

public class StaticPageUtils {

	public static final String REQUEST_EXT = ".do";
	public static final String REQUEST_STATIC_EXT = ".html";
	
	public static String converToHtmlFileName(String requestURI) {
		
		if (requestURI.endsWith(REQUEST_STATIC_EXT))
			return requestURI;
		
		StringBuilder result = new StringBuilder();
		
		String URI = StringUtils.substringBefore(requestURI, "?");
		URI = StringUtils.substringBefore(URI, REQUEST_EXT);
		
		String params = StringUtils.substringAfter(requestURI, "?");
		params = StringUtils.replace(params, "=", "!");
		params = StringUtils.replace(params, "&", "+");
		
		result.append(URI);
		if (!StringUtils.isBlank(params)) {
			result.append("-");
			result.append(params);
		}
		result.append(REQUEST_STATIC_EXT);
		
		return result.toString();
	}
	
	public static String converFromHtmlFileName(String requestURI) {
		
		if (requestURI.endsWith(REQUEST_EXT))
			return requestURI;
		
		StringBuilder result = new StringBuilder();
		
		String URI = StringUtils.substringBefore(requestURI, "-");
		
		String params = StringUtils.substringAfter(requestURI, "-");
		params = StringUtils.substringBefore(params, REQUEST_STATIC_EXT);
		params = StringUtils.replace(params, "!", "=");
		params = StringUtils.replace(params, "+", "&");
		
		result.append(URI);
		result.append(REQUEST_EXT);
		if (!StringUtils.isBlank(params)) {
			result.append("?");
			result.append(params);
		}
		
		return result.toString();
	}
}
