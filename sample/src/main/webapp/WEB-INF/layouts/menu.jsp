<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="staticpage" uri="http://www.ccesun.com/tags/staticpage" %>
<%@ taglib prefix="security" uri="http://www.ccesun.com/tags/security" %>

<div class="well sidebar-nav">
  <ul class="nav nav-list">
    <li class="nav-header">SIDEBAR</li>
    
    <security:hasPerm permCode="1">
    	<li <c:if test="${fn:startsWith(RELATIVE_REQUESTURI, '/contact') }" >class="active"</c:if>><a href="${pageContext.request.contextPath}/contact">联系人</a></li>
    </security:hasPerm>
    <security:hasPerm permCode="2">
    	<li <c:if test="${fn:startsWith(RELATIVE_REQUESTURI, '/fileUpload') }" >class="active"</c:if>><a href="${pageContext.request.contextPath}/fileUpload">文件上传</a></li>
    </security:hasPerm>
    <security:permFilter groupCode="security" var="securityPerms" />
    <c:forEach items="${securityPerms }" var="perm">
    	<li <c:if test="${fn:startsWith(RELATIVE_REQUESTURI, perm.url) }" >class="active"</c:if>><a href="${pageContext.request.contextPath}${perm.url}">${perm.name }</a></li>
    </c:forEach>
  </ul>
</div><!--/.well --> 