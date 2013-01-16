<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Polling Service</title>
<link href="${ctx}/css/home.css" type="text/css" rel="stylesheet">
<script src="${ctx}/js/table.js"></script>
<script>
			$(document).ready(function(){
				$("#message").fadeOut(3000);
				$("#poll-tab").addClass("active");
				$("#f_store").change(function(){
					search();			
				});
				$("#addpoll").click(function(){
					var params= '${param['filter_EQS_store']}';
					if(params==''){
						location.href="../poll/polling!input.action?store="+$('#f_store').val();
					}else{
						location.href="../poll/polling!input.action?store=${param['filter_EQS_store']}";
					}
				});
				$("#f_store").val('${param['filter_EQS_store']}');
			});
			
			function deleteThis(id){
				if(confirm("确定要删除吗?")){
					window.location="../poll/polling!delete.action?id="+id+"&store=${param['filter_EQS_store']}";
				}
			}
			function changeStatus(id){
				window.location="../poll/polling!change.action?id="+id+"&store=${param['filter_EQS_store']}";
			}
			function goXml(){
				var sid=$("#f_store").val();
				window.location="../poll/xml/"+sid;
			}
		</script>
</head>
<body>
	<h1>广播列表</h1>
	<form id="mainForm" action="polling.action" method="get" class="form-horizontal">


		<c:if test="${not empty actionMessages}">
			<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${actionMessages}</div>
		</c:if>
		<div id="filter" style="margin-bottom:5px;">
				商店: <s:select list="stores" id="f_store" name="filter_EQS_store" listKey="value" listValue="name" cssClass="span2"></s:select>
			<div class="pull-right"><a href="#" onclick="goXml()">查看XML</a></div>
		</div>
		<table class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>广告图片</th>
					<th>广播名称</th>
					<th>广播类型</th>
					<th>状态</th>
					<th>显示时段</th>
					<th>显示次数</th>
					<th><a href="javascript:sort('createTime','asc')">更新时间</a></th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="page.result">
					<tr>
						<td><img alt="广告图片" src="${ctx}/image.action?path=${previews[0].addr}" style="height: 20px;width: 70px;"></td>
						<td><a href="polling!input.action?id=${id}&pageNo=${page.pageNo}">${name}</a></td>
						<td>
							<s:if test="serType==0">
								上线通知
							</s:if>
							<s:elseif test="serType==1">
								广告
							</s:elseif>
						</td>
						<td>
							<s:if test="status==0">
								未发布
							</s:if><s:elseif test="status==1">
								已发布
							</s:elseif>	
						</td>
						<td>${showBegin}-${showEnd}</td>
						<td>${showTotal}</td>
						<td>${createTime}</td>
						<td><shiro:hasPermission name="store:edit">
							<s:if test="status==0">
								<a href="#" onclick="changeStatus(${id})">发布</a>
							</s:if><s:elseif test="status==1">
								<a href="#" onclick="changeStatus(${id})">取消发布</a>
							</s:elseif>	
								<a href="#" onclick="deleteThis(${id})">删除</a>
							</shiro:hasPermission></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<%@include file="/common/page.jsp" %>
		<shiro:hasPermission name="store:edit">
			<a class="btn" id="addpoll" href="#">添加广播</a>
		</shiro:hasPermission>
	</form>
</body>
</html>