<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>客户端列表</title>
<script>
			$(document).ready(function(){		
				$("#message").fadeOut(3000);	
				$("#client-tab").addClass("active");
                $("#client-tab a").append("<i class='icon-remove-circle'></i>");
			});
			function deleteThis(id){
				if(confirm("确定要删除吗?")){
					window.location="funlocker-client!delete.action?id="+id;
				}
			}
		</script>
</head>
<body>

	<h1>客户端列表</h1>
	<c:if test="${not empty actionMessages}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>
			${actionMessages}
		</div>
	</c:if>
	<div class="pull-right">
		<a class="icon-plus" href="file-upload!client.action">新增</a>
	</div>


	<table class="table table-hover">
		<thead>
			<tr>
				<th>文件名</th>
				<th>大小</th>
				<th>版本号</th>
				<th>上传时间</th>
				<th>更新时间</th>
				<th>类型</th>
				<th>编辑</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="clientFiles">
				<tr>
					<td>${name}</td>
					<td>${size}</td>
					<td>${version}</td>
					<td>${createTime}</td>
					<td>${modifyTime}</td>
					<td>${dtype}</td>
					<td><a href="funlocker-client!input.action?id=${id}"><i class="icon-edit"></i></a>&nbsp;
						<a href="#" onclick="deleteThis(${id})"><i class="icon-trash"></i></a></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</body>
</html>