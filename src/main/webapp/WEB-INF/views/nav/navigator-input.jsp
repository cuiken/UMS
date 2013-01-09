<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
	<head>
		<title>链接管理</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link href="${ctx}/static/bootstrap/2.1.1/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
		<link href="${ctx}/static/bootstrap/2.1.1/css/bootstrap-responsive.min.css" type="text/css" rel="stylesheet" />
		<link href="${ctx}/css/mini-web.css" type="text/css" rel="stylesheet" />
		<script src="${ctx}/static/jquery/1.7.2/jquery.min.js"></script>
		<script>
			$(document).ready(function(){
				$("#three").addClass("active");
				$("input[name='checkedBoardIds']").each(function(){
					var checkedIds=${checkedBoardIds};				
					for(var i=0;i<checkedIds.length;i++){
						if(this.value==checkedIds[i]){
							$(this).attr("checked","checked");
						}
					}
				});
				
				$("input[name='checkedTagIds']").each(function(){		
					var checkedIds=${checkedTagIds};
					for(var i=0;i<checkedIds.length;i++){
						if(this.value==checkedIds[i]){
							$(this).attr("checked","checked");
						}
					}
				});
				
			});
		</script>
	</head>
	<body>
		<form action="navigator!save.action" id="inputForm"  method="post" enctype="multipart/form-data" class="form-horizontal">
			<%@include file="nav-header.jsp" %>
			<div class="container" style="margin-top: 60px;">
				<input type="hidden" name="id" value="${id}">
				<input type="hidden" value="${page.pageNo}" name="page.pageNo">
				<fieldset>
					<legend><small>管理链接</small></legend>
					<div class="control-group">
						<label for="name" class="control-label">链接名称:</label>
						<div class="controls">
							<input type="text" name="name" id="name" value="${name}" required="required" size="35">
						</div>
					</div>
					
					<div class="control-group">
						<label for="navAddr" class="control-label">链接地址:</label>
						<div class="controls">
							<input type="url" name="navAddr" id="navAddr" value="${navAddr}" required="required" size="35">
						</div>
					</div>
					
					<div class="control-group">
						<label for="description" class="control-label">描述:</label>
						<div class="controls">
							<input type="text" name="description" id="description" value="${description}" size="100">
						</div>
					</div>
					
					<div class="control-group">
						<label for="icon" class="control-label">链接列表icon:</label>
						<div class="controls">
							<input type="file" class="input-file" id="icon" name="iicon">
							<span class="help-inline">*单张直接添加,无需ZIP</span>
						</div>
					</div>
					
					<div class="control-group">
						<label for="fileInput" class="control-label">链接首页大图:</label>
						<div class="controls">
							<input type="file" class="input-file" id="fileInput" name="upload">
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label">一级分类:</label>
						<div class="controls">
							<c:forEach var="board" items="${allBoards}">
								<label class="checkbox inline">
									<input type="checkbox" name="checkedBoardIds" value="${board.id}">${board.name}
								</label>
							</c:forEach>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">二级分类:</label>
						<div class="controls">
							<c:forEach items="${allTags}" var="tag">
								<label class="checkbox">
									<input type="checkbox" name="checkedTagIds" value="${tag.id}">${tag.name}
								</label>
							</c:forEach>
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