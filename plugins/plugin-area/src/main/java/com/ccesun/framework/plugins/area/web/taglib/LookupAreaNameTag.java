package com.ccesun.framework.plugins.area.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;

public class LookupAreaNameTag extends AreaNameTag {

	private static final long serialVersionUID = -6889214742917288270L;

	public int doStartTag() throws JspException {
    	
    	initAreaHelper();
    	String value = StringUtils.EMPTY;
    	if (!StringUtils.isBlank(areaCode))
	        value = areaHelper.lookupAreaName(areaCode);
    	
        try {
            this.pageContext.getOut().print(value);
        } catch (IOException e) {
            if (log.isErrorEnabled())
                log.error("IOException occurred.", e);
            e.printStackTrace();
        }
        
        return SKIP_BODY;
    }


}
