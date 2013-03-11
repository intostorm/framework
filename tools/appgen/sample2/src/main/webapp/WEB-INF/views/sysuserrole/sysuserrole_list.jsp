<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="pg" uri="http://www.ccesun.com/tags/pager" %>
<div class="row">
    <spring:url value="/sysUserRole" var="baseUrl"/>  
    
    <div class="span10"> 
    	<h3>列表</h3>
	    <form:form modelAttribute="searchForm" action="${REQUEST_URI}" class="form-inline" >
	    	<form:input path="form['id_eq_int']" id="search_id" placeholder="主键"/>
	    	<form:input path="form['userId_eq_int']" id="search_userId" placeholder="用户ID"/>
	    	<form:input path="form['roleId_eq_int']" id="search_roleId" placeholder="角色ID"/>
	    	<button type="submit" class="btn"><i class="icon-search"></i> 搜索</button>
	    </form:form>
    
	    <table class="table table-bordered table-hover"> 
	    	<tr>
		    	<td>主键</td>
		    	<td>用户ID</td>
		    	<td>角色ID</td>
		    	<td>操作</td>
	    	</tr>
	    	<c:forEach items="${sysUserRolePage.content}" var="entry">
	    	<tr>
		    	<td>${entry.id}</td>
		    	<td>${entry.userId}</td>
		    	<td>${entry.roleId}</td>
		    	<td>
		    		<a href="${baseUrl}/${entry.id}/show" class="btn btn-small"><i class="icon-eye-open"></i></a> 
		    		<a href="${baseUrl}/${entry.id}/update" class="btn btn-small"><i class="icon-edit"></i></a> 
		    		<a href="${baseUrl}/${entry.id}/remove" class="btn btn-small" onclick="return confirm('确定要删除吗？')"><i class="icon-remove-sign"></i></a> 
		    	</td>
	    	</tr>	
	    	</c:forEach>
	    </table> 
    	
    	<pg:pager url="${baseUrl}" page="${sysUserRolePage}" />
    	<%@ include file="/WEB-INF/pagers/pager-default.jsp" %>
    	<a href="${baseUrl}/create" class="btn"><i class="icon-file"></i> 新建</a>
    </div>

</div>