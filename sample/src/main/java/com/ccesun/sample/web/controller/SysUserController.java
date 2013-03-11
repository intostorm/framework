package com.ccesun.sample.web.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.ccesun.sample.domain.SysUser;
import com.ccesun.sample.service.SysUserService;
import com.ccesun.framework.core.dao.support.Page;
import com.ccesun.framework.core.dao.support.SearchForm;
import com.ccesun.framework.core.web.controller.BaseController;

@RequestMapping("/sysUser")
@Controller
public class SysUserController extends BaseController {

	final Logger logger = LoggerFactory.getLogger(SysUserController.class);	
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping(method = {GET, POST})
	public String list(@ModelAttribute SearchForm searchForm, Model model) {
		
		Page<SysUser> sysUserPage = sysUserService.findPage(searchForm);
		model.addAttribute("sysUserPage", sysUserPage);
		
		return "sysUser/list";
	}
	
	@RequestMapping(value = "/{userId}/update", method = GET)
    public String update(@PathVariable("userId") Integer userId, Model model) {
        model.addAttribute("sysUser", sysUserService.findByPk(userId));
        return "sysUser/update";
	}	
	
	@RequestMapping(value = "/{userId}/update", method = POST)
    public String update(@Valid @ModelAttribute SysUser sysUser, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("sysUser", sysUser);
            return "sysUser/update";
        }

        sysUserService.save(sysUser);
        return "redirect:/sysUser/" + sysUser.getUserId() + "/show";
    }	
	
	@RequestMapping(value = "/create", method = GET)
    public String create(Model model) {
		SysUser sysUser = new SysUser();
        model.addAttribute("sysUser", sysUser);
        return "sysUser/create";
    }
    
	@RequestMapping(value = "/create", method = POST)
    public String create(@Valid SysUser sysUser, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("sysUser", sysUser);
            return "sysUser/create";
        }

        sysUserService.save(sysUser);
        return "redirect:/sysUser/" + sysUser.getUserId() + "/show";
    }	
	
	@RequestMapping(value = "/{userId}/show", method = GET)
    public String show(@PathVariable("userId") Integer userId, Model model) {
        SysUser sysUser = sysUserService.findByPk(userId);
		model.addAttribute("sysUser", sysUser);
        return "sysUser/show";
    }
    
    @RequestMapping(value = "/{userId}/remove", method = GET)
    public String remove(@PathVariable("userId") Integer userId, Model model) {
        sysUserService.remove(userId);
        return "redirect:/sysUser";
    }
}

