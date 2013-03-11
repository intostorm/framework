package com.ccesun.framework.tools.codegen;

public class SimpleGenericArtifact implements IGenericArtifact {

	protected String templateName;
	protected String outputDir;
	protected String outputFileName;
	
	public SimpleGenericArtifact() {}
	
	public SimpleGenericArtifact(String templateName, String outputDir, String outputFileName) {
		super();
		this.templateName = templateName;
		this.outputDir = outputDir;
		this.outputFileName = outputFileName;
	}

	@Override
	public String getTemplateName() {
		return templateName;
	}

	@Override
	public String getOutputDir() {
		return outputDir;
	}

	@Override
	public String getOutputFileName() {
		return outputFileName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

}
