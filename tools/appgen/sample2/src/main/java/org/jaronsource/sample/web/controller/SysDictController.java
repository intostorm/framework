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

import org.jaronsource.sample.domain.SysDict;
import org.jaronsource.sample.service.SysDictService;
import com.ccesun.framework.core.dao.support.Page;
import com.ccesun.framework.core.dao.support.SearchForm;
import com.ccesun.framework.core.web.controller.BaseController;

@RequestMapping("/sysDict")
@Controller
public class SysDictController extends BaseController {

	final Logger logger = LoggerFactory.getLogger(SysDictController.class);	
	
	@Autowired
	private SysDictService sysDictService;
	
	@RequestMapping(method = {GET, POST})
	public String list(@ModelAttribute SearchForm searchForm, Model model) {
		
		Page<SysDict> sysDictPage = sysDictService.findPage(searchForm);
		model.addAttribute("sysDictPage", sysDictPage);
		
		return "sysDict/list";
	}
	
	@RequestMapping(value = "/{recordId}/update", method = GET)
    public String update(@PathVariable("recordId") Integer recordId, Model model) {
        model.addAttribute("sysDict", sysDictService.findByPk(recordId));
        return "sysDict/update";
	}	
	
	@RequestMapping(value = "/{recordId}/update", method = POST)
    public String update(@Valid @ModelAttribute SysDict sysDict, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("sysDict", sysDict);
            return "sysDict/update";
        }

        sysDictService.save(sysDict);
        return "redirect:/sysDict/" + sysDict.getRecordId() + "/show";
    }	
	
	@RequestMapping(value = "/create", method = GET)
    public String create(Model model) {
		SysDict sysDict = new SysDict();
        model.addAttribute("sysDict", sysDict);
        return "sysDict/create";
    }
    
	@RequestMapping(value = "/create", method = POST)
    public String create(@Valid SysDict sysDict, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("sysDict", sysDict);
            return "sysDict/create";
        }

        sysDictService.save(sysDict);
        return "redirect:/sysDict/" + sysDict.getRecordId() + "/show";
    }	
	
	@RequestMapping(value = "/{recordId}/show", method = GET)
    public String show(@PathVariable("recordId") Integer recordId, Model model) {
        SysDict sysDict = sysDictService.findByPk(recordId);
		model.addAttribute("sysDict", sysDict);
        return "sysDict/show";
    }
    
    @RequestMapping(value = "/{recordId}/remove", method = GET)
    public String remove(@PathVariable("recordId") Integer recordId, Model model) {
        sysDictService.remove(recordId);
        return "redirect:/sysDict";
    }
}

