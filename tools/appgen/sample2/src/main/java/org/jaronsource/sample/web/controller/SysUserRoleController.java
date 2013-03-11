package org.jaronsource.sample.web.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.jaronsource.sample.domain.SysUserRole;
import org.jaronsource.sample.service.SysUserRoleService;
import com.ccesun.framework.core.dao.support.Page;
import com.ccesun.framework.core.dao.support.SearchForm;
import com.ccesun.framework.core.web.controller.BaseController;

@RequestMapping("/sysUserRole")
@Controller
public class SysUserRoleController extends BaseController {

	final Logger logger = LoggerFactory.getLogger(SysUserRoleController.class);	
	
	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	@RequestMapping(method = {GET, POST})
	public String list(@ModelAttribute SearchForm searchForm, Model model) {
		
		Page<SysUserRole> sysUserRolePage = sysUserRoleService.findPage(searchForm);
		model.addAttribute("sysUserRolePage", sysUserRolePage);
		
		return "sysUserRole/list";
	}
	
	@RequestMapping(value = "/{id}/update", method = GET)
    public String update(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("sysUserRole", sysUserRoleService.findByPk(id));
        return "sysUserRole/update";
	}	
	
	@RequestMapping(value = "/{id}/update", method = POST)
    public String update(@Valid @ModelAttribute SysUserRole sysUserRole, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("sysUserRole", sysUserRole);
            return "sysUserRole/update";
        }

        sysUserRoleService.save(sysUserRole);
        return "redirect:/sysUserRole/" + sysUserRole.getId() + "/show";
    }	
	
	@RequestMapping(value = "/create", method = GET)
    public String create(Model model) {
		SysUserRole sysUserRole = new SysUserRole();
        model.addAttribute("sysUserRole", sysUserRole);
        return "sysUserRole/create";
    }
    
	@RequestMapping(value = "/create", method = POST)
    public String create(@Valid SysUserRole sysUserRole, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("sysUserRole", sysUserRole);
            return "sysUserRole/create";
        }

        sysUserRoleService.save(sysUserRole);
        return "redirect:/sysUserRole/" + sysUserRole.getId() + "/show";
    }	
	
	@RequestMapping(value = "/{id}/show", method = GET)
    public String show(@PathVariable("id") Integer id, Model model) {
        SysUserRole sysUserRole = sysUserRoleService.findByPk(id);
		model.addAttribute("sysUserRole", sysUserRole);
        return "sysUserRole/show";
    }
    
    @RequestMapping(value = "/{id}/remove", method = GET)
    public String remove(@PathVariable("id") Integer id, Model model) {
        sysUserRoleService.remove(id);
        return "redirect:/sysUserRole";
    }
}

