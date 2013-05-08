<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>用户反馈列表</title>
		<script src="${ctx}/js/table.js"></script>
		<script>
			$(function(){
				$("#feedback-tab").addClass("active");
                $("#feedback-tab a").append("<i class='icon-remove-circle'></i>");
				$("#f_status").change(function(){
					search();			
				});
				$("#f_status").val(${param['filter_EQS_status']});
			})
		</script>
	</head>
	<body>
		<form id="mainForm" action="feedback.action" method="get">
			<h1>用户反馈列表</h1>
			<div id="filter">
				<s:select list="#{'0':'待处理','1':'已处理'}" id="f_status" name="filter_EQS_status" listKey="key" listValue="value"></s:select>
			</div>
			<table class="table table-bordered table-hover">
				<thead>
				<tr>
					<th>反馈内容</th>
					<th>联系方式</th>
					<th>状态</th>
					<th>反馈时间</th>
					<th>处理时间</th>
				</tr>
				</thead>
				<tbody>
				<s:iterator value="page.result">
					<tr>
						<td><a href="${ctx}/report/feedback!input.action?id=${id}">
						<c:choose>
							<c:when test="${fn:length(content)>20}">
								${fn:substring(content,0,20)}...
							</c:when>
							<c:otherwise>
								${content}
							</c:otherwise>
						</c:choose>
						
						</a></td>
						<td>
							<c:choose>
								<c:when test="${fn:length(contact)>11}">
									${fn:substring(contact,0,11)}...
								</c:when>
								<c:otherwise>
									${contact}
								</c:otherwise>
							</c:choose>						
						</td>
						<td>
							<s:if test="status==0">
								待处理
							</s:if>
							<s:elseif test="status==1">
								已处理
							</s:elseif>
						<td>
							<fmt:formatDate value="${createTime}" pattern="yyyy/MM/dd HH:mm"/>
						</td>
						<td>
							<fmt:formatDate value="${modifyTime}" pattern="yyyy/MM/dd HH:mm"/>
						</td>
					</tr>
				</s:iterator>
				</tbody>
			</table>		
							
			<%@include file="/common/page.jsp" %>
		
		</form>
		
	</body>
</html>