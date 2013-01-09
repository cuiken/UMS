<%@ page language="java" pageEncoding="UTF-8" %>
<input type="hidden" name="page.pageNo" id="pageNo" value="${page.pageNo}"/>
<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
<input type="hidden" name="page.orderDir" id="order" value="${page.orderDir}"/>
<div class="pagination">
	${page.totalItems}条记录 , 第${page.pageNo}页, 共${page.totalPages}页
	<s:if test="page.pageNo==1">
		<span class="disabled">首页</span>
		<!-- <span class="disabled prev_page">« 上一页</span> -->
	</s:if>
	<s:else>
		<a href="javascript:jumpPage(1)">首页</a>
		<!-- <a href="javascript:jumpPage(${page.pageNo-1})" class="prev_page">« 上一页</a> -->
	</s:else>			
	<s:iterator value="sliders" id="num">	
		<s:if test="page.pageNo==#num">
			<span class="current">${num}</span>
		</s:if><s:else>		
			<a href="javascript:jumpPage(${num})"><s:property/></a>
		</s:else>
	</s:iterator>
	<s:if test="page.pageNo==page.totalPages">
		<span class="disabled">末页</span>
	</s:if>
	<s:else>
		<a href="javascript:jumpPage(${page.totalPages})">末页</a>
	</s:else>				
</div>