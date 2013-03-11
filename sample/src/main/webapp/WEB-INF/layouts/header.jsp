<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.ccesun.com/tags/security" %>
<spring:url value="/" var="baseUrl"/>
<security:securityUser var="user"/>
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
			<c:choose>
				<c:when test="${user == null }">
					<a href="${pageContext.request.contextPath }/login" class="navbar-link">Login</a>
				</c:when>
				<c:otherwise>
					Logged in as <a href="#" class="navbar-link">${user.userName }</a> <a href="${pageContext.request.contextPath }/logout" class="navbar-link">Logout</a>
				</c:otherwise>
			</c:choose>
			
		</p>
		<ul class="nav">
			<li class="active"><a href="${baseUrl }">首页</a></li>
			<li><a href="#about">关于</a></li>
			<li><a href="#contact">联系我们</a></li> 
		</ul>
	</div>
	<!--/.nav-collapse -->
</div>

