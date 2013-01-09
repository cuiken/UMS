<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
	<head>
		<title>一级分类</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link href="${ctx}/static/bootstrap/2.1.1/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
		<link href="${ctx}/static/bootstrap/2.1.1/css/bootstrap-responsive.min.css" type="text/css" rel="stylesheet" />
		<link href="${ctx}/css/mini-web.css" type="text/css" rel="stylesheet" />
		<style>
			.icon{
				height: 30px;
				width: 30px;
			}
		</style>
	</head>
	<body>
		<form action="board.action" id="mainForm" method="get">
			<%@include file="nav-header.jsp" %>
			<div class="container" style="margin-top: 60px;">
				<table class="table table-striped table-bordered table-condensed">
					<thead><tr><th>缩略图</th><th>配色</th><th>名称</th><th>标识符</th><th>描述</th><th>推荐链接</th><th>操作</th><tr></thead>
					<tbody>
						<c:forEach items="${boards}" var="board">
							<tr>
								<td><img class="icon" alt="${board.name}" src="${ctx}/image.action?path=${board.icons[0].value}"></td>
								<td style="background-color: ${board.color};">${board.color}</td>
								<td>${board.name}</td>
								<td>${board.value}</td>								
								<td>${board.description}</td>
								<td>${board.hotLink[0].name}</td>
								<td>
									<a href="hot-link!input.action?id=${board.hotLink[0].id}&bid=${board.id}">推荐</a>
									<a href="board!input.action?id=${board.id}">修改</a>
									<a href="#" onclick="deleteThis(${board.id})">删除</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<a class="btn" href="board!input.action">创建板块</a>
			</div>
		</form>
		<script src="${ctx}/static/jquery/1.7.2/jquery.min.js"></script>
		<script src="${ctx}/static/bootstrap/2.1.1/js/bootstrap.min.js" type="text/javascript"></script>
		<script>
		$(document).ready(function(){
			
			$("#one").addClass("active");
		});
		function deleteThis(id){
			if(confirm("确定要删除吗?")){
				window.location="board!delete.action?id="+id;
			}
		}
		</script>
	</body>
</html>