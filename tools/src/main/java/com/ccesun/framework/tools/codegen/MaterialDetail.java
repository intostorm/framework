package com.ccesun.framework.tools.codegen;

import java.util.ArrayList;
import java.util.List;

public class MaterialDetail {

	private Material material;
	
	private List<PropertyColumnMapping> pcMappings = new ArrayList<PropertyColumnMapping>();
	
	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public List<PropertyColumnMapping> getPcMappings() {
		return pcMappings;
	}

	public void setPcMappings(List<PropertyColumnMapping> pcMappings) {
		this.pcMappings = pcMappings;
	}

	public void addPcMapping(PropertyColumnMapping pcMapping) {
		getPcMappings().add(pcMapping);
	}
	
	public PropertyColumnMapping getPk() {
		for (PropertyColumnMapping pcMapping : pcMappings) {
			if (pcMapping.isPk())
				return pcMapping;
		}
		return null;
	}
	
	public boolean hasPk() {
		return getPk() != null;
	}
	
	public boolean hasFk() {
		for (PropertyColumnMapping pcMapping : pcMappings) {
			if (pcMapping.isFk())
				return true;
		}
		return false;
	}
}
