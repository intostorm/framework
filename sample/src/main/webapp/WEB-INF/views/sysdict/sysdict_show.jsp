<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div>

    <spring:url value="/sysDict" var="baseUrl"/>   

    <h1>信息</h1>

    <div id="sysDictInfo">

        <table>
	    	<tr>
                <td>未指定</td>
                <td>${sysDict.recordId}</td>
            </tr>
	    	<tr>
                <td>未指定</td>
                <td>${sysDict.dictType}</td>
            </tr>
	    	<tr>
                <td>未指定</td>
                <td>${sysDict.dictKey}</td>
            </tr>
	    	<tr>
                <td>未指定</td>
                <td>${sysDict.dictValue0}</td>
            </tr>
	    	<tr>
                <td>未指定</td>
                <td>${sysDict.dictValue1}</td>
            </tr>
	    	<tr>
                <td>未指定</td>
                <td>${sysDict.dictValue2}</td>
            </tr>
	    	<tr>
                <td>未指定</td>
                <td>${sysDict.dictValue3}</td>
            </tr>
        </table>         
       
        <a href="${baseUrl}/${sysDict.recordId}/update">修改</a>
        <a href="${baseUrl}/${sysDict.recordId}/remove">删除</a>
        <a href="${baseUrl}">返回列表</a>     
        <a href="${baseUrl}/create">新建</a>     
                      
    </div>

</div>
