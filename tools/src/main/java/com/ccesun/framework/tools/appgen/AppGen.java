package com.ccesun.framework.tools.appgen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;

import com.ccesun.framework.tools.codegen.CodeGen;
import com.ccesun.framework.tools.codegen.CodeGenConfiguation;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class AppGen {
	
	private AppGenConfiguation config;

	public void setConfig(AppGenConfiguation config) {
		this.config = config;
	}
	
	public void start() {
		try {
			writeApp();
			generateCode();
			writeConfig();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

	private void generateCode() {
		CodeGenConfiguation codeGenConfiguation = config.getCodeGenConfig();
		codeGenConfiguation.setBasePackage(config.getBasePackage());
		codeGenConfiguation.setDataSource(config.getDataSource());
		codeGenConfiguation.setBaseOutput(config.getBaseOutput() + "/" + config.getProjectName());
		codeGenConfiguation.setFreemarkerConfig(config.getFreemarkerConfig());
		
		CodeGen codeGen = new CodeGen();
		codeGen.setConfig(codeGenConfiguation);
		codeGen.start();
	}

	private void writeConfig() throws IOException, TemplateException {
		Configuration freemarkerConfig = config.getFreemarkerConfig();
		
		Map<String, Object> root = new HashMap<String, Object>();
		
		BasicDataSource dataSource = (BasicDataSource) config.getDataSource();
		String jdbcDriver = dataSource.getDriverClassName();
		String jdbcUrl = dataSource.getUrl();
		String jdbcUsername = dataSource.getUsername();
		String jdbcPassword = dataSource.getPassword();
		
		root.put("projectName", config.getProjectName());
		root.put("projectVersion", config.getProjectVersion());
		root.put("frameworkVersion", config.getFrameworkVersion());
		root.put("basePackage", config.getBasePackage());
		root.put("jdbcDriver", jdbcDriver);
		root.put("jdbcUrl", jdbcUrl);
		root.put("jdbcUsername", jdbcUsername);
		root.put("jdbcPassword", jdbcPassword);
		root.put("jdbcPassword", jdbcPassword);
		root.put("hibernateDialect", config.getHibernateDialect());
		root.put("codeGenConfig", config.getCodeGenConfig());
		
		{
			String templateName = "properties-jdbc.ftl";
			Template template = freemarkerConfig.getTemplate(templateName);
			String outputFileName = config.getBaseOutput() + "/" + config.getProjectName() + "/src/main/resources/jdbc.properties";
			
			writeAritfact(template, root, outputFileName);
		}
		
		{
			String templateName = "properties-messages.ftl";
			Template template = freemarkerConfig.getTemplate(templateName);
			String outputFileName = config.getBaseOutput() + "/" + config.getProjectName() + "/src/main/webapp/WEB-INF/i18n/messages.properties";
			
			writeAritfact(template, root, outputFileName);
		}
		
		{
			String templateName = "xml-app-context.ftl";
			Template template = freemarkerConfig.getTemplate(templateName);
			String outputFileName = config.getBaseOutput() + "/" + config.getProjectName() + "/src/main/webapp/WEB-INF/spring/app-context.xml";
			
			writeAritfact(template, root, outputFileName);
		}
		
		{
			String templateName = "xml-app-webmvc.ftl";
			Template template = freemarkerConfig.getTemplate(templateName);
			String outputFileName = config.getBaseOutput() + "/" + config.getProjectName() + "/src/main/webapp/WEB-INF/spring/app-webmvc.xml";
			
			writeAritfact(template, root, outputFileName);
		}
		
		{
			String templateName = "xml-pom.ftl";
			Template template = freemarkerConfig.getTemplate(templateName);
			String outputFileName = config.getBaseOutput() + "/" + config.getProjectName() + "/pom.xml";
			
			writeAritfact(template, root, outputFileName);
		}
		
		{
			String templateName = "xml-web.ftl";
			Template template = freemarkerConfig.getTemplate(templateName);
			String outputFileName = config.getBaseOutput() + "/" + config.getProjectName() + "/src/main/webapp/WEB-INF/web.xml";
			
			writeAritfact(template, root, outputFileName);
		}
		
		{
			String templateName = "jsp-menu.ftl";
			Template template = freemarkerConfig.getTemplate(templateName);
			String outputFileName = config.getBaseOutput() + "/" + config.getProjectName() + "/src/main/webapp/WEB-INF/layouts/menu.jsp";
			
			writeAritfact(template, root, outputFileName);
		}
		
		{
			String templateName = "java-main-controller.ftl";
			Template template = freemarkerConfig.getTemplate(templateName);
			String outputFileName = config.getBaseOutput() + "/" + config.getProjectName() + "/src/main/java/" + StringUtils.replace(config.getBasePackage(), ".", "/") + "/web/controller/MainController.java";
			
			writeAritfact(template, root, outputFileName);
		}
	}

	private void writeApp() throws IOException {
		File appgenResources = new File(AppGen.class.getResource("/appgen-res").getPath());
		File dest = new File(config.getBaseOutput() + "/" + config.getProjectName());
		FileUtils.copyDirectory(appgenResources, dest);
	}
	
	private void writeAritfact(Template template, Map<String, Object> root, String outputFileName) throws IOException, TemplateException {
		File outputFile = new File(outputFileName);
		File outputFileDir = outputFile.getParentFile();
		if (!outputFileDir.exists())
			outputFileDir.mkdirs();
		
		Writer out = new FileWriter(outputFile);
		template.process(root, out);
		out.flush();
		out.close();
	}
	
}
