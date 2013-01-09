<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>文件-市场-修改</title>
		<%@include file="/common/script.jsp" %>
		<script src="${ctx}/js/jquery/jquery-1.7.min.js"></script>
		<link href="${ctx}/js/jquery/validation/milk.css" rel="stylesheet">
		<script src="${ctx}/js/jquery/jquery-1.7.min.js"></script>
		<script src="${ctx}/js/jquery/validation/jquery.validate.min.js"></script>
		<script src="${ctx}/js/jquery/validation/messages_cn.js"></script>
		<script>
			$(document).ready(function(){
				$("textarea").css("height","150px");
				$("#inputForm").validate();
				$("#submitBtn").click(function(){
					$("#inputForm").submit();
				});
				
			})
		</script>
	</head>
	<body>
	<form id="inputForm" action="file-store-info!save.action" method="post">
		<fieldset>
			<legend>多语言描述信息</legend>
			<c:if test="${not empty actionMessages}">
				<div id="message" class="success">${actionMessages}</div>	
			</c:if>
			<s:iterator value="fileInfo" status="info">
				<input type="hidden" name="fileInfo[${info.index}].id" value="${id}"/>
				<input type="hidden" name="fileInfo[${info.index}].theme.id" value="${theme.id}"/>
				<input type="hidden" name="fileInfo[${info.index}].store.id" value="${store.id}"/>
				<input type="hidden" name="fileInfo[${info.index}].language" value="${language}"/>
				<input type="hidden" name="fileInfo[${info.index}].fiId" value="${fiId}"/>
				<div>
					<font color="blue">${language}</font>
				</div>
				<div>
					<label for="title" class="field">文件别名:<font class="red">*</font></label>
					<input type="text" id="title[${info.index}]" name="fileInfo[${info.index}].title" value="${title}" size="25" maxlength="50" class="required"/>
				</div>
				<div>
					<label for="shortDescription" class="field">简要描述:<font class="red">*</font></label>
					<input id="shortDescription[${info.index}]" name="fileInfo[${info.index}].shortDescription" type="text" size="25" maxlength="50" value="${shortDescription}" class="required"/>
				</div>
				<div>
					<label for="longDescription" class="field" style="vertical-align: top">详细描述:<font class="red">*</font></label>
					<textarea  id="longDescription[${info.index}]" name="fileInfo[${info.index}].longDescription" class="required" maxlength="500">${longDescription}</textarea>
				</div>
				<div>
					<label for="author" class="field">作者:<font class="red">*</font></label>
					<input type="text" id="author[${info.index}]" name="fileInfo[${info.index}].author" value="${author}" size="25" maxlength="25" class="required"/>
				</div>
				<div>
					<label for="price" class="field">单价:</label>
					<input type="text" id="price[${info.index}]" name="fileInfo[${info.index}].price" value="${price}" size="25" maxlength="10" class="number" />
				</div>
			</s:iterator>
		</fieldset>
		<div>
			<input type="button" id="submitBtn" value="保存">&nbsp;
			<input class="button" type="button" value="返回" onclick="window.close();">
		</div>
	</form>		
	</body>

</html>	
		