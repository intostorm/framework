<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="row">

    <spring:url value="/${materialDetail.material.domainName?uncap_first}" var="baseUrl"/>   

    <h3>信息</h3>

    <div id="${materialDetail.material.domainName?uncap_first}Info" class="span10">

        <table class="table table-bordered table-hover">
        	<#list materialDetail.pcMappings as pcMapping>
	    	<tr>
                <td>${pcMapping.columnRemarks!'未指定'}</td>
                <td>${"$" + "{" + materialDetail.material.domainName?uncap_first + "." + pcMapping.propertyName + "}"}</td>
            </tr>
	    	</#list>
        </table>         
       
        <a href="${"$" + "{baseUrl}"}/${"$" + "{" + materialDetail.material.domainName?uncap_first + "." + materialDetail.pk.propertyName + "}"}/update" class="btn">修改</a>
        <a href="${"$" + "{baseUrl}"}/${"$" + "{" + materialDetail.material.domainName?uncap_first + "." + materialDetail.pk.propertyName + "}"}/remove" class="btn">删除</a>
        <a href="${"$" + "{baseUrl}"}" class="btn">返回列表</a>     
        <a href="${"$" + "{baseUrl}"}/create" class="btn">新建</a>     
                      
    </div>

</div>
