<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="pg" uri="http://www.ccesun.com/tags/pager" %>
<div>
    <spring:url value="/sysDict" var="baseUrl"/>  
    
    <h2>列表</h2>
    <div>
    <form:form modelAttribute="searchForm">
    	<form:label path="form['recordId_eq_int']" for="search_recordId">未指定</form:label>
    	<form:input path="form['recordId_eq_int']" id="search_recordId"/>
    	<form:label path="form['dictType_eq']" for="search_dictType">未指定</form:label>
    	<form:input path="form['dictType_eq']" id="search_dictType"/>
    	<form:label path="form['dictKey_eq']" for="search_dictKey">未指定</form:label>
    	<form:input path="form['dictKey_eq']" id="search_dictKey"/>
    	<form:label path="form['dictValue0_eq']" for="search_dictValue0">未指定</form:label>
    	<form:input path="form['dictValue0_eq']" id="search_dictValue0"/>
    	<form:label path="form['dictValue1_eq']" for="search_dictValue1">未指定</form:label>
    	<form:input path="form['dictValue1_eq']" id="search_dictValue1"/>
    	<form:label path="form['dictValue2_eq']" for="search_dictValue2">未指定</form:label>
    	<form:input path="form['dictValue2_eq']" id="search_dictValue2"/>
    	<form:label path="form['dictValue3_eq']" for="search_dictValue3">未指定</form:label>
    	<form:input path="form['dictValue3_eq']" id="search_dictValue3"/>
    	<button type="submit">搜索</button>
    </form:form>
    
    <table id="list">
    	<tr>
	    	<td>未指定</td>
	    	<td>未指定</td>
	    	<td>未指定</td>
	    	<td>未指定</td>
	    	<td>未指定</td>
	    	<td>未指定</td>
	    	<td>未指定</td>
	    	<td>操作</td>
    	</tr>
    	<c:forEach items="${sysDictPage.content}" var="entry">
    	<tr>
	    	<td>${entry.recordId}</td>
	    	<td>${entry.dictType}</td>
	    	<td>${entry.dictKey}</td>
	    	<td>${entry.dictValue0}</td>
	    	<td>${entry.dictValue1}</td>
	    	<td>${entry.dictValue2}</td>
	    	<td>${entry.dictValue3}</td>
	    	<td>
	    		<a href="${baseUrl}/${entry.recordId}/show">查看</a> 
	    		<a href="${baseUrl}/${entry.recordId}/update">修改</a> 
	    		<a href="${baseUrl}/${entry.recordId}/remove">删除</a> 
	    	</td>
    	</tr>	
    	</c:forEach>
    </table> 
    <pg:pager url="${baseUrl}" page="${sysDictPage}" />
    <%@ include file="/WEB-INF/pagers/pager01.jsp" %>
    <a href="${baseUrl}/create">新建</a>
    </div>
    <div id="pager"></div>  
</div>