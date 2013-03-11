package com.ccesun.framework.tools.appgen;

import java.util.ArrayList;

import javax.sql.DataSource;

import com.ccesun.framework.tools.codegen.CodeGenConfiguation;
import com.ccesun.framework.tools.codegen.GenericProcessor;
import com.ccesun.framework.tools.codegen.IArtifact;
import com.ccesun.framework.tools.codegen.IProcessor;
import com.ccesun.framework.tools.codegen.JavaProcessor;
import com.ccesun.framework.tools.codegen.SimpleGenericArtifact;
import com.ccesun.framework.tools.codegen.SimpleJavaArtifact;

import freemarker.template.Configuration;

public class AppGenConfiguation {
	
	private String projectName;
	private String projectVersion;
	private String frameworkVersion;
	private String basePackage;
	private DataSource dataSource;
	private String hibernateDialect;
	private String baseOutput;
	private Configuration freemarkerConfig;
	
	private CodeGenConfiguation codeGenConfig = new CodeGenConfiguation() {
		{
			setAllArticfacts(true);
			setBaseOutput("baseOutput");
			setArtifacts(new ArrayList<IArtifact>() {
				private static final long serialVersionUID = 8911319880298783876L;
				{
					add(new SimpleGenericArtifact("jsp-list.ftl", "{baseOutput}/src/main/webapp/WEB-INF/views/{domainname}", "{domainname}_list.jsp"));
					add(new SimpleGenericArtifact("jsp-show.ftl", "{baseOutput}/src/main/webapp/WEB-INF/views/{domainname}", "{domainname}_show.jsp"));
					add(new SimpleGenericArtifact("jsp-edit.ftl", "{baseOutput}/src/main/webapp/WEB-INF/views/{domainname}", "{domainname}_edit.jsp"));
					add(new SimpleGenericArtifact("xml-views.ftl", "{baseOutput}/src/main/webapp/WEB-INF/views/{domainname}", "views.xml"));
					add(new SimpleJavaArtifact("java-domain.ftl", "{baseOutput}/src/main/java/{basePackage}/{relatedPackage}", "{domainName}.java", "domain"));
					add(new SimpleJavaArtifact("java-dao.ftl", "{baseOutput}/src/main/java/{basePackage}/{relatedPackage}", "{domainName}Dao.java", "dao"));
					add(new SimpleJavaArtifact("java-dao.impl.ftl", "{baseOutput}/src/main/java/{basePackage}/{relatedPackage}", "{domainName}DaoImpl.java", "dao.impl"));
					add(new SimpleJavaArtifact("java-service.ftl", "{baseOutput}/src/main/java/{basePackage}/{relatedPackage}", "{domainName}Service.java", "service"));
					add(new SimpleJavaArtifact("java-service.impl.ftl", "{baseOutput}/src/main/java/{basePackage}/{relatedPackage}", "{domainName}ServiceImpl.java", "service.impl"));
					add(new SimpleJavaArtifact("java-serviceTest.ftl", "{baseOutput}/src/test/java/{basePackage}/{relatedPackage}", "{domainName}ServiceTest.java", "service"));
					add(new SimpleJavaArtifact("java-controller.ftl", "{baseOutput}/src/main/java/{basePackage}/{relatedPackage}", "{domainName}Controller.java", "web.controller"));
				}
			});
			
			setProcessors(new ArrayList<IProcessor>() {

				private static final long serialVersionUID = -2025378786169296820L;
				{
					add(new GenericProcessor());
					add(new JavaProcessor());
				}
			});
		}
	};

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getFrameworkVersion() {
		return frameworkVersion;
	}

	public void setFrameworkVersion(String frameworkVersion) {
		this.frameworkVersion = frameworkVersion;
	}

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getHibernateDialect() {
		return hibernateDialect;
	}

	public void setHibernateDialect(String hibernateDialect) {
		this.hibernateDialect = hibernateDialect;
	}

	public CodeGenConfiguation getCodeGenConfig() {
		return codeGenConfig;
	}

	public void setCodeGenConfig(CodeGenConfiguation codeGenConfig) {
		this.codeGenConfig = codeGenConfig;
	}

	public String getBaseOutput() {
		return baseOutput;
	}

	public void setBaseOutput(String baseOutput) {
		this.baseOutput = baseOutput;
	}

	public Configuration getFreemarkerConfig() {
		return freemarkerConfig;
	}

	public void setFreemarkerConfig(Configuration freemarkerConfig) {
		this.freemarkerConfig = freemarkerConfig;
	}

	public String getProjectVersion() {
		return projectVersion;
	}

	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}
	
}
