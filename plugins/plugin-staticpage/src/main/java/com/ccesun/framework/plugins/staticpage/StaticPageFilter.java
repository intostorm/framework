package com.ccesun.framework.plugins.staticpage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

public class StaticPageFilter implements Filter {
	
	protected Log logger = LogFactory.getLog(StaticPageFilter.class);
	
	//protected String excludes;
	
	//protected List<String> excludeList;
	
	protected String staticPageRoot;
	
	protected static final String STATIC_PAGE_ROOT = "/staticpage";
	
	public String getStaticPageRoot() {
		return StringUtils.isBlank(staticPageRoot) ? STATIC_PAGE_ROOT : staticPageRoot;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Assert.notNull(filterConfig, "FilterConfig must not be null");
		if (logger.isDebugEnabled()) {
			logger.debug("Initializing filter '" + filterConfig.getFilterName() + "'");
		}
		
		//this.excludes = filterConfig.getInitParameter("excludes");
		//String[] excludeArray = StringUtils.split(this.excludes, ",");
		//this.excludeList = Arrays.asList(excludeArray);
		
		this.staticPageRoot = filterConfig.getInitParameter("staticPageRoot");
		
		if (logger.isDebugEnabled()) {
			logger.debug("Filter '" + filterConfig.getFilterName() + "' configured successfully");
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest  = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse  = (HttpServletResponse) response;
		
		String uri = httpServletRequest.getRequestURI();
		String contextPath = httpServletRequest.getContextPath();
		if (contextPath != null && contextPath.length() > 0 )
			uri = uri.substring(contextPath.length());
		
		/*if (isExclude(uri)) {
			chain.doFilter(httpServletRequest, httpServletResponse);
			return;
		}*/
		
		String encoding = "UTF-8";
		
		String tempaltePath = StaticPageUtils.convertFromHtmlFileName(uri);
		String realPath = httpServletRequest.getSession().getServletContext().getRealPath(getStaticPageRoot());

		String htmlName = StaticPageUtils.convertToHtmlFileName(uri);
		String cacheFileName = realPath + File.separator + htmlName;
		
		logger.debug("cacheFileName = " + cacheFileName);
		File cacheFile = new File(cacheFileName);
		
		boolean load = true;
		
		if (cacheFile.exists()) {
			load = false;
		}
		
		if (load) {
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			final ServletOutputStream stream = new ServletOutputStream() {

				@Override
				public void write(byte[] data, int offset, int length) throws IOException {
					os.write(data, offset, length);
				}
				
				@Override
				public void write(int b) throws IOException {
					os.write(b);
				}
				
			};
			
			final PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, encoding));
			
			HttpServletResponse rep = new HttpServletResponseWrapper(httpServletResponse) {
				public ServletOutputStream getOutputStream() {
					return stream;
				}
				
				public PrintWriter getWriter() {
					return pw;
				}
			};
			
			logger.debug("StaticPageFilter RequestDispatcher = " + tempaltePath);
			
			RequestDispatcher dispatcher = httpServletRequest.getRequestDispatcher(tempaltePath);
			
			dispatcher.include(httpServletRequest, rep);
			pw.flush();
			
			FileOutputStream fos = null;
			
			try {
				if (os.size() == 0) {
					httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "");
				}
				else {
					File directory = cacheFile.getParentFile();
					if (!directory.exists())
						directory.mkdirs();
						
					fos = new FileOutputStream(cacheFileName);
					os.writeTo(fos);
					dispatcher = httpServletRequest.getRequestDispatcher(STATIC_PAGE_ROOT + "/" + htmlName);
					dispatcher.include(httpServletRequest, httpServletResponse);
				}
			} 
			finally {
				if (fos != null) {
					fos.close();
				}
			}
		}
		else {
			RequestDispatcher dispatcher = httpServletRequest.getRequestDispatcher(STATIC_PAGE_ROOT + "/" + htmlName);
			dispatcher.include(httpServletRequest, httpServletResponse);
			
		}
	}
	
	/*private boolean isExclude(String uri) {
		
		for (String exclude : excludeList) {
			if (uri.endsWith(exclude))
				return true;
		}
		return false;
	}*/

	/*protected String simpleURLReWrite(HttpServletRequest request, String uri) throws ServletException, IOException {
		
		if (uri.endsWith(".html")) {
			uri = uri.substring(0, uri.length() - 5) + ".do";
		
		uri = StringUtils.replace(uri, "-", "?");
		uri = StringUtils.replace(uri, "!", "=");
		uri = StringUtils.replace(uri, "+", "&");
		
		logger.debug("StaticPageFilter get uri = " + uri);
		
		return uri;
	}
	
	private String getHtmlFileName(HttpServletRequest request, String uri) throws ServletException, IOException {
		
		uri = StringUtils.replace(uri, "?", "-");
		uri = StringUtils.replace(uri, "=", "!");
		uri = StringUtils.replace(uri, "&", "+");
		if (uri.endsWith(".do"))
			uri = uri.substring(0, uri.length() - 3);
		
		if (!uri.endsWith(".html"))
			uri += ".html";
		
		return uri;
	}*/

	@Override
	public void destroy() {
		
	}

}
