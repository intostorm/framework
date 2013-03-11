package ${config.basePackage}.${artifact.relatedPackage};

import ${config.basePackage}.dao.${materialDetail.material.domainName}Dao;
import ${config.basePackage}.domain.${materialDetail.material.domainName};
import ${config.basePackage}.service.${materialDetail.material.domainName}Service;
import dgtsrv.core.service.impl.BaseServiceImpl;

public class ${materialDetail.material.domainName}ServiceImpl extends BaseServiceImpl<${materialDetail.material.domainName}> implements ${materialDetail.material.domainName}Service {

	private ${materialDetail.material.domainName}Dao ${materialDetail.material.domainName?uncap_first}Dao;
	
	@Override
	public void set${materialDetail.material.domainName}Dao(${materialDetail.material.domainName}Dao ${materialDetail.material.domainName?uncap_first}Dao) {
		this.${materialDetail.material.domainName?uncap_first}Dao = ${materialDetail.material.domainName?uncap_first}Dao;
		super.setBaseDAO(${materialDetail.material.domainName?uncap_first}Dao);
	}
	
	@Override
	public ${materialDetail.material.domainName}Dao get${materialDetail.material.domainName}Dao() {
		return ${materialDetail.material.domainName?uncap_first}Dao;
	}

	public String getSearchSQL(String areaCode, Search${materialDetail.material.domainName}DTO searchDto){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ")
		<#list materialDetail.pcMappings as pcMapping>
		<#if pcMapping_has_next>.append("a.${pcMapping.columnName},")<#else>.append("a.${pcMapping.columnName}");</#if>
		</#list>
		sql.append(" FROM ${materialDetail.material.tableName} a WHERE 1=1 ");
		<#list materialDetail.pcMappings as pcMapping>
		<#if pcMapping.propertyType == 'String'>
		//${pcMapping.columnRemarks}
		if(StringUtils.isNotBlank(searchDto.get${pcMapping.propertyName?cap_first}())){
			sql.append(" AND a.${pcMapping.columnName}='").append(searchDto.get${pcMapping.propertyName?cap_first}()).append("'");
		}
		<#elseif pcMapping.propertyType != 'java.util.Date' && !pcMapping.isPk()>
		//${pcMapping.columnRemarks}
		if(searchDto.get${pcMapping.propertyName?cap_first}() != null){
			sql.append(" AND a.${pcMapping.columnName}=").append(searchDto.get${pcMapping.propertyName?cap_first}());
		}
		</#if>
		</#list>;
		return sql.toString();
	}
}