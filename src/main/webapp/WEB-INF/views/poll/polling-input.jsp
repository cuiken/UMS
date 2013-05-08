<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>广播维护</title>
		<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" rel="stylesheet">
        <link href="${ctx}/static/bootstrap/2.1.1/css/slider.css" rel="stylesheet">
		<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js"></script>
		<script src="${ctx}/static/jquery-validation/1.10.0/messages_bs_zh.js"></script>
        <script src="${ctx}/static/bootstrap/2.1.1/js/bootstrap-slider.js"></script>
		<script>
			$(document).ready(function(){
				$("#name").focus();
				$("#poll-tab").addClass("active");
                $("#poll-tab a").append("<i class='icon-remove-circle'></i>");
				$("#inputForm").validate();
				$("#submit-nopublish").click(function(){
					$("#pub-status").val('0');
					$("#inputForm").submit();
				});
				$("#btn-submit").click(function(){
					$("#pub-status").val('1');
					$("#inputForm").submit();
				});

                if($("#showBegin").val()==''){
                    $("#showBegin").val("0");
                }
                if($("#showEnd").val()==''){
                   $("#showEnd").val("0");
                }
                if($("#showTotal").val()==''){
                    $("#showTotal").val("0");
                }
                if($("#showPerday").val()==''){
                    $("#showPerday").val("0");
                }
                $('#showBegin').slider("setValue",$("#showBegin").val());
                $('#showEnd').slider("setValue",$("#showEnd").val());
                $('#showTotal').slider("setValue",$("#showTotal").val());
                $('#showPerday').slider("setValue",$("#showPerday").val());
			});
		</script>
	</head>
	<body>
		<h1>广播管理</h1>
		<form id="inputForm" action="polling!save.action" method="post" class="form-horizontal" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${id}">
			<input type="hidden" name="store" value="${store}">
			<input type="hidden" name="status" value="${status}" id="pub-status">
			<fieldset>
				<legend><small>广播编辑</small></legend>
				<div id="messageBox" class="alert alert-error" style="display:none">输入有误，请先更正。</div>
				<div class="control-group">
					<label for="name" class="control-label">广播类型:</label>
					<div class="controls">
						 <s:select list="#{'0':'上线通知','1':'广告'}" id="p_type" name="serType" listKey="key" listValue="value" cssClass="span2"></s:select>
					</div>
				</div>			
				<div class="control-group">
					<label for="name" class="control-label">广播名称:</label>
					<div class="controls">
						<input type="text" id="name" name="name" maxlength="20" value="${name}" class="required"/>
					</div>
				</div>		
				<div class="control-group">
					<label for="image" class="control-label">广播图片:</label>
					<div class="controls">
						<input class="input-file" type="file" id="image" name="pollImage" />
						<span class="help-inline">*单张时可直接添加,无需ZIP</span>
					</div>		
				</div>				
				<div class="control-group">
					<label for="serDesc" class="control-label">广播文字:</label>
					<div class="controls">
						<textarea rows="6" style="width: 340px;" id="serDesc" name="serDesc">${serDesc}</textarea>
					</div>		
				</div>		
				<div class="control-group">
					<label for="link" class="control-label">广播链接:</label>
					<div class="controls">
						<input type="url" id="link" name="link" maxlength="255" value="${link}" class="required"/>
					</div>		
				</div>		
				<div class="control-group">
					<label for="showBegin" class="control-label">开始时段:</label>
					<div class="controls">
						<input type="text" id="showBegin" name="showBegin" data-slider-step="1" data-slider-min="0" data-slider-max="23" data-slider-value="${showBegin}" value="${showBegin}" />
					</div>		
				</div>	
				<div class="control-group">
					<label for="showEnd" class="control-label">结束时段:</label>
					<div class="controls">
						<input type="text" id="showEnd" name="showEnd" data-slider-step="1" data-slider-min="0" data-slider-max="23" data-slider-value="${showEnd}" value="${showEnd}" />
					</div>		
				</div>		
				<div class="control-group">
					<label for="showTotal" class="control-label">显示总次数:</label>
					<div class="controls">
						<input type="text" id="showTotal" name="showTotal" data-slider-step="1" data-slider-min="0" data-slider-max="20" data-slider-value="${showTotal}" value="${showTotal}" />
					</div>		
				</div>		
				<div class="control-group">
					<label for="showPerday" class="control-label">每天显示次数:</label>
					<div class="controls">
						<input type="text" id="showPerday" name="showPerday" data-slider-step="1" data-slider-min="0" data-slider-max="10" data-slider-value="${showPerday}" value="${showPerday}" />
					</div>		
				</div>				
			</fieldset>
			
			<div class="form-actions">
				<s:if test="id==null">
					<input id="btn-submit" class="btn btn-primary" type="button" value="保存并发布">&nbsp;
					<input id="submit-nopublish" class="btn" type="button" value="保存">&nbsp;		
				</s:if><s:else>
					<input class="btn btn-primary" type="submit" value="保存">&nbsp;
				</s:else>		
				<input id="btn-cancel" class="btn" type="button" value="返回" onclick="history.back();">			
			</div>

		</form>
		
	</body>
</html>