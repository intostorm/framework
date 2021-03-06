<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dict" uri="http://www.ccesun.com/tags/dict" %>
<%@ taglib prefix="area" uri="http://www.ccesun.com/tags/area" %>
<div class="row">

    <spring:url value="/contact" var="baseUrl"/>   

    <h3>信息</h3>

    <div id="contactInfo" class="span10">

        <table class="table table-bordered table-hover">
	    	<tr>
                <td>主键</td>
                <td>${contact.recordId}</td>
            </tr>
	    	<tr>
                <td>姓名</td>
                <td>${contact.name}</td>
            </tr>
	    	<tr>
                <td>性别</td>
                <td><dict:lookupDictValue type="XINGB" key="${contact.sex}" /></td>
            </tr>
	    	<tr>
                <td>电话</td>
                <td>${contact.phone}</td>
            </tr>
	    	<tr>
                <td>地区</td>
                <td><area:lookupFullAreaName areaCode="${contact.areacode}" /></td>
            </tr>
	    	<tr>
                <td>地址</td>  
                <td>${contact.address}</td>
            </tr> 
        </table>         
        <a href="${baseUrl}" class="btn">返回列表</a>     
        <a href="${baseUrl}/create" class="btn">新建</a> 
        <a href="${baseUrl}/${contact.recordId}/update" class="btn">修改</a>
        <a href="${baseUrl}/${contact.recordId}/remove"  class="btn btn-danger">删除</a>
    </div>

</div>
