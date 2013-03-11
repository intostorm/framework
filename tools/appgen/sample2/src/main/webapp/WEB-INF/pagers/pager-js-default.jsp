<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script id="pagerTemplate01" type="text/html">
<div class="pagination">
	<ul>
		{% if (isFirstAvailable == true) { %}
			<li class="{% if (pageNo == '1') { %}active{% } %}"><a href="javascript:;" pageNo="1">首页</a>
		{% } else { %}
			<li class="disabled"><a href="javascript:;">首页</a></li>
		{% } %}
		{% if (isPreviousAvailable == true) { %}
			<li><a href="javascript:;" pageNo="{%= pageNo - 1 %}">&lt; 上一页</a></li>
		{% } else { %}
			<li class="disabled"><a href="javascript:;">&lt; 上一页</a></li>
		{% } %}
		{% if (totalPages == 0) { %}
			<li class="active"><a href="javascript:;">1</a></li>
		{% } else { %}
			{% for (var index = minIndexNo; index <= maxIndexNo; index++) { %}
		        <li class="{% if (pageNo == index) { %}active{% } %}"><a href="javascript:;" pageNo="{%= index %}" >{%= index %}</a></li>
		    {% } %}
		{% } %}
		{% if (isNextAvailable == true) { %}
			<li><a href="javascript:;" pageNo="{%= pageNo + 1 %}">下一页 &gt;</a></li>
		{% } else { %}
			<li class="disabled"><a href="javascript:;">下一页 &gt;</a></li>
		{% } %}
		{% if (isLastAvailable == true) { %} 
	        <li class="{%if (pageNo == totalPages) { %}active{% } %}"><a href="javascript:;" pageNo="{%= totalPages %}">末页</a></li>
		{% } else { %}
	        <li class="disabled"><a href="javascript:;">末页</a></li>
		{% } %}
	</ul>
</div>
</script>