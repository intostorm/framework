package com.ccesun.sample.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ccesun.framework.core.dao.support.QCriteria;
import com.ccesun.framework.plugins.dictionary.DictionaryHelper;
import com.ccesun.framework.plugins.report.DynamicReportBuilder;
import com.ccesun.sample.domain.Contact;
import com.ccesun.sample.service.ContactService;

@RequestMapping("/report")
@Controller
public class ReportController {

	final Logger logger = LoggerFactory.getLogger(ReportController.class);	
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private DictionaryHelper dictionaryHelper;
	
	private static final String TITLE = "联系人列表";
	
	@RequestMapping(value = "/pdf", method = {GET, POST})
	public void exportPdf(HttpServletResponse response) {
		
		List<Contact> contacts = contactService.find(new QCriteria());
		List<Map<String, String>> contactMap = dictionaryHelper.decodeBeansToMaps(contacts);
		new DynamicReportBuilder()
			.addColumn("姓名", "name", String.class, 10)
			.addColumn("性别", "sex", String.class, 10)
			.addColumn("电话", "phone", String.class, 10)
			.addColumn("地区", "areacode", String.class, 20)
			.addColumn("地址", "address", String.class, 50)
			.outputPdf(response, contactMap, TITLE, "pdf1");
	}

	@RequestMapping(value = "/excel", method = {GET, POST})
	public void exportExcel(HttpServletResponse response) {
		
		List<Contact> contacts = contactService.find(new QCriteria());
		List<Map<String, String>> contactMap = dictionaryHelper.decodeBeansToMaps(contacts);
		new DynamicReportBuilder()
			.addColumn("姓名", "name", String.class, 10)
			.addColumn("性别", "sex", String.class, 10)
			.addColumn("电话", "phone", String.class, 10)
			.addColumn("地区", "areacode", String.class, 20)
			.addColumn("地址", "address", String.class, 50)
			.outputExcel(response, contactMap, TITLE, "excel1");
		
	}
	
	@RequestMapping(value = "/html", method = {GET, POST})
	public void exportHtml(HttpServletResponse response) {
		
		List<Contact> contacts = contactService.find(new QCriteria());
		List<Map<String, String>> contactMap = dictionaryHelper.decodeBeansToMaps(contacts);
		new DynamicReportBuilder()
			.addColumn("姓名", "name", String.class, 10)
			.addColumn("性别", "sex", String.class, 10)
			.addColumn("电话", "phone", String.class, 10)
			.addColumn("地区", "areacode", String.class, 20)
			.addColumn("地址", "address", String.class, 50)
			.outputHtml(response, contactMap, TITLE);
		
	}
    
}

