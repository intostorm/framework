package com.ccesun.framework.plugins.dictionary.web.taglib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ccesun.framework.plugins.dictionary.DictionaryHelper;
import com.ccesun.framework.plugins.dictionary.domain.Dictionary;
import com.ccesun.framework.util.JsonUtils;

public class LoadDictListTag extends TagSupport implements Comparator<Dictionary> {

	private static final long serialVersionUID = 3155187394335436025L;

	private DictionaryHelper dictionaryHelper;
	
	private String var;
	
	private String type;
	
	private String parentKey;
	
	private boolean toJson;
	
	public void setVar(String var) {
		this.var = var;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}
	
	public void setToJson(Boolean toJson) {
		this.toJson = toJson;
	}
	
	@Override
	public int doStartTag() throws JspException {
		initDictionaryHelper();
		
		Map<String, Dictionary> dictMap = dictionaryHelper.getDictionariesByTypeAndParent(type, parentKey);
		List<Dictionary> items = dictMap == null ? new ArrayList<Dictionary>(0) : new ArrayList<Dictionary>(dictMap.values());
		Collections.sort(items, this);
		if(toJson){
			String json = JsonUtils.toJson(items);
			pageContext.setAttribute(var, json);
		}else{
			pageContext.setAttribute(var, items);
		}
		return SKIP_BODY;
	}
	
	private void initDictionaryHelper() {
		
		if (dictionaryHelper == null) {
			ServletContext servletContext = this.pageContext.getServletContext();
			WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			dictionaryHelper = applicationContext.getBean(DictionaryHelper.class);
		}
		
		if (dictionaryHelper == null)
			throw new RuntimeException("dictionaryHelper not found!");
	}

	@Override
	public int compare(Dictionary o1, Dictionary o2) {
		if (o1 == null)
			return o2 == null ? 0 : -1;
		if (o2 == null)
			return 1;
		return o1.getRecordId().compareTo(o2.getRecordId());
	}
	
}
