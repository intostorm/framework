package com.ccesun.framework.plugins.security.validatecode;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ccesun.framework.plugins.security.PluginSecurityConstants;
import com.ccesun.framework.util.NumberUtils;
import com.ccesun.framework.util.StringUtils;

public class ValidateCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private int width = 120;
	private int height = 40;
	private int codeCount = 4;
	private int lineCount = 50;
	
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		if (StringUtils.isNotBlank(config.getInitParameter("width")))
			width = NumberUtils.toInt(config.getInitParameter("width"));
		if (StringUtils.isNotBlank(config.getInitParameter("height")))
			height = NumberUtils.toInt(config.getInitParameter("height"));
		if (StringUtils.isNotBlank(config.getInitParameter("codeCount")))
			codeCount = NumberUtils.toInt(config.getInitParameter("codeCount"));
		if (StringUtils.isNotBlank(config.getInitParameter("lineCount")))
			lineCount = NumberUtils.toInt(config.getInitParameter("lineCount"));
	}



	@Override
	protected void doGet(HttpServletRequest reqeust,
			HttpServletResponse response) throws ServletException, IOException {
		// 设置响应的类型格式为图片格式
		response.setContentType("image/jpeg");
		//禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		
		HttpSession session = reqeust.getSession();
		 
		ValidateCode vCode = new ValidateCode(width, height, codeCount, lineCount);
		session.setAttribute(PluginSecurityConstants.VALIDATE_CODE, vCode.getCode());
		vCode.write(response.getOutputStream());
	}
/**
 * web.xml 添加servlet
	<servlet>
		<servlet-name>validateCodeServlet</servlet-name>
		<servlet-class>com.ccesun.framework.plugins.security.validatecode.ValidateCodeServlet</servlet-class>
		<init-param>
			<param-name>width</param-name>
			<param-value>80</param-value>
		</init-param>
		<init-param>
			<param-name>height</param-name>
			<param-value>30</param-value>
		</init-param>
		<init-param>
			<param-name>codeCount</param-name>
			<param-value>4</param-value>
		</init-param>
		<init-param>
			<param-name>lineCount</param-name>
			<param-value>40</param-value>
		</init-param>
	</servlet>	
	<servlet-mapping>
		<servlet-name>validateCodeServlet</servlet-name>
		<url-pattern>*.images</url-pattern>
	</servlet-mapping>

在地址栏输入XXX/dsna.images 测试
 */

}