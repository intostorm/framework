package com.ccesun.framework.plugins.security.domain;

import java.io.Serializable;

public interface ISecurityUser extends Serializable {

	String getUserName();
	
	String getRealName();
	
	String getPasswd();
	
	boolean isAvailable();
	
}
