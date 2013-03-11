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

import org.jaronsource.sample.domain.Contact;
import org.jaronsource.sample.service.ContactService;
import com.ccesun.framework.core.dao.support.Page;
import com.ccesun.framework.core.dao.support.SearchForm;
import com.ccesun.framework.core.web.controller.BaseController;

@RequestMapping("/contact")
@Controller
public class ContactController extends BaseController {

	final Logger logger = LoggerFactory.getLogger(ContactController.class);	
	
	@Autowired
	private ContactService contactService;
	
	@RequestMapping(method = {GET, POST})
	public String list(@ModelAttribute SearchForm searchForm, Model model) {
		
		Page<Contact> contactPage = contactService.findPage(searchForm);
		model.addAttribute("contactPage", contactPage);
		
		return "contact/list";
	}
	
	@RequestMapping(value = "/{recordId}/update", method = GET)
    public String update(@PathVariable("recordId") Integer recordId, Model model) {
        model.addAttribute("contact", contactService.findByPk(recordId));
        return "contact/update";
	}	
	
	@RequestMapping(value = "/{recordId}/update", method = POST)
    public String update(@Valid @ModelAttribute Contact contact, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("contact", contact);
            return "contact/update";
        }

        contactService.save(contact);
        return "redirect:/contact/" + contact.getRecordId() + "/show";
    }	
	
	@RequestMapping(value = "/create", method = GET)
    public String create(Model model) {
		Contact contact = new Contact();
        model.addAttribute("contact", contact);
        return "contact/create";
    }
    
	@RequestMapping(value = "/create", method = POST)
    public String create(@Valid Contact contact, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("contact", contact);
            return "contact/create";
        }

        contactService.save(contact);
        return "redirect:/contact/" + contact.getRecordId() + "/show";
    }	
	
	@RequestMapping(value = "/{recordId}/show", method = GET)
    public String show(@PathVariable("recordId") Integer recordId, Model model) {
        Contact contact = contactService.findByPk(recordId);
		model.addAttribute("contact", contact);
        return "contact/show";
    }
    
    @RequestMapping(value = "/{recordId}/remove", method = GET)
    public String remove(@PathVariable("recordId") Integer recordId, Model model) {
        contactService.remove(recordId);
        return "redirect:/contact";
    }
}

