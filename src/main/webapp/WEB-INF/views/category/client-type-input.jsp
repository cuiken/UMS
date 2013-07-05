<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>客户端分类维护</title>
		<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" rel="stylesheet">
		<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js"></script>
		<script src="${ctx}/static/jquery-validation/1.10.0/messages_bs_zh.js"></script>
		<script>
			$(document).ready(function(){
				$("#name").focus();
				$("#ccategory-tab").addClass("active");
                $("#ccategory-tab a").append("<i class='icon-remove-circle'></i>");
				$("#inputForm").validate();
			});
		</script>
	</head>
	<body>
		<h1>客户端分类管理</h1>
		<form id="inputForm" action="client-type!save.action" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${id}">
			<fieldset>
				<legend><small>分类编辑</small></legend>
				<div id="messageBox" class="alert alert-error" style="display:none">输入有误，请先更正。</div>
				<div class="control-group">
					<label for="name" class="control-label">分类名称:</label>
					<div class="controls">
						<input type="text" id="name" name="name" maxlength="20" value="${name}" class="required"/>
					</div>
				</div>			
				<div class="control-group">
					<label for="value" class="control-label">分类键值:</label>
					<div class="controls">
						<input type="text" id="value" name="value" maxlength="20" value="${value}" class="required" />
						<span class="help-inline">注意:此处值需和客户端ct值一一对应!!!</span>
					</div>		
				</div>			
			</fieldset>
			
			<div class="form-actions">
				<input id="btn-submit" class="btn btn-primary" type="submit" value="保存">&nbsp;
				<input id="btn-cancel" class="btn" type="button" value="返回" onclick="history.back();">
			</div>

		</form>
		
	</body>
</html>