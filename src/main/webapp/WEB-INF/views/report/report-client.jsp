<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>客户端统计</title>
		<link href="${ctx}/css/home.css" type="text/css" rel="stylesheet">
		<script src="${ctx}/js/table.js"></script>
		<script>
			$(function(){
				$("#reportc-tab").addClass("active");
			})
		</script>
	</head>
	<body>
		<form id="mainForm" action="report-client.action" method="get">
			<h1>客户端日报</h1>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
				<tr>
					<th>日期</th>
					<th>启用数</th>
					<th>总用户</th>
					<th>启用用户</th>
					<th>新增用户</th>
					<th>总下载</th>
					<th>通过内容下载</th>
					<th>通过分享下载</th>
					<th>其他下载</th>
					<th>商店访问数</th>
					<th>商店访问用户</th>
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
						<td>${totalDownload}</td>
						<td>${downByContent}</td>
						<td>${downByShare}</td>
						<td>${downByOther}</td>
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