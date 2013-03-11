<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="pg" uri="http://www.ccesun.com/tags/pager" %>
<div class="row">
    <spring:url value="/sysArea" var="baseUrl"/>  
    
    <div class="span10"> 
    	<h3>列表</h3>
	    <form:form modelAttribute="searchForm" action="${REQUEST_URI}" class="form-inline" >
	    	<form:input path="form['recordId_eq_int']" id="search_recordId" placeholder="主键"/>
	    	<form:input path="form['areacode_eq']" id="search_areacode" placeholder="编码"/>
	    	<form:input path="form['areaname_eq']" id="search_areaname" placeholder="简称"/>
	    	<form:input path="form['fullAreaname_eq']" id="search_fullAreaname" placeholder="全称"/>
	    	<form:input path="form['isAvailable_eq_int']" id="search_isAvailable" placeholder="是否可用"/>
	    	<button type="submit" class="btn"><i class="icon-search"></i> 搜索</button>
	    </form:form>
    
	    <table class="table table-bordered table-hover"> 
	    	<tr>
		    	<td>主键</td>
		    	<td>编码</td>
		    	<td>简称</td>
		    	<td>全称</td>
		    	<td>是否可用</td>
		    	<td>操作</td>
	    	</tr>
	    	<c:forEach items="${sysAreaPage.content}" var="entry">
	    	<tr>
		    	<td>${entry.recordId}</td>
		    	<td>${entry.areacode}</td>
		    	<td>${entry.areaname}</td>
		    	<td>${entry.fullAreaname}</td>
		    	<td>${entry.isAvailable}</td>
		    	<td>
		    		<a href="${baseUrl}/${entry.recordId}/show" class="btn btn-small"><i class="icon-eye-open"></i></a> 
		    		<a href="${baseUrl}/${entry.recordId}/update" class="btn btn-small"><i class="icon-edit"></i></a> 
		    		<a href="${baseUrl}/${entry.recordId}/remove" class="btn btn-small" onclick="return confirm('确定要删除吗？')"><i class="icon-remove-sign"></i></a> 
		    	</td>
	    	</tr>	
	    	</c:forEach>
	    </table> 
    	
    	<pg:pager url="${baseUrl}" page="${sysAreaPage}" />
    	<%@ include file="/WEB-INF/pagers/pager-default.jsp" %>
    	<a href="${baseUrl}/create" class="btn"><i class="icon-file"></i> 新建</a>
    </div>

</div>