package ${config.basePackage}.${artifact.relatedPackage};

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Transient;
<#if materialDetail.hasFk()>
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
</#if>
<#if config.dataSource.driverClassName == 'oracle.jdbc.driver.OracleDriver'>
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
</#if>
import com.ccesun.framework.core.annotation.DocDescription;
import com.ccesun.framework.core.dao.support.IEntity;
import com.ccesun.framework.core.dao.support.EntityUtils;

@Entity
@Table(name="${materialDetail.material.tableName}")
public class ${materialDetail.material.domainName} implements IEntity<${materialDetail.pk.propertyType}> {
	
	private static final long serialVersionUID = ${randomNumber}L;
<#list materialDetail.pcMappings as pcMapping>
	
	/***/
	@DocDescription("${pcMapping.columnRemarks!''}")
	<#if pcMapping.isPk()>
	<#if config.dataSource.driverClassName == 'oracle.jdbc.driver.OracleDriver'>
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="${materialDetail.material.domainName?uncap_first}Seq")
	@SequenceGenerator(name="${materialDetail.material.domainName?uncap_first}Seq",sequenceName="S_${materialDetail.material.tableName}",allocationSize=1)
	<#else>
	@Id
	@GeneratedValue
	</#if>
	@Column(name="${pcMapping.columnName}")
	<#elseif pcMapping.isFk()>
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="${pcMapping.columnName}")
	<#else>
	@Column(name="${pcMapping.columnName}")
	</#if>
	protected ${pcMapping.propertyType} ${pcMapping.propertyName};
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
	
	@Override
	@Transient
	public boolean isNew() {
		return EntityUtils.isNew(this.${materialDetail.pk.propertyName});
	}
}
