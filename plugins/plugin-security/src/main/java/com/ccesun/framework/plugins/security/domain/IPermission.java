package com.ccesun.framework.plugins.security.domain;

import java.io.Serializable;

public interface IPermission extends Serializable {

	String getCode();
	
	String getName();
	
	String getGroupCode();
	
	String getParentCode();
	
	String getUrl();
	
	Integer getOrder();
	
}
