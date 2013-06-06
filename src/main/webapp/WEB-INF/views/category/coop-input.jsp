<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>厂商维护</title>
    <link href="${ctx}/static/jquery-validation/1.10.0/validate.css" rel="stylesheet">
    <script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js"></script>
    <script src="${ctx}/static/jquery-validation/1.10.0/messages_bs_zh.js"></script>
    <script>
        $(document).ready(function(){
            $("#name").focus();
            $("#topic-tab").addClass("active");
            $("#topic-tab a").append("<i class='icon-remove-circle'></i>");
            $("#inputForm").validate({
                rules:{
                value:{
                remote: "coop!checkName.action?oldValue=" + encodeURIComponent('${value}')
                }
                },
                messages:{
                value:{
                remote:"厂商已存在"
                }
                },
                errorContainer:"#messageBox"

            });
        });
    </script>
</head>
<body>
<h1>厂商管理</h1>
<form id="inputForm" action="coop!save.action" method="post" class="form-horizontal">
    <input type="hidden" name="id" value="${id}">
    <fieldset>
        <legend><small>厂商编辑</small></legend>
        <div id="messageBox" class="alert alert-error" style="display:none">输入有误，请先更正。</div>
        <div class="control-group">
            <label for="name" class="control-label">厂商名称:</label>
            <div class="controls">
                <input type="text" id="name" name="name" maxlength="50" value="${name}" class="required"/>
            </div>
        </div>
        <div class="control-group">
            <label for="value" class="control-label">厂商标识:</label>
            <div class="controls">
                <input type="text" id="value" name="value" maxlength="20" value="${value}" class="required"/>
            </div>
        </div>
        <div class="control-group">
            <label for="channel" class="control-label">渠道号:</label>
            <div class="controls">
                <input type="text" id="channel" name="channel" maxlength="50" value="${channel}" class="required"/>
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