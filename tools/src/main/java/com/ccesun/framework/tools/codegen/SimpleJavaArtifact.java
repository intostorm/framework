package com.ccesun.framework.tools.codegen;

public class SimpleJavaArtifact extends SimpleGenericArtifact implements IJavaArtifact {

	protected String relatedPackage;
	
	public SimpleJavaArtifact() {}
	
	public SimpleJavaArtifact(String templateName, String outputDir, String outputFileName, String relatedPackage) {
		super(templateName, outputDir, outputFileName);
		this.relatedPackage = relatedPackage;
	}
	
	public String getRelatedPackage() {
		return relatedPackage;
	}

	public void setRelatedPackage(String relatedPackage) {
		this.relatedPackage = relatedPackage;
	}

}
