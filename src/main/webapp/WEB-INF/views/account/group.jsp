<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>帐号管理</title>
<script>
		$(document).ready(function() {
			
			$("#group-tab").addClass("active");
            $("#group-tab a").append("<i class='icon-remove-circle'></i>");
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
	<c:if test="${not empty actionMessages}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>
			${actionMessages}
		</div>
	</c:if>
    <div class="pull-right">
        <shiro:hasPermission name="group:edit">
            <a class="icon-plus" href="group!input.action">新增</a>
        </shiro:hasPermission>
    </div>
	<table class="table table-bordered table-hover">
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
							<a href="group!input.action?id=${group.id}"><i class="icon-edit"></i></a> &nbsp;
							<a href="#" onclick="deleteThis(${group.id})"><i class="icon-trash"></i></a>
						</shiro:hasPermission></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
