<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="row">
    
    <spring:url value="/fileUpload" var="baseUrl"/>    

    <div class="span10">
    <form id="contactForm" method="post" action="${baseUrl }" enctype="multipart/form-data">
		<fieldset>
			<legend>文件上传</legend>
	        <label name="files">选择文件1</label>
	        <input type="file" name="files"/>
	        <p/>
	        
	        <label name="files">选择文件2</label>
	        <input type="file" name="files"/>
	        <p/>
	        
	        <label name="files">选择文件3</label>
	        <input type="file" name="files"/>
	        <p/>
	        
	        <label name="files">选择文件4</label>
	        <input type="file" name="files"/>
	        <p/>
	        
	        <label name="files">选择文件5</label>
	        <input type="file" name="files"/>
	        <p/>
	        
	        <button type="submit" class="btn">提交</button>
	        <button type="reset" class="btn">重置</button>
        </fieldset>
    </form>   
    </div>

</div>
