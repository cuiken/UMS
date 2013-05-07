<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>帐号管理</title>
<script>
	$(document).ready(function() {
		
		$("#account-tab").addClass("active");
	});
</script>
</head>

<body>
	<form action="user.action" method="get">
		<h1>用户列表</h1>
		<c:if test="${not empty actionMessages}">
			<div id="message" class="alert alert-success">
				<button data-dismiss="alert" class="close">×</button>
				${actionMessages}
			</div>
		</c:if>

		<table id="contentTable" class="table table-bordered table-hover">
			<thead>
				<tr>
					<th>登录名</th>
					<th>用户名</th>
					<th>邮箱</th>
					<th>权限组</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${users}" var="user">
					<tr>
						<td>${user.loginName}</td>
						<td>${user.name}</td>
						<td>${user.email}</td>
						<td>${user.groupNames}</td>
						<td>${user.status}</td>
						<td><shiro:hasPermission name="user:edit">
								<a href="user!input.action?id=${user.id}">修改</a>
							</shiro:hasPermission></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<shiro:hasPermission name="user:edit">
			<a class="btn" href="user!input.action">创建用户</a>
		</shiro:hasPermission>

	</form>
</body>
</html>
