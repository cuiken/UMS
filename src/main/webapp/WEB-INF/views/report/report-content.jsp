<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>内容统计</title>
		<script src="${ctx}/static/zepto/table.js"></script>
		<script>

			$(function(){
				$("#reportcn-tab").addClass("active");
                $("#reportcn-tab a").append("<i class='icon-remove-circle'></i>");
                $("#sdate").datepicker();
			})

			function exportExcel(){
				window.location="report-content!export.action?theme="+encodeURIComponent($("#theme").val())+"&date="+$("#sdate").val();
			}
		</script>
	</head>
	<body>
		<form id="mainForm" action="report-content.action" method="post" class="form-horizontal">
		
			<h1>内容日报</h1>
			<div id="filter" style="margin-bottom: 5px;">
				内容: <input class="input-medium" type="text" id="theme" name="filter_LIKES_themeName" value="${param['filter_LIKES_themeName']}" />
				&nbsp;日期: <input class="input-medium" type="text" data-date-format="yyyy-mm-dd" id="sdate" autocomplete="on" name="filter_EQS_logDate" value="${param['filter_EQS_logDate']}" />
				<input class="btn" type="button" value="搜索" onclick="search();"/>&nbsp;
				<div class="pull-right"><a href="#" class="icon-download-alt" onclick="exportExcel();">导出</a></div>
			</div>
			<table class="table table-hover">
				<thead>
				<tr>
					<th><a href="javascript:sort('createTime','desc')">日期</a></th>
					<th><a href="javascript:sort('themeName','desc')">内容</a></th>
					<th><a href="javascript:sort('totalVisit','desc')">访问总量</a></th>
					<th><a href="javascript:sort('visitByAd','desc')">广告访问量</a></th>
					<th><a href="javascript:sort('visitByStore','desc')">商店访问量</a></th>
					<th><a href="javascript:sort('totalDown','desc')">下载总量</a></th>
				</tr>
				</thead>
				<tbody>
				<s:iterator value="page.result">
					<tr>
						<td>${logDate}</td>
						<td>${themeName}</td>
						<td>${totalVisit}</td>
						<td>${visitByAd}</td>
						<td>${visitByStore}</td>
						<td>${totalDown}</td>
					</tr>
				</s:iterator>
				</tbody>
			</table>		
							
			<%@include file="/WEB-INF/layouts/page.jsp" %>

		</form>
		
	</body>
</html>