package ${config.basePackage}.${artifact.relatedPackage};

import ${config.basePackage}.dao.${materialDetail.material.domainName}Dao;
import ${config.basePackage}.domain.${materialDetail.material.domainName};
import ${config.basePackage}.service.${materialDetail.material.domainName}Service;
import dgtsrv.core.service.BaseService;

public interface ${materialDetail.material.domainName}Service extends BaseService<${materialDetail.material.domainName}> {

	public void set${materialDetail.material.domainName}Dao(${materialDetail.material.domainName}Dao ${materialDetail.material.domainName?uncap_first}Dao);
	
	public ${materialDetail.material.domainName}Dao get${materialDetail.material.domainName}Dao();
}