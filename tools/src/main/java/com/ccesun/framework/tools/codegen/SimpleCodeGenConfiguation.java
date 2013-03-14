package com.ccesun.framework.tools.codegen;

public class SimpleCodeGenConfiguation extends CodeGenConfiguation {

	public SimpleCodeGenConfiguation () {
		processors.add(new com.ccesun.framework.tools.codegen.GenericProcessor());
		processors.add(new com.ccesun.framework.tools.codegen.JavaProcessor());
		
		{
			SimpleGenericArtifact simpleGenericArtifact = new com.ccesun.framework.tools.codegen.SimpleGenericArtifact();
			simpleGenericArtifact.setTemplateName("jsp-list.ftl");
			simpleGenericArtifact.setOutputDir("{baseOutput}/views/{domainname}");
			simpleGenericArtifact.setOutputFileName("{domainname}_list.jsp");
			artifacts.add(simpleGenericArtifact);
		}
		
		{
			SimpleGenericArtifact simpleGenericArtifact = new com.ccesun.framework.tools.codegen.SimpleGenericArtifact();
			simpleGenericArtifact.setTemplateName("jsp-show.ftl");
			simpleGenericArtifact.setOutputDir("{baseOutput}/views/{domainname}");
			simpleGenericArtifact.setOutputFileName("{domainname}_show.jsp");
			artifacts.add(simpleGenericArtifact);
		}
		
		{
			SimpleGenericArtifact simpleGenericArtifact = new com.ccesun.framework.tools.codegen.SimpleGenericArtifact();
			simpleGenericArtifact.setTemplateName("jsp-edit.ftl");
			simpleGenericArtifact.setOutputDir("{baseOutput}/views/{domainname}");
			simpleGenericArtifact.setOutputFileName("{domainname}_edit.jsp");
			artifacts.add(simpleGenericArtifact);
		}
		
		{
			SimpleGenericArtifact simpleGenericArtifact = new com.ccesun.framework.tools.codegen.SimpleGenericArtifact();
			simpleGenericArtifact.setTemplateName("xml-views.ftl");
			simpleGenericArtifact.setOutputDir("{baseOutput}/views/{domainname}");
			simpleGenericArtifact.setOutputFileName("views.xml");
			artifacts.add(simpleGenericArtifact);
		}
		
		{
			SimpleJavaArtifact simpleJavaArtifact = new com.ccesun.framework.tools.codegen.SimpleJavaArtifact();
			simpleJavaArtifact.setTemplateName("java-domain.ftl");
			simpleJavaArtifact.setOutputDir("{baseOutput}/java/{basePackage}/{relatedPackage}");
			simpleJavaArtifact.setOutputFileName("{domainName}.java");
			simpleJavaArtifact.setRelatedPackage("domain");
			artifacts.add(simpleJavaArtifact);
		}
		
		{
			SimpleJavaArtifact simpleJavaArtifact = new com.ccesun.framework.tools.codegen.SimpleJavaArtifact();
			simpleJavaArtifact.setTemplateName("java-dao.ftl");
			simpleJavaArtifact.setOutputDir("{baseOutput}/java/{basePackage}/{relatedPackage}");
			simpleJavaArtifact.setOutputFileName("{domainName}Dao.java");
			simpleJavaArtifact.setRelatedPackage("dao");
			artifacts.add(simpleJavaArtifact);
		}
		
		{
			SimpleJavaArtifact simpleJavaArtifact = new com.ccesun.framework.tools.codegen.SimpleJavaArtifact();
			simpleJavaArtifact.setTemplateName("java-dao.impl.ftl");
			simpleJavaArtifact.setOutputDir("{baseOutput}/java/{basePackage}/{relatedPackage}");
			simpleJavaArtifact.setOutputFileName("{domainName}DaoImpl.java");
			simpleJavaArtifact.setRelatedPackage("dao.impl");
			artifacts.add(simpleJavaArtifact);
		}
		
		
		{
			SimpleJavaArtifact simpleJavaArtifact = new com.ccesun.framework.tools.codegen.SimpleJavaArtifact();
			simpleJavaArtifact.setTemplateName("java-service.ftl");
			simpleJavaArtifact.setOutputDir("{baseOutput}/java/{basePackage}/{relatedPackage}");
			simpleJavaArtifact.setOutputFileName("{domainName}Service.java");
			simpleJavaArtifact.setRelatedPackage("service");
			artifacts.add(simpleJavaArtifact);
		}
		
		{
			SimpleJavaArtifact simpleJavaArtifact = new com.ccesun.framework.tools.codegen.SimpleJavaArtifact();
			simpleJavaArtifact.setTemplateName("java-service.impl.ftl");
			simpleJavaArtifact.setOutputDir("{baseOutput}/java/{basePackage}/{relatedPackage}");
			simpleJavaArtifact.setOutputFileName("{domainName}ServiceImpl.java");
			simpleJavaArtifact.setRelatedPackage("service.impl");
			artifacts.add(simpleJavaArtifact);
		}
		
		{
			SimpleJavaArtifact simpleJavaArtifact = new com.ccesun.framework.tools.codegen.SimpleJavaArtifact();
			simpleJavaArtifact.setTemplateName("java-serviceTest.ftl");
			simpleJavaArtifact.setOutputDir("{baseOutput}/test/{basePackage}/{relatedPackage}");
			simpleJavaArtifact.setOutputFileName("{domainName}ServiceTest.java");
			simpleJavaArtifact.setRelatedPackage("service");
			artifacts.add(simpleJavaArtifact);
		}
		
		{
			SimpleJavaArtifact simpleJavaArtifact = new com.ccesun.framework.tools.codegen.SimpleJavaArtifact();
			simpleJavaArtifact.setTemplateName("java-controller.ftl");
			simpleJavaArtifact.setOutputDir("{baseOutput}/java/{basePackage}/{relatedPackage}");
			simpleJavaArtifact.setOutputFileName("{domainName}Controller.java");
			simpleJavaArtifact.setRelatedPackage("web.controller");
			artifacts.add(simpleJavaArtifact);
		}
	}
}
