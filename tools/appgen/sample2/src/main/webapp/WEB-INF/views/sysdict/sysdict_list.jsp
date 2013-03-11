<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="pg" uri="http://www.ccesun.com/tags/pager" %>
<div class="row">
    <spring:url value="/sysDict" var="baseUrl"/>  
    
    <div class="span10"> 
    	<h3>列表</h3>
	    <form:form modelAttribute="searchForm" action="${REQUEST_URI}" class="form-inline" >
	    	<form:input path="form['recordId_eq_int']" id="search_recordId" placeholder="主键"/>
	    	<form:input path="form['dictType_eq']" id="search_dictType" placeholder="类型"/>
	    	<form:input path="form['dictKey_eq']" id="search_dictKey" placeholder="键"/>
	    	<form:input path="form['dictValue0_eq']" id="search_dictValue0" placeholder="值0"/>
	    	<form:input path="form['dictValue1_eq']" id="search_dictValue1" placeholder="值1"/>
	    	<form:input path="form['dictValue2_eq']" id="search_dictValue2" placeholder="值2"/>
	    	<form:input path="form['dictValue3_eq']" id="search_dictValue3" placeholder="值3"/>
	    	<form:input path="form['parentKey_eq']" id="search_parentKey" placeholder="父级KEY"/>
	    	<button type="submit" class="btn"><i class="icon-search"></i> 搜索</button>
	    </form:form>
    
	    <table class="table table-bordered table-hover"> 
	    	<tr>
		    	<td>主键</td>
		    	<td>类型</td>
		    	<td>键</td>
		    	<td>值0</td>
		    	<td>值1</td>
		    	<td>值2</td>
		    	<td>值3</td>
		    	<td>父级KEY</td>
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
		    	<td>${entry.parentKey}</td>
		    	<td>
		    		<a href="${baseUrl}/${entry.recordId}/show" class="btn btn-small"><i class="icon-eye-open"></i></a> 
		    		<a href="${baseUrl}/${entry.recordId}/update" class="btn btn-small"><i class="icon-edit"></i></a> 
		    		<a href="${baseUrl}/${entry.recordId}/remove" class="btn btn-small" onclick="return confirm('确定要删除吗？')"><i class="icon-remove-sign"></i></a> 
		    	</td>
	    	</tr>	
	    	</c:forEach>
	    </table> 
    	
    	<pg:pager url="${baseUrl}" page="${sysDictPage}" />
    	<%@ include file="/WEB-INF/pagers/pager-default.jsp" %>
    	<a href="${baseUrl}/create" class="btn"><i class="icon-file"></i> 新建</a>
    </div>

</div>