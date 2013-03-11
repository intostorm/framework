<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="row">

    <spring:url value="/sysUserRole" var="baseUrl"/>   

    <h3>信息</h3>

    <div id="sysUserRoleInfo" class="span10">

        <table class="table table-bordered table-hover">
	    	<tr>
                <td>主键</td>
                <td>${sysUserRole.id}</td>
            </tr>
	    	<tr>
                <td>用户ID</td>
                <td>${sysUserRole.userId}</td>
            </tr>
	    	<tr>
                <td>角色ID</td>
                <td>${sysUserRole.roleId}</td>
            </tr>
        </table>         
       
        <a href="${baseUrl}/${sysUserRole.id}/update" class="btn">修改</a>
        <a href="${baseUrl}/${sysUserRole.id}/remove" class="btn">删除</a>
        <a href="${baseUrl}" class="btn">返回列表</a>     
        <a href="${baseUrl}/create" class="btn">新建</a>     
                      
    </div>

</div>
