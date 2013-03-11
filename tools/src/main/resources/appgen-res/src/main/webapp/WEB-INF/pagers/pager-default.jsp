<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 
	//pageNo 		当前页码
    //totalCount	总记录数
    //totalPages	总页数
    //indexSize		索引数
    //minIndexNo	最小索引号
    //maxIndexNo	最大索引号
    //url			地址
    //firstUrl		首页地址
    //previousUrl	前一页地址
    //nextUrl		后一页地址
    //lastUrl		末页地址
    //isFirstAvailable		首页是否可用
    //isPreviousAvailable	前一页是否可用
    //isNextAvailable		下一页是否可用
    //isLastAvailable		末页是否可用
    //sp					参数的分隔符 & 或 ?
 -->
<div class="pagination">
	<ul>
		<c:choose>
			<c:when test="${isFirstAvailable eq true}">
				<li class="${pageNo eq 1?' active':''}"><a href="${firstUrl}">首页</a></li>
			</c:when>
			<c:otherwise>
				<li class="disabled"><a href="javascript:;">首页</a></li>
			</c:otherwise>
		</c:choose>
		<c:if test="${isPreviousAvailable == 'true'}">
			<li><a href="${previousUrl}">&lt; 上一页</a></li>
		</c:if>
		<c:choose>
			<c:when test="${totalPages eq 0}">
				<li class="active"><a href="${url}${sp}pageNo=1">1</a></li>
			</c:when>
			<c:otherwise>
				<c:forEach begin="${minIndexNo }" end="${maxIndexNo }" var="index">
		        	<li class="${pageNo eq index?'active':''}"><a href="${url}${sp}pageNo=${index}">${index }</a></li>
		        </c:forEach>
			</c:otherwise>
		</c:choose>
		<c:if test="${isNextAvailable == 'true'}">
			<li><a href="${nextUrl}">下一页 &gt;</a></li>
		</c:if>
       	<c:choose>
			<c:when test="${isLastAvailable eq true}">
        		<li class="${pageNo eq totalPages?' active':''}"><a href="${lastUrl}">末页</a></li>
			</c:when>
			<c:otherwise>
        		<li class="disabled"><a href="javascript:;">末页</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
</div>