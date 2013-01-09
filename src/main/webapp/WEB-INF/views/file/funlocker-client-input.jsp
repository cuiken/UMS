<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>客户端编辑</title>
<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" rel="stylesheet">
<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js"></script>
<script src="${ctx}/static/jquery-validation/1.10.0/messages_bs_zh.js"></script>
<script>
	$(document).ready(function() {
		$("#client-tab").addClass("active");
		$("#inputForm").validate({
			rules : {
				dtype : "required"
			},
			errorPlacement : function(error, element) {
				if (element.is(":radio"))
					error.appendTo(element.parent());
				else
					error.insertAfter(element);
			}
		});

		$("#message").fadeOut(3000);

	});
</script>
</head>
<body>
	<h1>文件管理</h1>
	<form id="inputForm" action="funlocker-client!save.action"
		method="post" class="form-horizontal">


		<c:if test="${not empty actionMessages}">
			<div id="message" class="notice">${actionMessages}</div>
		</c:if>

		<fieldset>
			<legend><small>客户端编辑</small></legend>
			<input type="hidden" name="id" value="${id}">
			<div class="control-group">
				<label for="pkName" class="control-label">文件包名:<font class="red">*</font></label>
				<div class="controls">
					<input type="text" id="pkName" name="pkName" value="${pkName}" />
				</div>
			</div>
			<div class="control-group">
				<label for="dtype" class="control-label">客户端类型:</label>
				<div class="controls">
					<s:radio id="dtype" name="dtype" list="types" listKey="value" listValue="name" theme="custom"></s:radio>
				</div>	
			</div>

		</fieldset>
		<div class="form-actions">
			<input type="submit" class="btn btn-primary" value="保存">&nbsp;
			<input type="button" class="btn" value="返回"
				onclick="javascript:history.back();">
		</div>


	</form>

</body>
</html>