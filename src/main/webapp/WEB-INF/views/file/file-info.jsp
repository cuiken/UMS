<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>文件多语言信息</title>
		<script>
			$(document).ready(function(){
				$("#message").fadeOut(3000);
				$("#lock-tab").addClass("active");
                $("#lock-tab a").append("<i class='icon-remove-circle'></i>");
			});
			function deleteThis(id,tid){
				if(confirm("确定要删除吗?")){
					window.location="file-info!delete.action?id="+id+"&themeId="+tid;
				}
			}
		</script>
	</head>
	<body>
		<form id="mainForm" action="file.action" method="get">
		<h1>多语言信息</h1>
		<c:if test="${not empty actionMessages}">
			<div id="message" class="alert alert-success">${actionMessages}</div>	
		</c:if>
		
			<table class="table table-striped table-bordered table-condensed">
				<thead>
				<tr>			
					<th>文件别名</th>
					<th>语言</th>
					<th>作者</th>
					<th>单价</th>			
					<th>操作</th>
				</tr>
				</thead>
				<tbody>
				<s:iterator value="allInfo">
					<tr>
						
						<td>${title}</td>
						<td>${language}</td>
						<td>${author}</td>
						<td>${price}</td>
						<td>
							<!-- <a href="file-info!input.action?id=${id}&themeId=${themeId}">修改</a>
						 -->
							<a href="#" onclick="deleteThis(${id},${themeId})">删除</a>
						</td>
					</tr>
				</s:iterator>
				</tbody>
			</table>	
			<div class="form-actions">
				<a class="btn btn-primary" href="file-info!input.action?themeId=${themeId}">新增</a>&nbsp;
				<a class="btn" href="file.action">返回</a>
			</div>
			
		</form>

	</body>
</html>