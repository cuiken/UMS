<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>货架</title>
		<%@include file="/common/script.jsp" %>
		<link href="${ctx}/js/jquery/validation/milk.css" rel="stylesheet">
		<script src="${ctx}/js/jquery/jquery-1.7.min.js"></script>
		<script src="${ctx}/js/jquery/validation/jquery.validate.min.js"></script>
		<script src="${ctx}/js/jquery/validation/messages_cn.js"></script>
		<script>
			$(document).ready(function(){
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
		<form id="inputForm" action="shelf!save.action" method="post">
		<div class="container">
		<%@include file="/common/header.jsp" %>
		<%@include file="/common/left.jsp" %>
		<input type="hidden" name="id" value="${id}">
		<div class="span-18 last prepend-top">
			<fieldset>
			<legend>管理货架</legend>
			<div id="messageBox" class="error" style="display:none">输入有误，请先更正。</div>
			<div>
				<label for="name" class="field">货架名称:</label>
				<input type="text" id="name" name="name" size="25" maxlength="20" value="${name}" class="required"/>
			</div>
			
			<div>
				<label for="value" class="field">货架标识:</label>
				<input type="text" id="value" name="value" value="${value}" size="25" maxlength="50" class="required"/>
			</div>
			
			<div>
				<label for="description" class="field">货架描述:</label>
				<input type="text" id="description" name="description" value="${description}" size="25" maxlength="50" />
			</div>
			<div>
				<label for="checkedStoreId" class="field">所属商店:</label>
				<s:radio name="checkedStoreId" list="allStores" listKey="id" listValue="name" theme="simple" />
			</div>
			
			</fieldset>
			<div>
				<input type="submit" value="保存">&nbsp;
				<input type="button" value="返回" onclick="history.back();">
			</div>
			</div>
		</div>
		</form>
		
	</body>
</html>