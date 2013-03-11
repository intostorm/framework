<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="row">

    <spring:url value="/sysRole" var="baseUrl"/>   

    <h3>信息</h3>

    <div id="sysRoleInfo" class="span10">

        <table class="table table-bordered table-hover">
	    	<tr>
                <td>主键</td>
                <td>${sysRole.roleId}</td>
            </tr>
	    	<tr>
                <td>角色名</td>
                <td>${sysRole.roleName}</td>
            </tr>
	    	<tr>
                <td>描述</td>
                <td>${sysRole.roleRemarks}</td>
            </tr>
        </table>         
       
        <a href="${baseUrl}/${sysRole.roleId}/update" class="btn">修改</a>
        <a href="${baseUrl}/${sysRole.roleId}/remove" class="btn">删除</a>
        <a href="${baseUrl}" class="btn">返回列表</a>     
        <a href="${baseUrl}/create" class="btn">新建</a>     
                      
    </div>

</div>
