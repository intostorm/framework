<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="row">
    
    <spring:url value="/sysArea" var="baseUrl"/>    
    
    <c:set var="labelNew" value="新建" />
    <c:set var="labelUpdate" value="更新" />
    <spring:eval expression="sysArea.recordId == null ? labelNew:labelUpdate" var="formTitle"/>

    <div class="span10">
    <form:form modelAttribute="sysArea" id="sysAreaForm" method="post">
		<fieldset>
			<legend>${formTitle}</legend>
	        <c:if test="${not empty message}">
	            <div id="message" class="${message.type}">${message.message}</div>
	        </c:if>
	        <form:hidden path="recordId" />
	        
	        <form:label path="areacode">编码</form:label>
	        <form:input path="areacode" />
	        <form:errors path="areacode" cssClass="error" element="div" />
	        <p/>
	        
	        <form:label path="areaname">简称</form:label>
	        <form:input path="areaname" />
	        <form:errors path="areaname" cssClass="error" element="div" />
	        <p/>
	        
	        <form:label path="fullAreaname">全称</form:label>
	        <form:input path="fullAreaname" />
	        <form:errors path="fullAreaname" cssClass="error" element="div" />
	        <p/>
	        
	        <form:label path="isAvailable">是否可用</form:label>
	        <form:input path="isAvailable" />
	        <form:errors path="isAvailable" cssClass="error" element="div" />
	        <p/>
	        
	        
	        <button type="submit" class="btn btn-primary">提交</button>
	        <button type="reset" class="btn">重置</button>
	        <a href="${baseUrl}" class="btn">返回列表</a>
        </fieldset>              
    </form:form>   
    </div>

</div>
