package com.ccesun.sample.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping("/fileUpload")
public class FileUploadController {

	final Logger logger = LoggerFactory.getLogger(FileUploadController.class);	
	
	@Autowired
	MessageSource messageSource;
	
	@RequestMapping(method = GET)
	public String fileUpload() {
		return "fileUpload/form";
	}
	
	@RequestMapping(method = POST)
    public String fileUpload(@RequestParam CommonsMultipartFile[] files) throws Exception {
		
		for (CommonsMultipartFile commonsMultipartFile : files) {
			System.out.println(commonsMultipartFile.getFileItem().getName());
			File destFile = new File("D:/tmp/file1");
			commonsMultipartFile.getFileItem().write(destFile);
		}
		
        return "fileUpload/form";
	}	
    
}

