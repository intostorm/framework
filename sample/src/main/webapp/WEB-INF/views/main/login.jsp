<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<form class="form-signin" action="${pageContext.request.contextPath }/login" method="post">
	<h2 class="form-signin-heading">登录</h2>
	<c:if test="${not empty errMsg}">
		<div class="alert alert-error">
	    	${errMsg }
	    </div>
    </c:if>
	<input name="username" type="text" placeholder="用户名" class="input-block-level" /> 
	<input name="password" type="password" placeholder="密码" class="input-block-level" />
	<input name="validateCode" type="text" placeholder="验证码" class="input-small" id="validateCode" /> 
	<img src="${pageContext.request.contextPath }/validate.images" id="validateCodeImg" width="80" height="30"/>
	<a href="#this" id="changeValidateCode" style="font-size: 12px;">看不清，换一张</a>
	<script>
		$('#changeValidateCode').click(function() {
			var orginSrc = $('#validateCodeImg').attr('src');
			$('#validateCodeImg').attr('src', orginSrc + '?');
		});
	</script>
	<label class="checkbox"> 
		<input type="checkbox" name="rememberme" value="true">
		记住我
	</label>
	<button type="submit" class="btn btn-large btn-primary">登录</button>
</form>
