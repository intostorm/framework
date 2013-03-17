package com.ccesun.framework.plugins.security.service;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.ccesun.framework.plugins.security.PluginSecurityConstants;
import com.ccesun.framework.plugins.security.SecurityTokenHolder;
import com.ccesun.framework.plugins.security.UserNotAvailableException;
import com.ccesun.framework.plugins.security.UserNotFoundException;
import com.ccesun.framework.plugins.security.WrongPasswordException;
import com.ccesun.framework.plugins.security.WrongValidateCodeException;
import com.ccesun.framework.plugins.security.dao.ISecurityDao;
import com.ccesun.framework.plugins.security.domain.IPermission;
import com.ccesun.framework.plugins.security.domain.ISecurityUser;
import com.ccesun.framework.plugins.security.domain.SecurityToken;
import com.ccesun.framework.util.MD5;
import com.ccesun.framework.util.StringUtils;

@Service
@Lazy
public class SecurityService implements ISecurityService {

	@Autowired
	public ISecurityDao iSecurityDao;
	
	@Override
	public SecurityToken login(HttpServletRequest request, 
							   HttpServletResponse response, 
							   String[] usernames, 
							   String passwd, 
							   boolean rememberme,
							   String validateCode) 
						throws UserNotFoundException,
							   UserNotAvailableException, 
							   WrongPasswordException, WrongValidateCodeException{
		
		if (StringUtils.isBlank(validateCode))
			throw new WrongValidateCodeException();
		
		HttpSession session = request.getSession();
		String vCode = (String) session.getAttribute(PluginSecurityConstants.VALIDATE_CODE);
		
		if (!validateCode.equalsIgnoreCase(vCode))
			throw new WrongValidateCodeException();
		
		return login(response, usernames, passwd, rememberme);
	}
	
	@Override
	public SecurityToken login(HttpServletResponse response, 
							   String[] usernames, 
							   String passwd, 
							   boolean rememberme) 
						throws UserNotFoundException,
							   UserNotAvailableException, 
							   WrongPasswordException{
		
		ISecurityUser user = iSecurityDao.getSecurityUser(usernames);
		
		if (user == null)
			throw new UserNotFoundException();
		
		if (!user.isAvailable())
			throw new UserNotAvailableException();
		
		if (!user.getPasswd().equals(passwd) 
				&& !user.getPasswd().equals(MD5.toMD5(passwd))) 
			throw new WrongPasswordException();
		
		Collection<IPermission> permissions = iSecurityDao.loadPermissions(user);
		SecurityToken token = new SecurityToken(user, permissions);
		
		SecurityTokenHolder.setSecurityToken(response, token, rememberme);
		return token;
	}
	
	@Override
	public void logout(HttpServletResponse response) {
		SecurityTokenHolder.cleanSecurityToken(response);
	}
	
}
