<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>专题维护</title>
    <link href="${ctx}/static/jquery-validation/1.10.0/validate.css" rel="stylesheet">
    <script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js"></script>
    <script src="${ctx}/static/jquery-validation/1.10.0/messages_bs_zh.js"></script>
    <script>
        $(document).ready(function(){
            $("#name").focus();
            $("#topic-tab").addClass("active");
            $("#topic-tab a").append("<i class='icon-remove-circle'></i>");
            $("#inputForm").validate({
                <%--rules:{--%>
                    <%--name:{--%>
                        <%--remote: "topic!checkName.action?oldName=" + encodeURIComponent('${name}')--%>
                    <%--}--%>
                <%--},--%>
                <%--messages:{--%>
                    <%--name:{--%>
                        <%--remote:"名称已存在"--%>
                    <%--}--%>
                <%--},--%>
                <%--errorContainer:"#messageBox"--%>

            });
        });
    </script>
</head>
<body>
<h1>专题管理</h1>
<form id="inputForm" action="topic!save.action" method="post" class="form-horizontal" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${id}">
    <fieldset>
        <legend><small>专题编辑</small></legend>
        <div id="messageBox" class="alert alert-error" style="display:none">输入有误，请先更正。</div>
        <div class="control-group">
            <label for="sort" class="control-label">序号:</label>
            <div class="controls">
                <input type="number" id="sort" name="sort" maxlength="50" value="${sort}" />
            </div>
        </div>
        <div class="control-group">
            <label for="name" class="control-label">专题名称:</label>
            <div class="controls">
                <input type="text" id="name" name="name" maxlength="20" value="${name}" class="required"/>
            </div>
        </div>
        <div class="control-group">
            <label for="description" class="control-label">专题描述:</label>
            <div class="controls">
                <textarea name="description" id="description" class="input-xlarge" rows="4" maxlength="255">${description}</textarea>
            </div>
        </div>
        <div class="control-group">
            <label for="icon" class="control-label">图标:</label>
            <div class="controls">
                <input type="file" id="icon" name="preview" />
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