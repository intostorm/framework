package com.ccesun.framework.plugins.area.web.taglib;

import javax.servlet.ServletContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ccesun.framework.plugins.area.AreaHelper;

public class AreaNameTag extends TagSupport {

    protected Log log = LogFactory.getLog(AreaNameTag.class);

    protected AreaHelper areaHelper;
    
    private static final long serialVersionUID = 6269492411075687996L;

    protected String areaCode;

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    protected void initAreaHelper() {
		if (areaHelper == null) {
			ServletContext servletContext = this.pageContext.getServletContext();
			WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			areaHelper = applicationContext.getBean(AreaHelper.class);
			
			if (areaHelper == null)
				throw new RuntimeException("areaHelper not found!");
		}
	}

}
