<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="well sidebar-nav">
	<ul class="nav nav-list">
		<li class="nav-header">SIDEBAR</li>
		<#list codeGenConfig.materials as material>
		<li><a href="${"$" + "{pageContext.request.contextPath}"}/${material.domainName?uncap_first}">${material.domainName}</a></li>
		</#list>
	</ul>
</div><!--/.well -->

