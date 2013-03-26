package com.ccesun.framework.plugins.dictionary.web.taglib;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ccesun.framework.plugins.dictionary.DictionaryHelper;

public class LookupDictTag extends TagSupport {

    //private Log log = LogFactory.getLog(LookupDictTag.class);

    private DictionaryHelper dictionaryHelper;
    
    private static final long serialVersionUID = 6269492411075687996L;

    private String type;

    private String key;
    
    private String var;

    public void setType(String type) {
        this.type = type;
    }

    public void setKey(String key) {
        this.key = key;
    }

	public void setVar(String var) {
		this.var = var;
	}

	public int doStartTag() throws JspException {
    	
    	initDictionaryHelper();
    	pageContext.setAttribute(var, dictionaryHelper.lookupDict(type, key));
        
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

}
