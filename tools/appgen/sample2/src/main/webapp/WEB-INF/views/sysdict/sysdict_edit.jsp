<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="row">
    
    <spring:url value="/sysDict" var="baseUrl"/>    
    
    <c:set var="labelNew" value="新建" />
    <c:set var="labelUpdate" value="更新" />
    <spring:eval expression="sysDict.recordId == null ? labelNew:labelUpdate" var="formTitle"/>

    <div class="span10">
    <form:form modelAttribute="sysDict" id="sysDictForm" method="post">
		<fieldset>
			<legend>${formTitle}</legend>
	        <c:if test="${not empty message}">
	            <div id="message" class="${message.type}">${message.message}</div>
	        </c:if>
	        <form:hidden path="recordId" />
	        
	        <form:label path="dictType">类型</form:label>
	        <form:input path="dictType" />
	        <form:errors path="dictType" cssClass="error" element="div" />
	        <p/>
	        
	        <form:label path="dictKey">键</form:label>
	        <form:input path="dictKey" />
	        <form:errors path="dictKey" cssClass="error" element="div" />
	        <p/>
	        
	        <form:label path="dictValue0">值0</form:label>
	        <form:input path="dictValue0" />
	        <form:errors path="dictValue0" cssClass="error" element="div" />
	        <p/>
	        
	        <form:label path="dictValue1">值1</form:label>
	        <form:input path="dictValue1" />
	        <form:errors path="dictValue1" cssClass="error" element="div" />
	        <p/>
	        
	        <form:label path="dictValue2">值2</form:label>
	        <form:input path="dictValue2" />
	        <form:errors path="dictValue2" cssClass="error" element="div" />
	        <p/>
	        
	        <form:label path="dictValue3">值3</form:label>
	        <form:input path="dictValue3" />
	        <form:errors path="dictValue3" cssClass="error" element="div" />
	        <p/>
	        
	        <form:label path="parentKey">父级KEY</form:label>
	        <form:input path="parentKey" />
	        <form:errors path="parentKey" cssClass="error" element="div" />
	        <p/>
	        
	        
	        <button type="submit" class="btn btn-primary">提交</button>
	        <button type="reset" class="btn">重置</button>
	        <a href="${baseUrl}" class="btn">返回列表</a>
        </fieldset>              
    </form:form>   
    </div>

</div>
