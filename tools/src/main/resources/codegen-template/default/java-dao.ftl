package ${config.basePackage}.${artifact.relatedPackage};

import ${config.basePackage}.domain.${materialDetail.material.domainName};
import com.ccesun.framework.core.dao.support.IDao;

public interface ${materialDetail.material.domainName}Dao extends IDao<${materialDetail.material.domainName}, ${materialDetail.pk.propertyType}> {

}