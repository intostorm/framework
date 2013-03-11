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

import com.ccesun.sample.domain.SysFunc;
import com.ccesun.sample.service.SysFuncService;
import com.ccesun.framework.core.dao.support.Page;
import com.ccesun.framework.core.dao.support.SearchForm;
import com.ccesun.framework.core.web.controller.BaseController;

@RequestMapping("/sysFunc")
@Controller
public class SysFuncController extends BaseController {

	final Logger logger = LoggerFactory.getLogger(SysFuncController.class);	
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private SysFuncService sysFuncService;
	
	@RequestMapping(method = {GET, POST})
	public String list(@ModelAttribute SearchForm searchForm, Model model) {
		
		Page<SysFunc> sysFuncPage = sysFuncService.findPage(searchForm);
		model.addAttribute("sysFuncPage", sysFuncPage);
		
		return "sysFunc/list";
	}
	
	@RequestMapping(value = "/{funcId}/update", method = GET)
    public String update(@PathVariable("funcId") Integer funcId, Model model) {
        model.addAttribute("sysFunc", sysFuncService.findByPk(funcId));
        return "sysFunc/update";
	}	
	
	@RequestMapping(value = "/{funcId}/update", method = POST)
    public String update(@Valid @ModelAttribute SysFunc sysFunc, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("sysFunc", sysFunc);
            return "sysFunc/update";
        }

        sysFuncService.save(sysFunc);
        return "redirect:/sysFunc/" + sysFunc.getFuncId() + "/show";
    }	
	
	@RequestMapping(value = "/create", method = GET)
    public String create(Model model) {
		SysFunc sysFunc = new SysFunc();
        model.addAttribute("sysFunc", sysFunc);
        return "sysFunc/create";
    }
    
	@RequestMapping(value = "/create", method = POST)
    public String create(@Valid SysFunc sysFunc, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("sysFunc", sysFunc);
            return "sysFunc/create";
        }
        
        if (sysFunc.getParent() != null && sysFunc.getParent().getFuncId() == null)
        	sysFunc.setParent(null);
        
        sysFuncService.save(sysFunc);
        return "redirect:/sysFunc/" + sysFunc.getFuncId() + "/show";
    }	
	
	@RequestMapping(value = "/{funcId}/show", method = GET)
    public String show(@PathVariable("funcId") Integer funcId, Model model) {
        SysFunc sysFunc = sysFuncService.findByPk(funcId);
		model.addAttribute("sysFunc", sysFunc);
        return "sysFunc/show";
    }
    
    @RequestMapping(value = "/{funcId}/remove", method = GET)
    public String remove(@PathVariable("funcId") Integer funcId, Model model) {
        sysFuncService.remove(funcId);
        return "redirect:/sysFunc";
    }
}

