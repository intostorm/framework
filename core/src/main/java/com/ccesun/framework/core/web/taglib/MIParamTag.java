package com.ccesun.framework.core.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class MIParamTag extends TagSupport {

	private static final long serialVersionUID = 8077035430436042219L;
	
	private String type;
	private Object value; 
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	public int doStartTag() throws JspException {
		
		MethodInvokorTag methodInvokorTag = (MethodInvokorTag) findAncestorWithClass (this, MethodInvokorTag.class);
		if (methodInvokorTag == null) 
			throw new JspException("MethodExecutorTag not found");
		MIParam miParam = new MIParam(type, value);
		methodInvokorTag.addParam(miParam);
        return SKIP_BODY;
    }

}