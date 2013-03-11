package ${config.basePackage}.${artifact.relatedPackage};

import ${config.basePackage}.dao.${materialDetail.material.domainName}Dao;
import ${config.basePackage}.domain.${materialDetail.material.domainName};
import ${config.basePackage}.service.${materialDetail.material.domainName}Service;
import com.ccesun.framework.core.service.SearchFormSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import com.ccesun.framework.core.dao.support.IDao;
import org.springframework.stereotype.Service;

@Service
public class ${materialDetail.material.domainName}ServiceImpl extends SearchFormSupportService<${materialDetail.material.domainName}, ${materialDetail.pk.propertyType}> implements ${materialDetail.material.domainName}Service {

	@Autowired
	private ${materialDetail.material.domainName}Dao ${materialDetail.material.domainName?uncap_first}Dao;
	
	@Override
	public IDao<${materialDetail.material.domainName}, ${materialDetail.pk.propertyType}> getDao() {
		return ${materialDetail.material.domainName?uncap_first}Dao;
	}

}