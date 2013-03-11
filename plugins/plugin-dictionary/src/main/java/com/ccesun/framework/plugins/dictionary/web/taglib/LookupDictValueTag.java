package com.ccesun.framework.plugins.dictionary.web.taglib;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ccesun.framework.plugins.dictionary.DictionaryHelper;

public class LookupDictValueTag extends TagSupport {

    private Log log = LogFactory.getLog(LookupDictValueTag.class);

    private DictionaryHelper dictionaryHelper;
    
    private static final long serialVersionUID = 6269492411075687996L;

    private String type;

    private String key;
    
    private int position;

    public void setType(String type) {
        this.type = type;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setPosition(int position) {
		this.position = position;
	}

	public int doStartTag() throws JspException {
    	
    	initDictionaryHelper();
    	String value = "";
    	if (!StringUtils.isBlank(type) || !StringUtils.isBlank(key))
	        value = getValue(type, key);
    		value = value == null ? "" : value;
        try {
            this.pageContext.getOut().print(value);
        } catch (IOException e) {
            if (log.isErrorEnabled())
                log.error("IOException occurred.", e);
            e.printStackTrace();
        }
        
        return SKIP_BODY;
    }

	private String getValue(String type, String key) {
		
		String result = "";
		switch (position) {
		case 0:
			result = dictionaryHelper.lookupDictValue0(type, key);
			break;
		case 1:
			result = dictionaryHelper.lookupDictValue1(type, key);
			break;
		case 2:
			result = dictionaryHelper.lookupDictValue2(type, key);
			break;
		case 3:
			result = dictionaryHelper.lookupDictValue3(type, key);
			break;
		default:
			result = dictionaryHelper.lookupDictValue0(type, key);
			break;
		}
		
		return result;
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
