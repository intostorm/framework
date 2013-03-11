package com.ccesun.framework.tools.codegen;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import freemarker.template.Configuration;

public class CodeGenConfiguation {
	
	private boolean allArticfacts;
	private String baseOutput;
	private String basePackage;
	private DataSource dataSource;
	private Configuration freemarkerConfig;
	private List<IArtifact> artifacts = new ArrayList<IArtifact>();
	private List<Material> materials = new ArrayList<Material>();
	private List<IProcessor> processors = new ArrayList<IProcessor>();
	
	public boolean isAllArticfacts() {
		return allArticfacts;
	}
	public void setAllArticfacts(boolean allArticfacts) {
		this.allArticfacts = allArticfacts;
	}
	public String getBaseOutput() {
		return baseOutput;
	}
	public void setBaseOutput(String baseOutput) {
		this.baseOutput = baseOutput;
	}
	public List<IArtifact> getArtifacts() {
		return artifacts;
	}
	public void setArtifacts(List<IArtifact> artifacts) {
		this.artifacts = artifacts;
	}
	public List<Material> getMaterials() {
		return materials;
	}
	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}
	public List<IProcessor> getProcessors() {
		return processors;
	}
	public void setProcessors(List<IProcessor> processors) {
		this.processors = processors;
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
	public Configuration getFreemarkerConfig() {
		return freemarkerConfig;
	}
	public void setFreemarkerConfig(Configuration freemarkerConfig) {
		this.freemarkerConfig = freemarkerConfig;
	}
	
}
