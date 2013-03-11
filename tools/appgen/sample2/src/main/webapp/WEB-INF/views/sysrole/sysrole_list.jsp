<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="pg" uri="http://www.ccesun.com/tags/pager" %>
<div class="row">
    <spring:url value="/sysRole" var="baseUrl"/>  
    
    <div class="span10"> 
    	<h3>列表</h3>
	    <form:form modelAttribute="searchForm" action="${REQUEST_URI}" class="form-inline" >
	    	<form:input path="form['roleId_eq_int']" id="search_roleId" placeholder="主键"/>
	    	<form:input path="form['roleName_eq']" id="search_roleName" placeholder="角色名"/>
	    	<form:input path="form['roleRemarks_eq']" id="search_roleRemarks" placeholder="描述"/>
	    	<button type="submit" class="btn"><i class="icon-search"></i> 搜索</button>
	    </form:form>
    
	    <table class="table table-bordered table-hover"> 
	    	<tr>
		    	<td>主键</td>
		    	<td>角色名</td>
		    	<td>描述</td>
		    	<td>操作</td>
	    	</tr>
	    	<c:forEach items="${sysRolePage.content}" var="entry">
	    	<tr>
		    	<td>${entry.roleId}</td>
		    	<td>${entry.roleName}</td>
		    	<td>${entry.roleRemarks}</td>
		    	<td>
		    		<a href="${baseUrl}/${entry.roleId}/show" class="btn btn-small"><i class="icon-eye-open"></i></a> 
		    		<a href="${baseUrl}/${entry.roleId}/update" class="btn btn-small"><i class="icon-edit"></i></a> 
		    		<a href="${baseUrl}/${entry.roleId}/remove" class="btn btn-small" onclick="return confirm('确定要删除吗？')"><i class="icon-remove-sign"></i></a> 
		    	</td>
	    	</tr>	
	    	</c:forEach>
	    </table> 
    	
    	<pg:pager url="${baseUrl}" page="${sysRolePage}" />
    	<%@ include file="/WEB-INF/pagers/pager-default.jsp" %>
    	<a href="${baseUrl}/create" class="btn"><i class="icon-file"></i> 新建</a>
    </div>

</div>