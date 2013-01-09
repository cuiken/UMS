<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>用户反馈管理</title>
<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" rel="stylesheet">
<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js"></script>
<script src="${ctx}/static/jquery-validation/1.10.0/messages_bs_zh.js"></script>
<script>
	$(document).ready(function() {
		//active tab
		$("#feedback-tab").addClass("active");
					
	});
</script>
</head>

<body>
	<h1>用户反馈管理</h1>
	<form id="inputForm" action="${ctx}/report/feedback!save.action"
		method="post" class="form-horizontal">

		<input type="hidden" name="id" value="${id}" />

		<fieldset>
			<legend>
				<small>反馈处理</small>
			</legend>
			<div id="messageBox" class="alert alert-error" style="display: none">输入有误，请先更正。</div>

			<div class="control-group">
				<label for="contact" class="control-label">联系方式:</label>
				<div class="controls">
					<input type="text" id="contact" value="${contact}" disabled/>
				</div>
			</div>
			
			<div class="control-group">
				<label for="loginName" class="control-label">反馈意见:</label>
				<div class="controls">
					<textarea rows="6" class="input-xxlarge disabled" disabled>${content}</textarea>
				</div>
			</div>
			
			<div class="control-group">
				<label for="email" class="control-label">链接参数:</label>
				<div class="controls">
					<textarea rows="6" class="input-xxlarge disabled" disabled>${params}</textarea>
				</div>
			</div>
			<div class="control-group">
				<label for="status" class="control-label">状态:</label>
				<div class="controls">
					<s:select name="status"
						list="#{'0':'待处理','1':'已处理'}" listKey="key"
						listValue="value"></s:select>
				</div>
			</div>
			<div class="form-actions">
				<input id="submit" class="btn btn-primary" type="submit" value="提交" />&nbsp;
				<input id="cancel" class="btn" type="button" value="返回"
					onclick="history.back()" />
			</div>
		</fieldset>

	</form>
</body>
</html>
