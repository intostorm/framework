package com.ccesun.framework.tools.runner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ccesun.framework.tools.appgen.AppGen;


public class AppGenRunner {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:appgen-context.xml");
		AppGen appGen = (AppGen) applicationContext.getBean("appGen", AppGen.class);
		appGen.start();
	}

	
}
