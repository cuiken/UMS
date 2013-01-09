<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>内容统计</title>
		<link href="${ctx}/css/home.css" type="text/css" rel="stylesheet">
		<script src="${ctx}/js/table.js"></script>
		<script>

			$(function(){
				$("#reportcn-tab").addClass("active");
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
				&nbsp;日期<input class="input-medium" type="date" id="sdate" autocomplete="on" name="filter_EQS_logDate" value="${param['filter_EQS_logDate']}" />
				<input class="btn" type="button" value="搜索" onclick="search();"/>&nbsp;
				<div style="float:right"><a href="#" class="btn" onclick="exportExcel();"><i class=" icon-download-alt"></i>Excel</a></div>
			</div>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
				<tr>
					<th><a href="javascript:sort('createTime','desc')">日期</a></th>
					<th><a href="javascript:sort('themeName','desc')">内容</a></th>
					<th><a href="javascript:sort('totalVisit','desc')">访问总量</a></th>
					<th><a href="javascript:sort('visitByAd','desc')">广告访问量</a></th>
					<th><a href="javascript:sort('visitByStore','desc')">商店访问量</a></th>
					<th><a href="javascript:sort('totalDown','desc')">下载总量</a></th>
					<th>市场下载量</th>
					<th><a href="javascript:sort('downByStore','desc')">自有下载量</a></th>
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
						<td>
							<div>
								<s:iterator value="downByPerMarket">
									
									<li>${marketName} &nbsp; ${totalDown}</li>								
									
								</s:iterator>
							</div>
						</td>
						<td>${downByStore}</td>
					</tr>
				</s:iterator>
				</tbody>
			</table>		
							
			<%@include file="/common/page.jsp" %>

		</form>
		
	</body>
</html>