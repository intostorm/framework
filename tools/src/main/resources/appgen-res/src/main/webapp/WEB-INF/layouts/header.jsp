<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/" var="baseUrl"/>
<div class="container-fluid">
	<spring:message code="application_name" var="headerText" />
	<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> 
		<span class="icon-bar"></span> 
		<span class="icon-bar"></span> 
		<span class="icon-bar"></span>
	</a> 
	<a class="brand" href="#">${headerText}</a>
	<div class="nav-collapse collapse">
		<p class="navbar-text pull-right">
			Logged in as <a href="#" class="navbar-link">Username</a>
		</p>
		<ul class="nav">
			<li class="active"><a href="${baseUrl }">首页</a></li>
			<li><a href="#about">关于</a></li>
			<li><a href="#contact">联系我们</a></li>
		</ul>
	</div>
	<!--/.nav-collapse -->
</div>

