package ${config.basePackage}.${artifact.relatedPackage};

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

import ${config.basePackage}.domain.${materialDetail.material.domainName};
import ${config.basePackage}.service.${materialDetail.material.domainName}Service;
import com.ccesun.framework.core.dao.support.Page;
import com.ccesun.framework.core.dao.support.SearchForm;
import com.ccesun.framework.core.web.controller.BaseController;

@RequestMapping("/${materialDetail.material.domainName?uncap_first}")
@Controller
public class ${materialDetail.material.domainName}Controller extends BaseController {

	final Logger logger = LoggerFactory.getLogger(${materialDetail.material.domainName}Controller.class);	
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private ${materialDetail.material.domainName}Service ${materialDetail.material.domainName?uncap_first}Service;
	
	@RequestMapping(method = {GET, POST})
	public String list(@ModelAttribute SearchForm searchForm, Model model) {
		
		Page<${materialDetail.material.domainName}> ${materialDetail.material.domainName?uncap_first}Page = ${materialDetail.material.domainName?uncap_first}Service.findPage(searchForm);
		model.addAttribute("${materialDetail.material.domainName?uncap_first}Page", ${materialDetail.material.domainName?uncap_first}Page);
		
		return "${materialDetail.material.domainName?uncap_first}/list";
	}
	
	@RequestMapping(value = "/{${materialDetail.pk.propertyName}}/update", method = GET)
    public String update(@PathVariable("${materialDetail.pk.propertyName}") ${materialDetail.pk.propertyType} ${materialDetail.pk.propertyName}, Model model) {
        model.addAttribute("${materialDetail.material.domainName?uncap_first}", ${materialDetail.material.domainName?uncap_first}Service.findByPk(${materialDetail.pk.propertyName}));
        return "${materialDetail.material.domainName?uncap_first}/update";
	}	
	
	@RequestMapping(value = "/{${materialDetail.pk.propertyName}}/update", method = POST)
    public String update(@Valid @ModelAttribute ${materialDetail.material.domainName} ${materialDetail.material.domainName?uncap_first}, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("${materialDetail.material.domainName?uncap_first}", ${materialDetail.material.domainName?uncap_first});
            return "${materialDetail.material.domainName?uncap_first}/update";
        }

        ${materialDetail.material.domainName?uncap_first}Service.save(${materialDetail.material.domainName?uncap_first});
        return "redirect:/${materialDetail.material.domainName?uncap_first}/" + ${materialDetail.material.domainName?uncap_first}.get${materialDetail.pk.propertyName?cap_first}() + "/show";
    }	
	
	@RequestMapping(value = "/create", method = GET)
    public String create(Model model) {
		${materialDetail.material.domainName} ${materialDetail.material.domainName?uncap_first} = new ${materialDetail.material.domainName}();
        model.addAttribute("${materialDetail.material.domainName?uncap_first}", ${materialDetail.material.domainName?uncap_first});
        return "${materialDetail.material.domainName?uncap_first}/create";
    }
    
	@RequestMapping(value = "/create", method = POST)
    public String create(@Valid ${materialDetail.material.domainName} ${materialDetail.material.domainName?uncap_first}, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("${materialDetail.material.domainName?uncap_first}", ${materialDetail.material.domainName?uncap_first});
            return "${materialDetail.material.domainName?uncap_first}/create";
        }

        ${materialDetail.material.domainName?uncap_first}Service.save(${materialDetail.material.domainName?uncap_first});
        return "redirect:/${materialDetail.material.domainName?uncap_first}/" + ${materialDetail.material.domainName?uncap_first}.get${materialDetail.pk.propertyName?cap_first}() + "/show";
    }	
	
	@RequestMapping(value = "/{${materialDetail.pk.propertyName}}/show", method = GET)
    public String show(@PathVariable("${materialDetail.pk.propertyName}") ${materialDetail.pk.propertyType} ${materialDetail.pk.propertyName}, Model model) {
        ${materialDetail.material.domainName} ${materialDetail.material.domainName?uncap_first} = ${materialDetail.material.domainName?uncap_first}Service.findByPk(${materialDetail.pk.propertyName});
		model.addAttribute("${materialDetail.material.domainName?uncap_first}", ${materialDetail.material.domainName?uncap_first});
        return "${materialDetail.material.domainName?uncap_first}/show";
    }
    
    @RequestMapping(value = "/{${materialDetail.pk.propertyName}}/remove", method = GET)
    public String remove(@PathVariable("${materialDetail.pk.propertyName}") ${materialDetail.pk.propertyType} ${materialDetail.pk.propertyName}, Model model) {
        ${materialDetail.material.domainName?uncap_first}Service.remove(${materialDetail.pk.propertyName});
        return "redirect:/${materialDetail.material.domainName?uncap_first}";
    }
}

