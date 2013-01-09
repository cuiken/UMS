<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
	<head>
		<title>Hot Link</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link href="${ctx}/static/bootstrap/2.1.1/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
		<link href="${ctx}/static/bootstrap/2.1.1/css/bootstrap-responsive.min.css" type="text/css" rel="stylesheet" />
		<link href="${ctx}/css/mini-web.css" type="text/css" rel="stylesheet" />
		<script src="${ctx}/static/jquery/1.7.2/jquery.min.js"></script>
		<script>
			$(document).ready(function(){
				if($("#bid").val()!=''){
					$("#one").addClass("active");
				}
				if($("#tid").val()!=''){
					$("#two").addClass("active");
				}
			});
		</script>
	</head>
	<body>
		<form action="hot-link!save.action" id="inputForm" method="post" class="form-horizontal">
			<%@include file="nav-header.jsp" %>
			<div class="container" style="margin-top: 60px;">
				<input type="hidden" name="id" value="${id}">
				<input type="hidden" name="bid" value="${bid}" id="bid">
				<input type="hidden" name="tid" value="${tid}" id="tid">
				<fieldset>
					<legend><small>Hot Link</small></legend>
					<div class="control-group">
						<label for="name" class="control-label">名称:</label>
						<div class="controls">
							<input type="text" name="name" id="name" value="${name}" required="required" size="35">
						</div>
					</div>
					<div class="control-group">
						<label for="addr" class="control-label">链接地址:</label>
						<div class="controls">
							<input type="url" name="addr" id="addr" value="${addr}" required="required" size="100">
						</div>
					</div>
					<div class="control-group">
						<label for="descr" class="control-label">描述:</label>
						<div class="controls">
							<input type="text" name="descr" id="descr" value="${descr}" size="100">
						</div>
					</div>
					
					<div class="form-actions">
						<input id="submit" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
						<input id="cancel" class="btn" type="button" value="返回" onclick="history.back()"/>
					</div>
				</fieldset>
			</div>
		</form>
	
		<script src="${ctx}/static/bootstrap/2.1.1/js/bootstrap.min.js" type="text/javascript"></script>
	</body>
</html>