package ${config.basePackage}.${artifact.relatedPackage};

import ${config.basePackage}.domain.${materialDetail.material.domainName};
import ${config.basePackage}.dao.${materialDetail.material.domainName}Dao;
import dgtsrv.core.dao.hibernate.BaseDAOImpl;

public class ${materialDetail.material.domainName}DaoImpl extends BaseDAOImpl<${materialDetail.material.domainName}> implements ${materialDetail.material.domainName}Dao {

}