<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>客户端文件上传</title>
<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" rel="stylesheet">
<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js"></script>
<script src="${ctx}/static/jquery-validation/1.10.0/messages_bs_zh.js"></script>
<script>
	$(document).ready(function() {
		$("#inputForm").validate();
		$("#client-tab").addClass("active");
        $("#client-tab a").append("<i class='icon-remove-circle'></i>");
		$("#message").fadeOut(3000);

	});
</script>
</head>
<body>
	<h1>文件管理</h1>
	<form id="inputForm" action="file-upload!uploadClient.action"
		method="post" enctype="multipart/form-data" class="form-horizontal">

		<c:if test="${not empty actionMessages}">
			<div id="message" class="success">${actionMessages}</div>
		</c:if>

		<fieldset>
			<legend><small>客户端上传</small></legend>
			<div class="control-group">
				<label for="file" class="control-label">客户端文件:<font class="red">*</font></label>
				<div class="controls">
					<input type="file" id="file" name="upload" class="input-file" required="required"/>
				</div>
			</div>

		</fieldset>
		<div class="form-actions">
			<input id="submit" type="submit" class="btn btn-primary" value="上传">&nbsp;
			<input id="cancel" class="btn" type="button" value="返回"
					onclick="history.back()" />
		</div>


	</form>

</body>
</html>