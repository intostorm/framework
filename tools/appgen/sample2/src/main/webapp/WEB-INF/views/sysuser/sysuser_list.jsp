<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="pg" uri="http://www.ccesun.com/tags/pager" %>
<div class="row">
    <spring:url value="/sysUser" var="baseUrl"/>  
    
    <div class="span10"> 
    	<h3>列表</h3>
	    <form:form modelAttribute="searchForm" action="${REQUEST_URI}" class="form-inline" >
	    	<form:input path="form['userId_eq_int']" id="search_userId" placeholder="主键"/>
	    	<form:input path="form['userName_eq']" id="search_userName" placeholder="用户名"/>
	    	<form:input path="form['realName_eq']" id="search_realName" placeholder="姓名"/>
	    	<form:input path="form['passwd_eq']" id="search_passwd" placeholder="密码"/>
	    	<form:input path="form['isAvailable_eq_int']" id="search_isAvailable" placeholder="是否可用"/>
	    	<button type="submit" class="btn"><i class="icon-search"></i> 搜索</button>
	    </form:form>
    
	    <table class="table table-bordered table-hover"> 
	    	<tr>
		    	<td>主键</td>
		    	<td>用户名</td>
		    	<td>姓名</td>
		    	<td>密码</td>
		    	<td>是否可用</td>
		    	<td>操作</td>
	    	</tr>
	    	<c:forEach items="${sysUserPage.content}" var="entry">
	    	<tr>
		    	<td>${entry.userId}</td>
		    	<td>${entry.userName}</td>
		    	<td>${entry.realName}</td>
		    	<td>${entry.passwd}</td>
		    	<td>${entry.isAvailable}</td>
		    	<td>
		    		<a href="${baseUrl}/${entry.userId}/show" class="btn btn-small"><i class="icon-eye-open"></i></a> 
		    		<a href="${baseUrl}/${entry.userId}/update" class="btn btn-small"><i class="icon-edit"></i></a> 
		    		<a href="${baseUrl}/${entry.userId}/remove" class="btn btn-small" onclick="return confirm('确定要删除吗？')"><i class="icon-remove-sign"></i></a> 
		    	</td>
	    	</tr>	
	    	</c:forEach>
	    </table> 
    	
    	<pg:pager url="${baseUrl}" page="${sysUserPage}" />
    	<%@ include file="/WEB-INF/pagers/pager-default.jsp" %>
    	<a href="${baseUrl}/create" class="btn"><i class="icon-file"></i> 新建</a>
    </div>

</div>