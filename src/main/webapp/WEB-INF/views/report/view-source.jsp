<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>日志查看</title>
<script>
	$(document).ready(function() {

		$("#viewsource-tab").addClass("active");
	});
</script>
</head>
<body>
	<form action="view-source!view.action" method="get">
		<h1>日志查看</h1>
		<p>
			params:/**download,auto**/ <br /> userDir: ${userDir}
		</p>
		<div class="control-group">
				<label for="path" class="control-label">abs-path:</label>
				<div class="controls">
					<input type="text" id="path" name="path" size="100" required/>
				</div>
		</div>

		<div class="form-actions">
			<input id="submit" class="btn btn-primary" type="submit" value="提交" />&nbsp;
			
		</div>
	</form>
</body>
</html>