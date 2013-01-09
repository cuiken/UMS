<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
	<head>
		<title>Read Me</title>
		<link href="${ctx}/static/bootstrap/2.1.1/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
		<link href="${ctx}/static/bootstrap/2.1.1/css/bootstrap-responsive.min.css" type="text/css" rel="stylesheet" />
		<link href="${ctx}/css/mini-web.css" type="text/css" rel="stylesheet" />

		<script src="${ctx}/static/jquery/1.7.2/jquery.min.js"></script>
		<script>
		$(function(){
			$("#readme").addClass("active");
		});
		</script>
	</head>
	<body>
		<form action="read.action" id="mainForm" method="post" class="form-horizontal">
			<%@include file="nav-header.jsp" %>
			<div class="container" style="margin-top: 60px;">
				read me!!
			</div>
		</form>
		<script src="${ctx}/static/bootstrap/2.1.1/js/bootstrap.min.js" type="text/javascript"></script>
	</body>
</html>