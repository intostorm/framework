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

import org.jaronsource.sample.domain.SysArea;
import org.jaronsource.sample.service.SysAreaService;
import com.ccesun.framework.core.dao.support.Page;
import com.ccesun.framework.core.dao.support.SearchForm;
import com.ccesun.framework.core.web.controller.BaseController;

@RequestMapping("/sysArea")
@Controller
public class SysAreaController extends BaseController {

	final Logger logger = LoggerFactory.getLogger(SysAreaController.class);	
	
	@Autowired
	private SysAreaService sysAreaService;
	
	@RequestMapping(method = {GET, POST})
	public String list(@ModelAttribute SearchForm searchForm, Model model) {
		
		Page<SysArea> sysAreaPage = sysAreaService.findPage(searchForm);
		model.addAttribute("sysAreaPage", sysAreaPage);
		
		return "sysArea/list";
	}
	
	@RequestMapping(value = "/{recordId}/update", method = GET)
    public String update(@PathVariable("recordId") Integer recordId, Model model) {
        model.addAttribute("sysArea", sysAreaService.findByPk(recordId));
        return "sysArea/update";
	}	
	
	@RequestMapping(value = "/{recordId}/update", method = POST)
    public String update(@Valid @ModelAttribute SysArea sysArea, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("sysArea", sysArea);
            return "sysArea/update";
        }

        sysAreaService.save(sysArea);
        return "redirect:/sysArea/" + sysArea.getRecordId() + "/show";
    }	
	
	@RequestMapping(value = "/create", method = GET)
    public String create(Model model) {
		SysArea sysArea = new SysArea();
        model.addAttribute("sysArea", sysArea);
        return "sysArea/create";
    }
    
	@RequestMapping(value = "/create", method = POST)
    public String create(@Valid SysArea sysArea, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("sysArea", sysArea);
            return "sysArea/create";
        }

        sysAreaService.save(sysArea);
        return "redirect:/sysArea/" + sysArea.getRecordId() + "/show";
    }	
	
	@RequestMapping(value = "/{recordId}/show", method = GET)
    public String show(@PathVariable("recordId") Integer recordId, Model model) {
        SysArea sysArea = sysAreaService.findByPk(recordId);
		model.addAttribute("sysArea", sysArea);
        return "sysArea/show";
    }
    
    @RequestMapping(value = "/{recordId}/remove", method = GET)
    public String remove(@PathVariable("recordId") Integer recordId, Model model) {
        sysAreaService.remove(recordId);
        return "redirect:/sysArea";
    }
}

