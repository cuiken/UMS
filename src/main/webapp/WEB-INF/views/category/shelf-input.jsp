<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>货架</title>
		<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" rel="stylesheet">
		<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js"></script>
		<script src="${ctx}/static/jquery-validation/1.10.0/messages_bs_zh.js"></script>
		<script>
			$(document).ready(function(){
				$("#shelf-tab").addClass("active");
                $("#shelf-tab a").append("<i class='icon-remove-circle'></i>");
				$("#name").focus();
				$("#inputForm").validate({
					
					rules:{
						checkedStoreId:"required",
						//name:{
					//		remote: "shelf!checkShelfName.action?storeId=" + encodeURIComponent('${store.id}')+"&oldName="+encodeURIComponent('${name}')
					//	}
						
					},
					errorContainer: "#messageBox",
					errorPlacement: function(error, element) {
						if (element.is(":radio") )
							error.appendTo (element.parent());
						else
							error.insertAfter( element );
					}
					
				});
			});
		</script>
	</head>
	<body>
		<form id="inputForm" action="shelf!save.action" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${id}">
		<input type="hidden" name="sid" value="${sid}">
		<fieldset>
			<legend>管理货架</legend>
			<div id="messageBox" class="alert alert-error" style="display:none">输入有误，请先更正。</div>
			<div class="control-group">
				<label for="name" class="control-label">货架名称:</label>
				<div class="controls">
					<input type="text" id="name" name="name" size="25" maxlength="20" value="${name}" class="required"/>
				</div>
			</div>
			
			<div class="control-group">
				<label for="value" class="control-label">货架标识:</label>
				<div class="controls">
					<input type="text" id="value" name="value" value="${value}" size="25" maxlength="50" class="required"/>
				</div>
			</div>
			
			<div class="control-group">
				<label for="description" class="control-label">货架描述:</label>
				<div class="controls">
					<input type="text" id="description" name="description" value="${description}" size="25" maxlength="50" />
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