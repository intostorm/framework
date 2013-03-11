package com.ccesun.framework.tools.codegen;

import java.util.List;

public class CodeGen {

	private CodeGenConfiguation config;

	public void setConfig(CodeGenConfiguation config) {
		this.config = config;
	}

	public void start() {
		
		List<IArtifact> artifacts = config.getArtifacts();
		
		for (IArtifact artifact : artifacts) {
			IProcessor processor = getProcessor(artifact.getClass(), config);
			if (processor == null)
				continue;
			
			processor.process(artifact, config);
		}
	}
	
	private static <T extends IArtifact> IProcessor getProcessor(Class<T> cls, CodeGenConfiguation config) {
		
		List<IProcessor> processors = config.getProcessors();
		for (IProcessor processor : processors) {
			if (processor.canHandler(cls))
				return processor;
		}
		return null;
	}
}
