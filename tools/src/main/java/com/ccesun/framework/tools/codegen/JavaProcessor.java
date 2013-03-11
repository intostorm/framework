package com.ccesun.framework.tools.codegen;

import com.ccesun.framework.util.StringUtils;


public class JavaProcessor extends AbstractProcessor implements IProcessor{

	@Override
	public <T extends IArtifact> boolean canHandler(Class<T> cls) {
		return cls.isAssignableFrom(SimpleJavaArtifact.class);
	}

	@Override
	protected <T extends IArtifact> String parseOutputFileName(T artifact, CodeGenConfiguation config, MaterialDetail materialDetail) {
		
		SimpleJavaArtifact simpleJavaArtifact = (SimpleJavaArtifact) artifact;
		String baseOutput = config.getBaseOutput();
		String basePackage = config.getBasePackage();
		String relatedPackage = simpleJavaArtifact.getRelatedPackage();
		String domainName = materialDetail.getMaterial().getDomainName();
		
		String outputDirToParse = artifact.getOutputDir();
		String outputFileNameToParse = artifact.getOutputFileName();
		
		String destOutputDir = StringUtils.replace(outputDirToParse, "{baseOutput}", baseOutput);
		destOutputDir = StringUtils.replace(destOutputDir, "{basePackage}", basePackage);
		destOutputDir = StringUtils.replace(destOutputDir, "{relatedPackage}", relatedPackage);
		destOutputDir = StringUtils.replace(destOutputDir, ".", "/");
		
		String destOutputFileName = StringUtils.replace(outputFileNameToParse, "{domainName}", domainName);
		
		return destOutputDir.concat("/").concat(destOutputFileName);
	}

}
