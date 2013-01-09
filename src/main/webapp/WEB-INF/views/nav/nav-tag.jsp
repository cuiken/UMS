<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
	<head>
		<title>二级分类</title>
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
		<form action="nav-tag.action" id="mainForm" method="get">
			<%@include file="nav-header.jsp" %>
			<div class="container" style="margin-top: 60px;">
				<div id="filter">
			
				</div>
				<table class="table table-striped table-bordered table-condensed">
					<thead><tr><th>缩略图</th><th>名称</th><th>标识符</th><th>描述</th><th>推荐链接</th><th>操作</th><tr></thead>
					<tbody>
						<c:forEach items="${tags}" var="tag">
							<tr>
								<td><img class="icon" alt="${tag.name}" src="${ctx}/image.action?path=${tag.icons[0].value}"></td>
								<td>${tag.name}</td>
								<td>${tag.value}</td>
								<td>${tag.description}</td>
								<td>${tag.hotLink[0].name}</td>
								<td>
									<a href="hot-link!input.action?id=${tag.hotLink[0].id}&tid=${tag.id}">推荐</a>
									<a href="nav-tag!input.action?id=${tag.id}">修改</a>
									<a href="#" onclick="deleteThis(${tag.id})">删除</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<a class="btn" href="nav-tag!input.action">创建分类</a>
			</div>
		</form>
		<script src="${ctx}/static/jquery/1.7.2/jquery.min.js"></script>
		<script src="${ctx}/static/bootstrap/2.1.1/js/bootstrap.min.js" type="text/javascript"></script>
		<script>
		$(function(){
			$("#two").addClass("active");
		})	
		function deleteThis(id){
			if(confirm("确定要删除吗?")){
				window.location="nav-tag!delete.action?id="+id;
			}
		}
		</script>
	</body>
</html>