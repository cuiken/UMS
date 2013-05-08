<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<html>
<head>
	<title>帐号管理</title>
<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" rel="stylesheet">
<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js"></script>
<script src="${ctx}/static/jquery-validation/1.10.0/messages_bs_zh.js"></script>
	<script>
		$(document).ready(function() {
			$("#name").focus();
			$("#group-tab").addClass("active");
            $("#group-tab a").append("<i class='icon-remove-circle'></i>");
			$("#inputForm").validate();
		});
	</script>
</head>

<body>
	<h1>系统管理</h1>
	<form id="inputForm"  action="${ctx}/account/group!save.action" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${id}"/>
		<fieldset>
			<legend><small>角色维护</small></legend>
			<div class="control-group">
				<label for="name" class="control-label">名称:</label>
				<div class="controls">
					<input type="text" id="name" name="name" size="50" class="required" value="${name}"/>
				</div>
			</div>
			<div class="control-group">
				<label for="groupList" class="control-label">权限列表:</label>
				<div class="controls">
					<s:checkboxlist id="permissionList" name="permissionList" list="allPermissions" listKey="value" listValue="displayName" theme="custom"></s:checkboxlist>
				</div>
			</div>	
			<div class="form-actions">
					<input id="submit" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
					<input id="cancel" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>

	</form>
</body>
</html>
