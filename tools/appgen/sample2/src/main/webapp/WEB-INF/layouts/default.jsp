<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=8" />
		
        <!-- jQuery and jQuery UI -->
        <script src="${pageContext.request.contextPath}/scripts/jquery-1.7.1.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/scripts/jquery-ui-1.8.16.custom.min.js" type="text/javascript"></script>
		
		<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/styles/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/styles/bootstrap-responsive.min.css" />
        
        <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/artTemplate/template.min.js"></script> 
        <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/bootstrap.min.js"></script> 
        <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/pager.js"></script>
	    <style>
	    	body {padding-top: 60px; padding-bottom: 40px; } 
			.sidebar-nav {padding: 9px 0;}
		</style>
		<title><spring:message code="application_name" htmlEscape="false"/></title>
	</head>
	
  	<body>
		<div class="navbar navbar-inverse navbar-fixed-top">
			<div class="navbar-inner">
				<tiles:insertAttribute name="header" ignore="true" />
			</div>
	    </div>
	    
	    <div class="container-fluid">
			<div class="row-fluid">
		        <div class="span3">
					<tiles:insertAttribute name="menu" ignore="true" />   
		        </div>
		        <div class="span9">
					<tiles:insertAttribute name="body"/> 
		        </div>
			</div>
      		<hr>
      		<tiles:insertAttribute name="footer" ignore="true"/>
    	</div>
	</body>
</html>
