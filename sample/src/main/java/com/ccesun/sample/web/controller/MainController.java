package com.ccesun.sample.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ccesun.framework.core.web.controller.BaseController;
import com.ccesun.framework.plugins.security.UserNotAvailableException;
import com.ccesun.framework.plugins.security.UserNotFoundException;
import com.ccesun.framework.plugins.security.WrongPasswordException;
import com.ccesun.framework.plugins.security.WrongValidateCodeException;
import com.ccesun.framework.plugins.security.service.ISecurityService;
import com.ccesun.framework.util.BooleanUtils;

@RequestMapping("/")
@Controller
public class MainController extends BaseController {

	final Logger logger = LoggerFactory.getLogger(MainController.class);	
	
	@Autowired
	private ISecurityService securityService;
	
	@RequestMapping(value="/main", method = {GET})
	public String main() {
		return "main/index";
	}
	
	@RequestMapping(value="/login", method = {GET})
	public String login() {
		return "main/login";
	}
	
	@RequestMapping(value="/login", method = {POST})
	public String login(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("username") String userName, 
			@RequestParam("password") String password,
			@RequestParam(required=false, value="rememberme") Boolean rememberme,
			@RequestParam(required=false, value="validateCode") String validateCode,
			Model model) {

		try {
			securityService.login(request, response, new String[] {userName}, password, BooleanUtils.isTrue(rememberme), validateCode);
		} catch (UserNotFoundException e) {
			String errMsg = getMessage("security.errMsg.userNotFound");
			model.addAttribute("errMsg", errMsg);
			return "main/login";
		} catch (UserNotAvailableException e) {
			String errMsg = getMessage("security.errMsg.userNotAvailable");
			model.addAttribute("errMsg", errMsg);
			return "main/login";
		} catch (WrongPasswordException e) {
			String errMsg = getMessage("security.errMsg.wrongPassword");
			model.addAttribute("errMsg", errMsg);
			return "main/login";
		} catch (WrongValidateCodeException e) {
			String errMsg = getMessage("security.errMsg.wrongValidateCode");
			model.addAttribute("errMsg", errMsg);
			return "main/login";
		}
		return "redirect:/main";
	}
	
	@RequestMapping(value="/logout", method = {GET})
	public String logout(HttpServletResponse response) {
		securityService.logout(response);
		return "redirect:/login";
	}
}

