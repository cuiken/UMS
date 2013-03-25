b<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>客户端安装日报</title>
		<script src="${ctx}/js/table.js"></script>
		<script>
			$(function(){
				$("#report-client-install-tab").addClass("active");
                $("#sdate").datepicker();
			})
		</script>
	</head>
	<body>
		<form id="mainForm" action="report-client-install.action" method="post" class="form-horizontal">
			<h1>客户端安装日报</h1>
			<div id="filter" style="margin-bottom: 5px;">
				市场名称: <input class="input-medium" type="text" id="theme" name="filter_LIKES_marketName" value="${param['filter_LIKES_marketName']}" />
				&nbsp;日期: <input class="input-medium" type="text" data-date-format="yyyy-mm-dd" id="sdate" autocomplete="on" name="filter_EQS_date" value="${param['filter_EQS_date']}" />
				<input class="btn" type="button" value="搜索" onclick="search();"/>&nbsp;
			</div>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
				<tr>
					<th><a href="javascript:sort('date','desc')">日期</a></th>
					<th><a href="javascript:sort('marketName','desc')">市场名称</a></th>
					<th><a href="javascript:sort('installed','desc')">安装量</a></th>
                    <th><a href="javascript:sort('distinctInstalled','desc')">安装量(-)</a></th>
				</tr>
				</thead>
				<tbody>
				<s:iterator value="page.result">
					<tr>
						<td>${date}</td>
						<td>${marketName}</td>
						<td>${installed}</td>
                        <td>${distinctInstalled}</td>
					</tr>
				</s:iterator>
				</tbody>
			</table>		
							
			<%@include file="/common/page.jsp" %>
		
		</form>
		
	</body>
</html>