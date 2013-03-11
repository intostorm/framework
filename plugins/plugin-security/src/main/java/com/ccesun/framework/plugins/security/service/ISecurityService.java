package com.ccesun.framework.plugins.security.service;

import javax.servlet.http.HttpServletResponse;

import com.ccesun.framework.plugins.security.UserNotAvailableException;
import com.ccesun.framework.plugins.security.UserNotFoundException;
import com.ccesun.framework.plugins.security.WrongPasswordException;
import com.ccesun.framework.plugins.security.domain.SecurityToken;

public interface ISecurityService {

	SecurityToken login
		(HttpServletResponse response, String[] usernames, String passwd, boolean rememberme)
		throws UserNotFoundException, UserNotAvailableException, WrongPasswordException;
	
	void logout(HttpServletResponse response);
}
