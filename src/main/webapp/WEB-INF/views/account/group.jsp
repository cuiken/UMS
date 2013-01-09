<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>帐号管理</title>
<script>
		$(document).ready(function() {
			
			$("#group-tab").addClass("active");
		});
		function deleteThis(id){
			if(confirm("确定要删除吗?")){
				window.location="group!delete.action?id="+id;
			}
		}
	</script>
</head>

<body>
	<h1>角色列表</h1>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>
			${message}
		</div>
	</c:if>

	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>授权</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${groups}" var="group">
				<tr>
					<td>${group.name}</td>
					<td><c:choose>
							<c:when test="${fn:length(group.permissionNames)>30}">
							${fn:substring(group.permissionNames,0,30)}....
						</c:when>
							<c:otherwise>
							${group.permissionNames}
						</c:otherwise>
						</c:choose></td>
					<td><shiro:hasPermission name="group:edit">
							<a href="group!input.action?id=${group.id}">修改</a>
							<a href="#" onclick="deleteThis(${group.id})">删除</a>
						</shiro:hasPermission></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<shiro:hasPermission name="group:edit">
		<a class="btn" href="group!input.action">创建角色</a>
	</shiro:hasPermission>

</body>
</html>
