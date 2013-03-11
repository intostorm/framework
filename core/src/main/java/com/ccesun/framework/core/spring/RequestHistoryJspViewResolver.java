package com.ccesun.framework.core.spring;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.RedirectView;

import com.ccesun.framework.core.requesthistory.RequestHistoryEntry;

public class RequestHistoryJspViewResolver extends InternalResourceViewResolver {
	
	public static final String HISOTRY_URL_PREFIX = "history:";
	public static final String REDIRECT_URL_PREFIX = "redirect:";
	
	@Override
	protected View createView(String viewName, Locale locale) throws Exception {
		
		View resultView = null;
		
		if (viewName.startsWith(HISOTRY_URL_PREFIX)) {
			String redirectUrl = viewName.substring(HISOTRY_URL_PREFIX.length());
			
			RequestHistoryEntry historyEntry = com.ccesun.framework.core.requesthistory.RequestHistory.getInstance().getEntry(redirectUrl);
			if (historyEntry != null) {
				StringBuilder redirectUrlBuilding = new StringBuilder();
				redirectUrlBuilding.append(redirectUrl);
				appendParameters(redirectUrlBuilding, historyEntry.getParameterMap());
				
				resultView = new RedirectView(redirectUrlBuilding.toString(), isRedirectContextRelative(), isRedirectHttp10Compatible());
				resultView = (View) getApplicationContext().getAutowireCapableBeanFactory().initializeBean(resultView, viewName);
			}
			
			else {
				viewName = REDIRECT_URL_PREFIX.concat(viewName.substring(HISOTRY_URL_PREFIX.length()));
				resultView = super.createView(viewName, locale);
			}
			//return applyLifecycleMethods(viewName, resultView);
		} else {
				resultView = super.createView(viewName, locale);
		}
			
		return resultView;
	}
	
	private void appendParameters(StringBuilder redirectUrlBuilding, Map<String, Object> parameterMap) {
		
		String sp = (redirectUrlBuilding.indexOf("?") == -1) ? "?" : "&";
		
		for (Iterator<Map.Entry<String, Object>> iterator = parameterMap.entrySet().iterator(); iterator.hasNext();) {
			
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			String key = entry.getKey();
			String[] value = (String[]) entry.getValue();
			redirectUrlBuilding.append(sp + key + "=" + value[0]);
			sp = "&";
		}
	}

}
