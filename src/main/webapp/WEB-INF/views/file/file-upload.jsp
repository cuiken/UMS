<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>文件上传</title>	
		<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" rel="stylesheet">
		<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js"></script>
		<script src="${ctx}/static/jquery-validation/1.10.0/messages_bs_zh.js"></script>
		<script>
			$(document).ready(function(){
				$("#lock-tab").addClass("active");
				$("#inputForm").validate({
					rules:{
						title:{
							remote:"file!checkTitle.action?oldTitle="+encodeURIComponent('${title}')							
						},
						checkedCategoryIds:"required",
						longDescription:{
							required:true,
							maxlength:500
						}
					},
					messages:{
						title:{
							remote:"文件已存在"
						}						
					},
					errorPlacement: function(error, element) {
						if (element.is(":radio") )
							error.appendTo (element.parent());
						else
							error.insertAfter( element );
					}				
				});
				/**
				$("#upload").bind('change', function() {
								
			  					return this.files[0].size<1;
		
						});
				*/
				$("#message").fadeOut(3000);
				$("#golist").click(function(){
					window.location="file.action";
				});
				
			});

		</script>
	</head>
	<body>
		<h1>文件管理</h1>
		<form id="inputForm" action="file-upload!upload.action" method="post" enctype="multipart/form-data" class="form-horizontal">

			<c:if test="${not empty actionMessages}">
				<div id="message" class="notice">${actionMessages}</div>	
			</c:if>
			<fieldset>
				<legend><small>解锁上传</small></legend>
				<div class="control-group">
					<label for="upload" class="control-label">上传文件:<font class="red">*</font></label>
					<div class="controls">
						<input type="file" id="upload" name="upload" class="input-file" required="required"/>
						<span class="help-inline">*仅支持zip文件,最大28M*</span>
					</div>
				</div>		
				<div class="control-group">
					<label for="title" class="control-label">标题:<font class="red">*</font></label>
					<div class="controls">
						<input type="text" id="title" name="title" maxlength="30" value="${title}" class="required">
					</div>	
				</div>
				
				<div class="control-group">
					<label for="checkedCategoryIds" class="control-label">分类:<font class="red">*</font></label>
					<div class="controls">
						<s:radio name="checkedCategoryIds" list="allCategoryList" listKey="id" listValue="name" theme="custom"></s:radio>
					</div>
				</div>
				
				<div class="control-group">
					<label for="checkedStoreIds" class="control-label">文件类型:</label>
					<div class="controls">
						<s:checkboxlist name="checkedStoreIds" id="checkedStoreIds" list="allStore" listKey="id" listValue="name" theme="custom"></s:checkboxlist>
					</div>
				</div>		
				<div class="control-group">
					<label for="marketURL" class="control-label">apk包名:<font class="red">*</font></label>
					<div class="controls">
						<input type="text" id="marketURL" name="marketURL" value="${marketURL}" maxlength="100" class="required">
					</div>	
				</div>
				
				<div class="control-group">
					<label for="version" class="control-label">文件版本:<font class="red">*</font></label>
					<div class="controls">
						<input type="text" id="version" name="version" value="${version}"  maxlength="10" class="required">
					</div>	
				</div>
				
				<div class="control-group">
					<label for="shortDescription" class="control-label">简要描述:<font class="red">*</font></label>
					<div class="controls">
						<input type="text" id="shortDescription" name="shortDescription" value="${shortDescription}" maxlength="50" class="required">
					</div>					
				</div>
				<div class="control-group">
					<label for="longDescription" class="control-label" style="vertical-align: top">详细描述:<font class="red">*</font></label>
					<div class="controls">
						<textarea rows="6" id="longDescription" name="longDescription"></textarea>
					</div>					
				</div>
				<div class="control-group">
					<label for="author" class="control-label">作者:<font class="red">*</font></label>
					<div class="controls">
						<input type="text" id="author" name="author" value="${author}" maxlength="25" class="required"/>
					</div>	
				</div>
				<div class="control-group">
					<label for="price" class="control-label">单价:</label>
					<div class="controls">
						<input type="text" id="price" name="price" value="${price}" maxlength="4" class="number" />						
					</div>	
				</div>
				<div class="control-group">
					<label for="availMachine" class="control-label">可用机型:</label>
					<div class="controls">
						<input type="text" id="availMachine" name="availMachine" value="${availMachine}" maxlength="255"/>
					</div>	
				</div>
				<div class="control-group">
					<label for="unavailMachine" class="control-label">不可用机型:</label>
					<div class="controls">
						<input type="text" id="unavailMachine" name="unavailMachine" value="${unavailMachine}" maxlength="255"/>
					</div>	
				</div>
			</fieldset>
			<div class="form-actions">
				<input type="submit" class="btn btn-primary" value="上传">&nbsp;
				<input type="button" class="btn" id="golist" value="取消"/>
			</div>	

		</form>

	</body>
</html>