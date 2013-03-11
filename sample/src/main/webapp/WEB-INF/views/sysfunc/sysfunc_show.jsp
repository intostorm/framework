<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="row">

    <spring:url value="/sysFunc" var="baseUrl"/>   

    <h3>信息</h3>

    <div id="sysFuncInfo" class="span10">

        <table class="table table-bordered table-hover">
	    	<tr>
                <td>主键</td>
                <td>${sysFunc.funcId}</td>
            </tr>
	    	<tr>
                <td>功能名</td>
                <td>${sysFunc.funcName}</td>
            </tr>
	    	<tr>
                <td>功能地址</td>
                <td>${sysFunc.funcUrl}</td>
            </tr>
	    	<tr>
                <td>描述</td>
                <td>${sysFunc.funcRemarks}</td>
            </tr>
        </table>         
       
        <a href="${baseUrl}/${sysFunc.funcId}/update" class="btn">修改</a>
        <a href="${baseUrl}/${sysFunc.funcId}/remove" class="btn">删除</a>
        <a href="${baseUrl}" class="btn">返回列表</a>     
        <a href="${baseUrl}/create" class="btn">新建</a>     
                      
    </div>

</div>
