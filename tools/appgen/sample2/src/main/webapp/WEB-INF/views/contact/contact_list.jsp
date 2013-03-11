<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="pg" uri="http://www.ccesun.com/tags/pager" %>
<div class="row">
    <spring:url value="/contact" var="baseUrl"/>  
    
    <div class="span10"> 
    	<h3>列表</h3>
	    <form:form modelAttribute="searchForm" action="${REQUEST_URI}" class="form-inline" >
	    	<form:input path="form['recordId_eq_int']" id="search_recordId" placeholder="主键"/>
	    	<form:input path="form['name_eq']" id="search_name" placeholder="姓名"/>
	    	<form:input path="form['sex_eq']" id="search_sex" placeholder="性别"/>
	    	<form:input path="form['phone_eq']" id="search_phone" placeholder="电话"/>
	    	<form:input path="form['areacode_eq']" id="search_areacode" placeholder="地区"/>
	    	<form:input path="form['address_eq']" id="search_address" placeholder="地址"/>
	    	<button type="submit" class="btn"><i class="icon-search"></i> 搜索</button>
	    </form:form>
    
	    <table class="table table-bordered table-hover"> 
	    	<tr>
		    	<td>主键</td>
		    	<td>姓名</td>
		    	<td>性别</td>
		    	<td>电话</td>
		    	<td>地区</td>
		    	<td>地址</td>
		    	<td>操作</td>
	    	</tr>
	    	<c:forEach items="${contactPage.content}" var="entry">
	    	<tr>
		    	<td>${entry.recordId}</td>
		    	<td>${entry.name}</td>
		    	<td>${entry.sex}</td>
		    	<td>${entry.phone}</td>
		    	<td>${entry.areacode}</td>
		    	<td>${entry.address}</td>
		    	<td>
		    		<a href="${baseUrl}/${entry.recordId}/show" class="btn btn-small"><i class="icon-eye-open"></i></a> 
		    		<a href="${baseUrl}/${entry.recordId}/update" class="btn btn-small"><i class="icon-edit"></i></a> 
		    		<a href="${baseUrl}/${entry.recordId}/remove" class="btn btn-small" onclick="return confirm('确定要删除吗？')"><i class="icon-remove-sign"></i></a> 
		    	</td>
	    	</tr>	
	    	</c:forEach>
	    </table> 
    	
    	<pg:pager url="${baseUrl}" page="${contactPage}" />
    	<%@ include file="/WEB-INF/pagers/pager-default.jsp" %>
    	<a href="${baseUrl}/create" class="btn"><i class="icon-file"></i> 新建</a>
    </div>

</div>