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

import org.jaronsource.sample.domain.SysRoleFunc;
import org.jaronsource.sample.service.SysRoleFuncService;
import com.ccesun.framework.core.dao.support.Page;
import com.ccesun.framework.core.dao.support.SearchForm;
import com.ccesun.framework.core.web.controller.BaseController;

@RequestMapping("/sysRoleFunc")
@Controller
public class SysRoleFuncController extends BaseController {

	final Logger logger = LoggerFactory.getLogger(SysRoleFuncController.class);	
	
	@Autowired
	private SysRoleFuncService sysRoleFuncService;
	
	@RequestMapping(method = {GET, POST})
	public String list(@ModelAttribute SearchForm searchForm, Model model) {
		
		Page<SysRoleFunc> sysRoleFuncPage = sysRoleFuncService.findPage(searchForm);
		model.addAttribute("sysRoleFuncPage", sysRoleFuncPage);
		
		return "sysRoleFunc/list";
	}
	
	@RequestMapping(value = "/{id}/update", method = GET)
    public String update(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("sysRoleFunc", sysRoleFuncService.findByPk(id));
        return "sysRoleFunc/update";
	}	
	
	@RequestMapping(value = "/{id}/update", method = POST)
    public String update(@Valid @ModelAttribute SysRoleFunc sysRoleFunc, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("sysRoleFunc", sysRoleFunc);
            return "sysRoleFunc/update";
        }

        sysRoleFuncService.save(sysRoleFunc);
        return "redirect:/sysRoleFunc/" + sysRoleFunc.getId() + "/show";
    }	
	
	@RequestMapping(value = "/create", method = GET)
    public String create(Model model) {
		SysRoleFunc sysRoleFunc = new SysRoleFunc();
        model.addAttribute("sysRoleFunc", sysRoleFunc);
        return "sysRoleFunc/create";
    }
    
	@RequestMapping(value = "/create", method = POST)
    public String create(@Valid SysRoleFunc sysRoleFunc, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("sysRoleFunc", sysRoleFunc);
            return "sysRoleFunc/create";
        }

        sysRoleFuncService.save(sysRoleFunc);
        return "redirect:/sysRoleFunc/" + sysRoleFunc.getId() + "/show";
    }	
	
	@RequestMapping(value = "/{id}/show", method = GET)
    public String show(@PathVariable("id") Integer id, Model model) {
        SysRoleFunc sysRoleFunc = sysRoleFuncService.findByPk(id);
		model.addAttribute("sysRoleFunc", sysRoleFunc);
        return "sysRoleFunc/show";
    }
    
    @RequestMapping(value = "/{id}/remove", method = GET)
    public String remove(@PathVariable("id") Integer id, Model model) {
        sysRoleFuncService.remove(id);
        return "redirect:/sysRoleFunc";
    }
}

