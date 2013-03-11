<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="row">
    
    <spring:url value="/sysFunc" var="baseUrl"/>    
    
    <c:set var="labelNew" value="新建" />
    <c:set var="labelUpdate" value="更新" />
    <spring:eval expression="sysFunc.funcId == null ? labelNew:labelUpdate" var="formTitle"/>

    <div class="span10">
    <form:form modelAttribute="sysFunc" id="sysFuncForm" method="post">
		<fieldset>
			<legend>${formTitle}</legend>
	        <c:if test="${not empty message}">
	            <div id="message" class="${message.type}">${message.message}</div>
	        </c:if>
	        <form:hidden path="funcId" />
	        
	        <form:label path="funcName">功能名</form:label>
	        <form:input path="funcName" />
	        <form:errors path="funcName" cssClass="error" element="div" />
	        <p/>
	        
	        <form:label path="funcUrl">功能地址</form:label>
	        <form:input path="funcUrl" />
	        <form:errors path="funcUrl" cssClass="error" element="div" />
	        <p/>
	        
	        <form:label path="funcRemarks">描述</form:label>
	        <form:input path="funcRemarks" />
	        <form:errors path="funcRemarks" cssClass="error" element="div" />
	        <p/>
	        
	        <form:label path="funcLevel">功能级别</form:label>
	        <form:input path="funcLevel" />
	        <form:errors path="funcLevel" cssClass="error" element="div" />
	        <p/>
	        
	        <form:label path="parentId">父功能</form:label>
	        <form:input path="parentId" />
	        <form:errors path="parentId" cssClass="error" element="div" />
	        <p/>
	        
	        
	        <button type="submit" class="btn btn-primary">提交</button>
	        <button type="reset" class="btn">重置</button>
	        <a href="${baseUrl}" class="btn">返回列表</a>
        </fieldset>              
    </form:form>   
    </div>

</div>
