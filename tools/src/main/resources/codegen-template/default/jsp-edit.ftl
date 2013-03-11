<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="row">
    
    <spring:url value="/${materialDetail.material.domainName?uncap_first}" var="baseUrl"/>    
    
    <c:set var="labelNew" value="新建" />
    <c:set var="labelUpdate" value="更新" />
    <spring:eval expression="${materialDetail.material.domainName?uncap_first}.${materialDetail.pk.propertyName} == null ? labelNew:labelUpdate" var="formTitle"/>

    <div class="span10">
    <form:form modelAttribute="${materialDetail.material.domainName?uncap_first}" id="${materialDetail.material.domainName?uncap_first}Form" method="post">
		<fieldset>
			<legend>${"$" + "{formTitle}"}</legend>
	        <c:if test="${"$" + "{not empty message}"}">
	            <div id="message" class="${"$" + "{message.type}"}">${"$" + "{message.message}"}</div>
	        </c:if>
			<#list materialDetail.pcMappings as pcMapping>
			<#if pcMapping.isPk() == false>
	        <form:label path="${pcMapping.propertyName}">${pcMapping.columnRemarks!'未指定'}</form:label>
	        <form:input path="${pcMapping.propertyName}" />
	        <form:errors path="${pcMapping.propertyName}" cssClass="error" element="div" />
	        <p/>
	        <#else>
	        <form:hidden path="${pcMapping.propertyName}" />
	        </#if>
	        
	    	</#list>
	        
	        <button type="submit" class="btn btn-primary">提交</button>
	        <button type="reset" class="btn">重置</button>
	        <a href="${"$" + "{baseUrl}"}" class="btn">返回列表</a>
        </fieldset>              
    </form:form>   
    </div>

</div>
