package com.ccesun.framework.plugins.staticpage.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ccesun.framework.plugins.staticpage.StaticPageUtils;

public class StaticURITag extends TagSupport {

    private Log log = LogFactory.getLog(StaticURITag.class);

    private static final long serialVersionUID = 6269492411075687996L;

    private String requestURI;

	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}

	public int doStartTag() throws JspException {
    	
    	try {
            this.pageContext.getOut().print(StaticPageUtils.convertToHtmlFileName(requestURI));
        } catch (IOException e) {
            if (log.isErrorEnabled())
                log.error("IOException occurred.", e);
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

}
