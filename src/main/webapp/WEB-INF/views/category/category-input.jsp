<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>分类维护</title>
		<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" rel="stylesheet">
		<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js"></script>
		<script src="${ctx}/static/jquery-validation/1.10.0/messages_bs_zh.js"></script>
		<script>
			$(document).ready(function(){
				$("#name").focus();
				$("#category-tab").addClass("active");
                $("#category-tab a").append("<i class='icon-remove-circle'></i>");
				$("#inputForm").validate({			
					rules:{
						name:{
							remote: "category!checkCategoryName.action?oldCategoryName=" + encodeURIComponent('${name}')
						}
					},
					messages:{
						name:{
							remote:"名称已存在"
						}
					},	
					errorContainer:"#messageBox"			
					
				});
			});
		</script>
	</head>
	<body>
		<h1>分类管理</h1>
		<form id="inputForm" action="category!save.action" method="post" class="form-horizontal" enctype="multipart/form-data">
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
					<label for="description" class="control-label">分类描述:</label>
					<div class="controls">
						<input type="text" id="description" name="description" maxlength="50" value="${description}" />
					</div>		
				</div>	
				<div class="control-group">
					<label for="value" class="control-label">排序:</label>
					<div class="controls">
						<input type="number" id="value" name="value" maxlength="50" value="${value}" />
					</div>		
				</div>	
				<div class="control-group">
					<label for="icon" class="control-label">图标:</label>
					<div class="controls">
						<input type="file" id="icon" name="iicon" />
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