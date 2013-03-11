<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="pg" uri="http://www.ccesun.com/tags/pager" %>
<%@ taglib prefix="dict" uri="http://www.ccesun.com/tags/dict" %>
<%@ taglib prefix="area" uri="http://www.ccesun.com/tags/area" %>
<div class="row">
    <spring:url value="/contact" var="baseUrl"/>  
    <div class="span10"> 
    	<h3>列表</h3>
	    <form:form modelAttribute="searchForm" action="${REQUEST_URI }" class="form-inline" >
	    	<form:input path="form['name_eq']" id="search_name" placeholder="姓名"/>
	    	<form:input path="form['phone_eq']" id="search_phone" placeholder="电话"/>
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
		    	<td><dict:lookupDictValue type="XINGB" key="${entry.sex}" /></td>
		    	<td>${entry.phone}</td>
		    	<td><area:lookupFullAreaName areaCode="${entry.areacode}" /></td>
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
    <div id="pager"></div>  
</div> 





