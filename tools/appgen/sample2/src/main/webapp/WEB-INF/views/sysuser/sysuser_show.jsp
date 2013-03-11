<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="row">

    <spring:url value="/sysUser" var="baseUrl"/>   

    <h3>信息</h3>

    <div id="sysUserInfo" class="span10">

        <table class="table table-bordered table-hover">
	    	<tr>
                <td>主键</td>
                <td>${sysUser.userId}</td>
            </tr>
	    	<tr>
                <td>用户名</td>
                <td>${sysUser.userName}</td>
            </tr>
	    	<tr>
                <td>姓名</td>
                <td>${sysUser.realName}</td>
            </tr>
	    	<tr>
                <td>密码</td>
                <td>${sysUser.passwd}</td>
            </tr>
	    	<tr>
                <td>是否可用</td>
                <td>${sysUser.isAvailable}</td>
            </tr>
        </table>         
       
        <a href="${baseUrl}/${sysUser.userId}/update" class="btn">修改</a>
        <a href="${baseUrl}/${sysUser.userId}/remove" class="btn">删除</a>
        <a href="${baseUrl}" class="btn">返回列表</a>     
        <a href="${baseUrl}/create" class="btn">新建</a>     
                      
    </div>

</div>
