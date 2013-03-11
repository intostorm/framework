<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="row">

    <spring:url value="/sysDict" var="baseUrl"/>   

    <h3>信息</h3>

    <div id="sysDictInfo" class="span10">

        <table class="table table-bordered table-hover">
	    	<tr>
                <td>主键</td>
                <td>${sysDict.recordId}</td>
            </tr>
	    	<tr>
                <td>类型</td>
                <td>${sysDict.dictType}</td>
            </tr>
	    	<tr>
                <td>键</td>
                <td>${sysDict.dictKey}</td>
            </tr>
	    	<tr>
                <td>值0</td>
                <td>${sysDict.dictValue0}</td>
            </tr>
	    	<tr>
                <td>值1</td>
                <td>${sysDict.dictValue1}</td>
            </tr>
	    	<tr>
                <td>值2</td>
                <td>${sysDict.dictValue2}</td>
            </tr>
	    	<tr>
                <td>值3</td>
                <td>${sysDict.dictValue3}</td>
            </tr>
	    	<tr>
                <td>父级KEY</td>
                <td>${sysDict.parentKey}</td>
            </tr>
        </table>         
       
        <a href="${baseUrl}/${sysDict.recordId}/update" class="btn">修改</a>
        <a href="${baseUrl}/${sysDict.recordId}/remove" class="btn">删除</a>
        <a href="${baseUrl}" class="btn">返回列表</a>     
        <a href="${baseUrl}/create" class="btn">新建</a>     
                      
    </div>

</div>
