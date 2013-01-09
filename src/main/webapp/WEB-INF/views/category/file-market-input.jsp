<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>文件市场编辑</title>
		<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" rel="stylesheet">
		<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js"></script>
		<script src="${ctx}/static/jquery-validation/1.10.0/messages_bs_zh.js"></script>
		<script>
			$(document).ready(function(){
				$("#market-tab").addClass("active");
				$("#inputForm").validate();
			});
		
		</script>
	</head>
	<body>
	<div class="container">
		<form id="inputForm" action="file-market!save.action" method="post" class="form-horizontal">
		
			<input type="hidden" value="${id}" name="id"/>
			<input type="hidden" value="${themeId}" name="themeId"/>
			<input type="hidden" value="${marketId}" name="marketId"/>
			<fieldset>
				<legend><small>文件市场属性</small></legend>
				<div class="control-group">
					<label for="keyName" class="control-label">字段名:<font class="red">*</font></label>
					<div class="controls">
						<input type="text" name="keyName" value="${keyName}" size="25" class="required">
					</div>
				</div>

				<div class="control-group">
					<label for="keyValue" class="control-label">字段值:<font class="red">*</font></label>
					<div class="controls">
						<input type="text" name="keyValue" value="${keyValue}" size="25" class="required">
					</div>	
				</div>
				

				
			</fieldset>
			
			<div class="form-actions">
				<input class="btn btn-primary" type="submit" value="保存">&nbsp;
				<input class="btn" type="button" value="返回" onclick="history.back();">
			</div>

		</form>
		</div>
	</body>
</html>