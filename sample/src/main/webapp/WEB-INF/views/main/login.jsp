<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<form class="form-signin" action="${pageContext.request.contextPath }/login" method="post">
	<h2 class="form-signin-heading">登录</h2>
	<c:if test="${not empty errMsg}">
		<div class="alert alert-error">
	    	${errMsg }
	    </div>
    </c:if>
	<input name="username" type="text" placeholder="用户名" class="input-block-level"> 
	<input name="password" type="password" placeholder="密码" class="input-block-level"> 
	<label class="checkbox"> 
		<input type="checkbox" name="rememberme" value="true">
		记住我
	</label>
	<button type="submit" class="btn btn-large btn-primary">登录</button>
</form>
