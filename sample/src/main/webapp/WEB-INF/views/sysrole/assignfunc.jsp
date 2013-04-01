<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="pg" uri="http://www.ccesun.com/tags/pager" %>
<div class="row">

	
    
    <div class="span10"> 
    	<h3>${sysRole.roleName }</h3>
    	<form:form modelAttribute="assignFuncForm">
    	<form:hidden path="roleId" />
	    <table class="table table-bordered table-hover"> 
	    	<tr>
		    	<td><input type="checkbox" id="checkHandler"/></td>
		    	<script>
					$('#checkHandler').click(function() {
						var checked = $(this).attr('checked');
						if (!checked) checked = false;
						$('.funcIds').attr('checked', checked);
					});
		    	</script>
		    	<td>主键</td>
		    	<td>功能组</td>
		    	<td>父级编码</td>
		    	<td>功能名</td>
		    	<td>功能地址</td>
		    	<td>序号</td>
	    	</tr>
	    	<c:forEach items="${functions}" var="entry">
	    	<tr>
		    	<td><form:checkbox path="funcIds" cssClass="funcIds" value="${entry.funcId}"/> </td>
		    	<td>${entry.funcId}</td>
		    	<td>${entry.funcGroupCode}</td>
		    	<td>${entry.parent.funcId}</td>
		    	<td>${entry.funcName}</td>
		    	<td>${entry.funcUrl}</td>
		    	<td>${entry.funcOrder}</td>
	    	</tr>	
	    	</c:forEach>
	    </table> 
    	
    	<button class="btn"><i class="icon-file"></i> 保存</button>
    	</form:form>
    </div>

</div>