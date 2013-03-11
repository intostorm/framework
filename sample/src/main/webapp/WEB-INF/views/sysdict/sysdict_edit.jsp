<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div>
    
    <spring:url value="/sysDict" var="baseUrl"/>    
    
    <c:set var="labelNew" value="新建" />
    <c:set var="labelUpdate" value="更新" />
    <spring:eval expression="sysDict.recordId == null ? labelNew:labelUpdate" var="formTitle"/>

    <h1>${formTitle}</h1>

    <div>
    <form:form modelAttribute="sysDict" id="sysDictForm" method="post">

        <c:if test="${not empty message}">
            <div id="message" class="${message.type}">${message.message}</div>
        </c:if>
        <form:hidden path="recordId" />
        
        <form:label path="dictType">未指定</form:label>
        <form:input path="dictType" />
        <form:errors path="dictType" cssClass="error" element="div" />
        <p/>
        
        <form:label path="dictKey">未指定</form:label>
        <form:input path="dictKey" />
        <form:errors path="dictKey" cssClass="error" element="div" />
        <p/>
        
        <form:label path="dictValue0">未指定</form:label>
        <form:input path="dictValue0" />
        <form:errors path="dictValue0" cssClass="error" element="div" />
        <p/>
        
        <form:label path="dictValue1">未指定</form:label>
        <form:input path="dictValue1" />
        <form:errors path="dictValue1" cssClass="error" element="div" />
        <p/>
        
        <form:label path="dictValue2">未指定</form:label>
        <form:input path="dictValue2" />
        <form:errors path="dictValue2" cssClass="error" element="div" />
        <p/>
        
        <form:label path="dictValue3">未指定</form:label>
        <form:input path="dictValue3" />
        <form:errors path="dictValue3" cssClass="error" element="div" />
        <p/>
        
        
        <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
            <span class="ui-button-text">提交</span>
        </button> 
        <button type="reset" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
            <span class="ui-button-text">重置</span>
        </button>           
        
        <a href="${baseUrl}">返回列表</a>
                      
    </form:form>   
    </div>

</div>
