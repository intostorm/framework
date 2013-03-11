package ${config.basePackage}.${artifact.relatedPackage};

import ${config.basePackage}.domain.${materialDetail.material.domainName};
import ${config.basePackage}.dao.${materialDetail.material.domainName}Dao;
import com.ccesun.framework.core.dao.support.GenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class ${materialDetail.material.domainName}DaoImpl extends GenericDao<${materialDetail.material.domainName}, ${materialDetail.pk.propertyType}> implements ${materialDetail.material.domainName}Dao {

}