<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="row">
    
    <spring:url value="/sysRole" var="baseUrl"/>    
    
    <c:set var="labelNew" value="新建" />
    <c:set var="labelUpdate" value="更新" />
    <spring:eval expression="sysRole.roleId == null ? labelNew:labelUpdate" var="formTitle"/>

    <div class="span10">
    <form:form modelAttribute="sysRole" id="sysRoleForm" method="post">
		<fieldset>
			<legend>${formTitle}</legend>
	        <c:if test="${not empty message}">
	            <div id="message" class="${message.type}">${message.message}</div>
	        </c:if>
	        <form:hidden path="roleId" />
	        
	        <form:label path="roleName">角色名</form:label>
	        <form:input path="roleName" />
	        <form:errors path="roleName" cssClass="error" element="div" />
	        <p/>
	        
	        <form:label path="roleRemarks">描述</form:label>
	        <form:input path="roleRemarks" />
	        <form:errors path="roleRemarks" cssClass="error" element="div" />
	        <p/>
	        
	        
	        <button type="submit" class="btn btn-primary">提交</button>
	        <button type="reset" class="btn">重置</button>
	        <a href="${baseUrl}" class="btn">返回列表</a>
        </fieldset>              
    </form:form>   
    </div>

</div>
