<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>客户端统计</title>
		<script src="${ctx}/js/table.js"></script>
		<script>
			$(function(){
				$("#reportc-tab").addClass("active");
                $("#reportc-tab a").append("<i class='icon-remove-circle'></i>");
			})
		</script>
	</head>
	<body>
		<form id="mainForm" action="report-client.action" method="get">
			<h1>客户端日报</h1>
			<table class="table table-bordered table-hover">
				<thead>
				<tr>
					<th>日期</th>
					<th>客户端启用次数</th>
					<th>总用户</th>
					<th>客户端启用用户数</th>
					<th>新增用户</th>
					<th>商店访问次数</th>
					<th>商店访问用户数</th>
				</tr>
				</thead>
				<tbody>
				<s:iterator value="page.result">
					<tr>
						<td>${createTime}</td>
						<td>${openCount}</td>
						<td>${totalUser}</td>
						<td>${openUser}</td>
						<td>${incrementUser}</td>
						<td>${visitStoreCount}</td>
						<td>${visitStoreUser}</td>
					</tr>
				</s:iterator>
				</tbody>
			</table>		
							
			<%@include file="/common/page.jsp" %>
		
		</form>
		
	</body>
</html>