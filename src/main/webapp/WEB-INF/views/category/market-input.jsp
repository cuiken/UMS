<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>市场编辑</title>
		<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" rel="stylesheet">
		<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js"></script>
		<script src="${ctx}/static/jquery-validation/1.10.0/messages_bs_zh.js"></script>
		<script>
			$(document).ready(function(){
				$("#name").focus();
				$("#market-tab").addClass("active");
                $("#market-tab a").append("<i class='icon-remove-circle'></i>");
				$("#inputForm").validate({
					rules:{
						name:{
							remote: "market!checkMarketName.action?oldName=" + encodeURIComponent('${name}')
						}
					},
					errorContainer: "#messageBox",
					messages:{
						name:{
							remote:"市场已存在"
						}
					}
				
				});
			});
		</script>
	</head>
	<body>
		<form id="inputForm" action="market!save.action" method="post" class="form-horizontal">

		<input type="hidden" name="id" value="${id}">

			<fieldset>
				<legend><small>编辑市场</small></legend>
				<div id="messageBox" class="alert alert-error" style="display:none">输入有误，请先更正。</div>
				<div class="control-group">
					<label for="name" class="control-label">市场名称:</label>
					<div class="controls">
						<input type="text" id="name" name="name" maxlength="20" value="${name}" class="required"/>
					</div>	
				</div>
				<div class="control-group">
					<label for="value" class="control-label">市场标识:</label>
					<div class="controls">
						<input type="text" id="value" name="value" maxlength="20" value="${value}" class="required"/>
					</div>
				</div>
				<div class="control-group">
					<label for="pkName" class="control-label">市场APK包名:</label>
					<div class="controls">
						<input type="text" id="pkName" name="pkName" maxlength="50" value="${pkName}" class="required"/>
					</div>	
				</div>
				<div class="control-group">
					<label for="marketKey" class="control-label">市场market key:</label>
					<div class="controls">
						<input type="text" id="marketKey" name="marketKey"  maxlength="100" value="${marketKey}" />
					</div>
				</div>
				<!--  
				<div>
					<label for="dtype" class="field">下载方式:</label>
					<input type="radio" value="market" name="dntype">market
					<input type="radio" value="http" name="dntype">http
				</div>
				-->
			</fieldset>
			
			<div class="form-actions">
				<input class="btn btn-primary" type="submit" value="保存">&nbsp;
				<input class="btn" type="button" value="返回" onclick="history.back();">
			</div>

		</form>
		
	</body>
</html>