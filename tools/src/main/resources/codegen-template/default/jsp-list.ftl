<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="pg" uri="http://www.ccesun.com/tags/pager" %>
<div class="row">
    <spring:url value="/${materialDetail.material.domainName?uncap_first}" var="baseUrl"/>  
    
    <div class="span10"> 
    	<h3>列表</h3>
	    <form:form modelAttribute="searchForm" action="${"$" + "{REQUEST_URI}"}" class="form-inline" >
	    	<#list materialDetail.pcMappings as pcMapping>
	    	<form:input path="form['${pcMapping.propertyName}_eq<#if pcMapping.propertyType == 'Integer'>_int</#if>']" id="search_${pcMapping.propertyName}" placeholder="${pcMapping.columnRemarks!'未指定'}"/>
	    	</#list>
	    	<button type="submit" class="btn"><i class="icon-search"></i> 搜索</button>
	    </form:form>
    
	    <table class="table table-bordered table-hover"> 
	    	<tr>
	    		<#list materialDetail.pcMappings as pcMapping>
		    	<td>${pcMapping.columnRemarks!'未指定'}</td>
		    	</#list>
		    	<td>操作</td>
	    	</tr>
	    	<c:forEach items="${"$" + "{" + materialDetail.material.domainName?uncap_first + "Page.content}"}" var="entry">
	    	<tr>
		    	<#list materialDetail.pcMappings as pcMapping>
		    	<td>${"$" + "{entry." + pcMapping.propertyName + "}"}</td>
		    	</#list>
		    	<td>
		    		<a href="${"$" + "{baseUrl}"}/${"$" + "{entry." + materialDetail.pk.propertyName + "}"}/show" class="btn btn-small"><i class="icon-eye-open"></i></a> 
		    		<a href="${"$" + "{baseUrl}"}/${"$" + "{entry." + materialDetail.pk.propertyName + "}"}/update" class="btn btn-small"><i class="icon-edit"></i></a> 
		    		<a href="${"$" + "{baseUrl}"}/${"$" + "{entry." + materialDetail.pk.propertyName + "}"}/remove" class="btn btn-small" onclick="return confirm('确定要删除吗？')"><i class="icon-remove-sign"></i></a> 
		    	</td>
	    	</tr>	
	    	</c:forEach>
	    </table> 
    	
    	<pg:pager url="${"$" + "{baseUrl}"}" page="${"$" + "{" + materialDetail.material.domainName?uncap_first + "Page}"}" />
    	<%@ include file="/WEB-INF/pagers/pager-default.jsp" %>
    	<a href="${"$" + "{baseUrl}"}/create" class="btn"><i class="icon-file"></i> 新建</a>
    </div>

</div>