package com.ccesun.framework.tools.runner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ccesun.framework.tools.codegen.CodeGen;


public class CodeGenRunner {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:codegen-context.xml");
		CodeGen codeGen = (CodeGen) applicationContext.getBean("codeGen", CodeGen.class);
		codeGen.start();
	}

	
}
