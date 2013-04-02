<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="pg" uri="http://www.ccesun.com/tags/pager" %>
<div class="row">

	<spring:url value="/sysUser" var="baseUrl"/>  
    
    <div class="span10"> 
    	<h3>分配 ${sysUser.userName } 的角色</h3>
    	<form:form modelAttribute="assignRoleForm">
    	<form:hidden path="userId" />
	    <table class="table table-bordered table-hover"> 
	    	<tr>
		    	<td><input type="checkbox" id="checkHandler"/></td>
		    	<script>
					$('#checkHandler').click(function() {
						var checked = $(this).attr('checked');
						if (!checked) checked = false;
						$('.checkboxes').attr('checked', checked);
					});
		    	</script>
		    	<td>主键</td>
		    	<td>角色名</td>
		    	<td>描述</td>
	    	</tr>
	    	<c:forEach items="${roles}" var="entry">
	    	<tr>
		    	<td><form:checkbox path="roleIds" cssClass="checkboxes" value="${entry.roleId}"/> </td>
		    	<td>${entry.roleId}</td>
		    	<td>${entry.roleName}</td>
		    	<td>${entry.roleRemarks}</td>
	    	</tr>	
	    	</c:forEach>
	    </table> 
    	
    	<button type="submit" class="btn btn-primary">提交</button>
        <a href="${baseUrl}" class="btn">返回列表</a>
    	</form:form>
    </div>

</div>