package org.jaronsource.sample.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class MainController {

	final Logger logger = LoggerFactory.getLogger(MainController.class);	
	
	@RequestMapping(method = {GET, POST})
	public String main() {
		return "main/index";
	}
	
}

