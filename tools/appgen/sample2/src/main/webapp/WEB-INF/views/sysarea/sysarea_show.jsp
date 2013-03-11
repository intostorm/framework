<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="row">

    <spring:url value="/sysArea" var="baseUrl"/>   

    <h3>信息</h3>

    <div id="sysAreaInfo" class="span10">

        <table class="table table-bordered table-hover">
	    	<tr>
                <td>主键</td>
                <td>${sysArea.recordId}</td>
            </tr>
	    	<tr>
                <td>编码</td>
                <td>${sysArea.areacode}</td>
            </tr>
	    	<tr>
                <td>简称</td>
                <td>${sysArea.areaname}</td>
            </tr>
	    	<tr>
                <td>全称</td>
                <td>${sysArea.fullAreaname}</td>
            </tr>
	    	<tr>
                <td>是否可用</td>
                <td>${sysArea.isAvailable}</td>
            </tr>
        </table>         
       
        <a href="${baseUrl}/${sysArea.recordId}/update" class="btn">修改</a>
        <a href="${baseUrl}/${sysArea.recordId}/remove" class="btn">删除</a>
        <a href="${baseUrl}" class="btn">返回列表</a>     
        <a href="${baseUrl}/create" class="btn">新建</a>     
                      
    </div>

</div>
