<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>内容引导客户端统计</title>
		<link href="${ctx}/css/home.css" type="text/css" rel="stylesheet">
		<script src="${ctx}/js/table.js"></script>
		<script>
			$(function(){
				$("#report-getClient-tab").addClass("active");
			})
		</script>
	</head>
	<body>
		<form id="mainForm" action="report-get-client.action" method="get">
			<h1>内容引导客户端日报</h1>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
				<tr>
					<th><a href="javascript:sort('createTime','desc')">日期</a></th>
					<th><a href="javascript:sort('appName','desc')">解锁名称</a></th>
					<th><a href="javascript:sort('download','desc')">引导下载量</a></th>
				</tr>
				</thead>
				<tbody>
				<s:iterator value="page.result">
					<tr>
						<td>${createTime}</td>
						<td>${appName}</td>
						<td>${download}</td>
					</tr>
				</s:iterator>
				</tbody>
			</table>		
							
			<%@include file="/common/page.jsp" %>
		
		</form>
		
	</body>
</html>