package com.ccesun.framework.tools.codegen;

import com.ccesun.framework.util.StringUtils;

public class GenericProcessor extends AbstractProcessor implements IProcessor{

	@Override
	public <T extends IArtifact> boolean canHandler(Class<T> cls) {
		return cls.isAssignableFrom(SimpleGenericArtifact.class);
	}

	@Override
	protected <T extends IArtifact> String parseOutputFileName(T artifact, CodeGenConfiguation config, MaterialDetail materialDetail) {
		
		String baseOutput = config.getBaseOutput();
		String domainName = materialDetail.getMaterial().getDomainName();
		String outputDirToParse = artifact.getOutputDir();
		String outputFileNameToParse = artifact.getOutputFileName();
		
		String destOutputDir = StringUtils.replace(outputDirToParse, "{baseOutput}", baseOutput);
		destOutputDir = StringUtils.replace(destOutputDir, "{domainname}", domainName.toLowerCase());
		
		String destOutputFileName = StringUtils.replace(outputFileNameToParse, "{domainname}", domainName.toLowerCase());
		
		return destOutputDir.concat("/").concat(destOutputFileName);
	}


}
