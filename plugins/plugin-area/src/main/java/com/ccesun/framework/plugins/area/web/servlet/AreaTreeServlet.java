package com.ccesun.framework.plugins.area.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ccesun.framework.core.AppContext;
import com.ccesun.framework.core.web.AjaxResult;
import com.ccesun.framework.plugins.area.AreaConstants;
import com.ccesun.framework.plugins.area.AreaHelper;
import com.ccesun.framework.plugins.area.domain.Area;

public class AreaTreeServlet extends HttpServlet {

	private static final long serialVersionUID = -5730813353528541639L;
	
	private static final Log logger = LogFactory.getLog(AreaTreeServlet.class);
	
	@Override
	protected void service(HttpServletRequest request, 
						   HttpServletResponse response) 
						   throws ServletException, IOException {
		
		String m = request.getParameter("m");
		if ("loadNodes".equals(m)) {
			doLoadNodes(request, response);
			return;
		}
		
		if ("getParentNode".equals(m)) {
			doGetParentNode(request, response);
			return;
		}
		
		throw new IllegalArgumentException("param m not found.");
		
	}

	private void doGetParentNode(HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		AreaHelper areaHelper = wac.getBean(AreaHelper.class);
		
		String areaCode = request.getParameter("areaCode");
		
		if(areaCode == null ){
			AjaxResult result = AjaxResult.fail("request parameter [areaCode] not found!");
			outputResult(response, result);
			return;
		}
		
		HttpSession session = request.getSession();
		String userRootAreaCode = (String) session.getAttribute(AreaConstants.KEY_USER_ROOT_AREA_CODE);
		
		if (StringUtils.isBlank(userRootAreaCode))
			userRootAreaCode = AppContext.getInstance().getString("plugin.area.rootAreaCode");
		
		String parentAreaCode = areaHelper.getParentAreaCode(areaCode);
		
		if (StringUtils.isBlank(parentAreaCode))
			parentAreaCode = userRootAreaCode;
		
		if (parentAreaCode.startsWith(userRootAreaCode)) {
			Area parentArea = areaHelper.lookupArea(parentAreaCode);
			Collection<Area> resultList =  new ArrayList<Area>();
			resultList.add(parentArea);
			AjaxResult result = AjaxResult.success(resultList);
			outputResult(response, result);
		}
		
	}

	private void doLoadNodes(HttpServletRequest request, HttpServletResponse response) {
		
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		AreaHelper areaHelper = wac.getBean(AreaHelper.class);
		
		String areaCode = request.getParameter("areaCode");
		
		if(areaCode == null ){
			AjaxResult result = AjaxResult.fail("request parameter [areaCode] not found!");
			outputResult(response, result);
			return;
		}
		
		HttpSession session = request.getSession();
		String userRootAreaCode = (String) session.getAttribute(AreaConstants.KEY_USER_ROOT_AREA_CODE);
		
		if (StringUtils.isBlank(userRootAreaCode))
			userRootAreaCode = AppContext.getInstance().getString("plugin.area.rootAreaCode");
		
		if (StringUtils.isBlank(areaCode))
			areaCode = userRootAreaCode;
		
		if (areaCode.startsWith(userRootAreaCode)) {
			Map<String, Area> childArea = areaHelper.lookupAreaAndNextLevelAreas(areaCode);
			AjaxResult result = AjaxResult.success(childArea.values());
			outputResult(response, result);
		}
	}
	
	private void outputResult(HttpServletResponse response, AjaxResult result) {
		JSONObject jsonResult = JSONObject.fromObject(result);
		response.setContentType("text/plain; charset=utf-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(jsonResult.toString());
		} catch (IOException e) {
			if (logger.isDebugEnabled())
				logger.debug("error output AjaxResult");
		}
	}

}
