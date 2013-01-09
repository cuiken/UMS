<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>文件信息管理</title>
		<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" rel="stylesheet">
		<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js"></script>
		<script src="${ctx}/static/jquery-validation/1.10.0/messages_bs_zh.js"></script>
		<script>
			$(document).ready(function(){
				$("#inputForm").validate();
				$("#lock-tab").addClass("active");
			});
		</script>
	</head>
	
	<body>
		<form id="inputForm" action="file-info!save.action" method="post" class="form-horizontal">	
		<input type="hidden" value="${id}" name="id"/>
		<input type="hidden" value="${themeId}" name="themeId"/>
		<fieldset>
			<legend><small>多语言编辑</small></legend>
			<div class="control-group">
				<label for="language" class="control-label">语言:</label>
				<div class="controls">

					<s:if test="id==null">
						<select name="language">
							<option value="zh">中文</option>
							<option value="en">英文</option>
							<option value="jp">日文</option>
						</select>
					</s:if>
					<s:else>
						<input class="disabled" type="text" value="${language}" disabled>
					</s:else>
				</div>
			</div>
			<div class="control-group">
				<label for="title" class="control-label">文件别名:<font class="red">*</font></label>
				<div class="controls">
					<input type="text" name="title" class="required" value="${title}" maxlength="25">
				</div>	
			</div>
			<div class="control-group">
				<label for="shortDescription" class="control-label">简要描述:<font class="red">*</font></label>
				<div class="controls">
					<input type="text" name="shortDescription" class="required" value="${shortDescription}" maxlength="50">
				</div>	
			</div>
			<div class="control-group">
				<label for="longDescription" class="control-label">详细描述:<font class="red">*</font></label>
				<div class="controls">
					<textarea rows="6"  id="longDescription" name="longDescription" class="required" maxlength="500">${longDescription}</textarea>
				</div>	
			</div>
			<div class="control-group">
				<label for="author" class="control-label">作者:<font class="red">*</font></label>
				<div class="controls">
					<input type="text" id="author" name="author" value="${author}" maxlength="25" class="required" />
				</div>	
			</div>
			<div class="control-group">
				<label for="price" class="control-label">单价:</label>
				<div class="controls">
					<div class="input-prepend input-append">
						<span class="add-on">￥</span>
							<input type="text" id="price" name="price" value="${price}" maxlength="10" class="number" />
						<span class="add-on">.00</span>
					</div>
				</div>	
			</div>
		</fieldset>
		<div class="form-actions">
			<input  class="btn btn-primary" type="submit" value="保存"> &nbsp;
			<input class="btn" type="button" value="返回" onclick="history.back()"/>
		</div>
			
		</form>

	</body>
	
</html>