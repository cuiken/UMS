<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>管理商店</title>
		<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" rel="stylesheet">
		<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js"></script>
		<script src="${ctx}/static/jquery-validation/1.10.0/messages_bs_zh.js"></script>
		<script>
			$(document).ready(function(){
				$("#name").focus();
				$("#store-tab").addClass("active");
                $("#store-tab a").append("<i class='icon-remove-circle'></i>");
				$("#inputForm").validate({
					rules:{
						name:{
							remote: "store!checkStoreName.action?oldStoreName=" + encodeURIComponent('${name}')
						},
						value:{
							remote: "store!checkStoreValue.action?oldValue=" +encodeURIComponent('${value}')
						}
					},
					errorContainer: "#messageBox",
					messages:{
						name:{
							remote:"商店名称已存在"
						},
						value:{
							remote:"商店标识也存在"
						}
					}
				
				});
			});
		</script>
	</head>
	<body>
		<form id="inputForm" action="store!save.action" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${id}">
		<input type="hidden" name="copyId" value="${copyId}">

			<fieldset>
				<legend><small><s:if test="copyId!=null">复制</s:if><s:elseif test="id!=null">编辑</s:elseif>
					<s:else>新增</s:else>商店
				</small></legend>
				<div id="messageBox" class="alert alert-error" style="display:none">输入有误，请先更正。</div>
				<div class="control-group">
					<label for="name" class="control-label">商店名称:</label>
					<div class="controls">
						<input type="text" id="name" name="name" maxlength="20" value="${name}" class="required"/>
					</div>
				</div>
				<div class="control-group">
					<label for="value" class="control-label">商店标识:</label>
					<div class="controls">
						<input type="text" id="value" name="value" maxlength="20" value="${value}" class="required"/>
					</div>
				</div>
				<div class="control-group">
					<label for="description" class="control-label">商店类型:</label>
					<div class="controls">
						<s:radio name="description" list="#{'free':'免费','charge':'收费'}"
						 listKey="key" listValue="value" theme="custom"></s:radio>
					</div>
				</div>

			</fieldset>
			
			<div class="form-actions">
				<input class="btn btn-primary" type="submit" value="保存">&nbsp;
				<input class="btn" type="button" value="返回" onclick="history.back();">
			</div>

		</form>
		
	</body>
</html>