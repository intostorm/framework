package ${config.basePackage}.${artifact.relatedPackage};

import ${config.basePackage}.domain.${materialDetail.material.domainName};
import com.ccesun.framework.core.service.ISearchFormSupportService;

public interface ${materialDetail.material.domainName}Service extends ISearchFormSupportService<${materialDetail.material.domainName}, ${materialDetail.pk.propertyType}> {

}