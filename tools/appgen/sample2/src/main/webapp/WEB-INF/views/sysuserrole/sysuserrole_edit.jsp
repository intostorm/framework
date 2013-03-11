<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="row">
    
    <spring:url value="/sysUserRole" var="baseUrl"/>    
    
    <c:set var="labelNew" value="新建" />
    <c:set var="labelUpdate" value="更新" />
    <spring:eval expression="sysUserRole.id == null ? labelNew:labelUpdate" var="formTitle"/>

    <div class="span10">
    <form:form modelAttribute="sysUserRole" id="sysUserRoleForm" method="post">
		<fieldset>
			<legend>${formTitle}</legend>
	        <c:if test="${not empty message}">
	            <div id="message" class="${message.type}">${message.message}</div>
	        </c:if>
	        <form:hidden path="id" />
	        
	        <form:label path="userId">用户ID</form:label>
	        <form:input path="userId" />
	        <form:errors path="userId" cssClass="error" element="div" />
	        <p/>
	        
	        <form:label path="roleId">角色ID</form:label>
	        <form:input path="roleId" />
	        <form:errors path="roleId" cssClass="error" element="div" />
	        <p/>
	        
	        
	        <button type="submit" class="btn btn-primary">提交</button>
	        <button type="reset" class="btn">重置</button>
	        <a href="${baseUrl}" class="btn">返回列表</a>
        </fieldset>              
    </form:form>   
    </div>

</div>
