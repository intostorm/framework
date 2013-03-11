package com.ccesun.framework.core.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.ccesun.framework.core.AppContext;

public class AppContextTag extends SimpleTagSupport {

    private String var;

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    @Override
    public void doTag() throws JspException, IOException {
        getJspContext().setAttribute(var, AppContext.getInstance().getContextMap());
    }

}