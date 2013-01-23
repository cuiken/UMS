<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>文件列表</title>
<link href="${ctx}/css/home.css" type="text/css" rel="stylesheet">
<script src="${ctx}/js/table.js"></script>
<script>
			$(document).ready(function(){
				$("#message").fadeOut(3000);
				$("#lock-tab").addClass("active");
				$("#f_dtype").change(function(){
					search();			
				});
				$("#f_dtype").val(${param['filter_EQS_dtype']});
			});
			function deleteThis(id){
				if(confirm("确定要删除吗?")){
					window.location="file!delete.action?id="+id;
				}
			}
			
			function doupload(){
				var t=$("#f_dtype").val();
				window.location="file-upload.action?dtype="+t;
			}
		</script>
</head>
<body>
	<h1>文件列表</h1>
	<form id="mainForm" action="file.action" method="post" class="form-horizontal">


		<c:if test="${not empty actionMessages}">
			<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${actionMessages}</div>
		</c:if>
		<div id="filter" style="margin-bottom:5px;">
				文件类型:<s:select list="#{'0':'标准解锁','1':'模板解锁'}" id="f_dtype" name="filter_EQS_dtype" listKey="key" listValue="value" cssClass="span2"></s:select>
				&nbsp;标题: <input type="text" class="input-medium" name="filter_LIKES_title"
				value="${param['filter_LIKES_title']}" size="20" /> <input
				type="button" id="submit_btn" class="btn" value="搜索"
				onclick="search();" />
			<div class="pull-right">
				<a class="btn" href="#" onclick="doupload();">
				<i class="icon-upload"></i>
				上传</a>
			</div>		
		</div>
		<table class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th><a href="javascript:sort('title','asc')">标题</a></th>
					<th>文件名</th>
					<th>版本号</th>
					<th><a href="javascript:sort('createTime','asc')">添加时间</a></th>
					<th><a href="javascript:sort('modifyTime','asc')">更新时间</a></th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="page.result">
					<tr>
						<td><a href="file!input.action?id=${id}&page.pageNo=${page.pageNo}">${title}</a></td>
						<td>${name}</td>
						<td>${version}</td>
						<td>${createTime}</td>
						<td>${modifyTime}</td>
						<td><shiro:hasPermission name="file:edit">
								<!-- <a href="file-info.action?themeId=${id}">语言</a> -->
								<a href="#" onclick="deleteThis(${id})">删除</a>
							</shiro:hasPermission></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<%@include file="/common/page.jsp"%>

	</form>
</body>
</html>