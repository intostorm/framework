package ${config.basePackage}.${artifact.relatedPackage};

@Entity
@Table(name="${materialDetail.material.tableName}")
public class ${materialDetail.material.domainName} implements Serializable {
	
	private static final long serialVersionUID = ${randomNumber}L;
<#list materialDetail.pcMappings as pcMapping>
	
	/** ${pcMapping.columnRemarks!''} */
	private ${pcMapping.propertyType} ${pcMapping.propertyName};
</#list>
<#list materialDetail.pcMappings as pcMapping>
	
	public void set${pcMapping.propertyName?cap_first}(${pcMapping.propertyType} ${pcMapping.propertyName}) {
		this.${pcMapping.propertyName} = ${pcMapping.propertyName};
	}
	
	<#if pcMapping.propertyType == 'boolean'>
	public ${pcMapping.propertyType} is${pcMapping.propertyName?cap_first}() {
		return ${pcMapping.propertyName};
	}
	<#else>
	public ${pcMapping.propertyType} get${pcMapping.propertyName?cap_first}() {
		return ${pcMapping.propertyName};
	}
	</#if>
</#list>
}
