<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Advertisement Edit</title>
    <link href="${ctx}/static/jquery-validation/1.10.0/validate.css" rel="stylesheet">
    <script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js"></script>
    <script src="${ctx}/static/jquery-validation/1.10.0/messages_bs_zh.js"></script>
    <script>
        $(document).ready(function () {
            $("#sort").focus();
            $("#advertisement-tab").addClass("active");
            $("#inputForm").validate();
        });
    </script>
</head>
<body>
<h1>广告管理</h1>

<form id="inputForm" action="advertisement!save.action" method="post" class="form-horizontal"
      enctype="multipart/form-data">
    <input type="hidden" name="id" value="${id}">
    <input type="hidden" name="store" value="${store}">
    <input type="hidden" name="status" value="${status}" id="pub-status">
    <fieldset>
        <legend>
            <small>广告维护</small>
        </legend>
        <div id="messageBox" class="alert alert-error" style="display:none">输入有误，请先更正。</div>

        <div class="control-group">
            <label for="sort" class="control-label">编号:</label>
            <div class="controls">
                <input type="text" id="sort"  name="sort" value="${sort}" maxlength="5" class="required">
            </div>
        </div>

        <div class="control-group">
            <label for="name" class="control-label">用途:</label>

            <div class="controls">
                <s:select list="#{'client':'客户端','store':'商店'}" id="p_type" name="dtype" listKey="key" listValue="value"
                          cssClass="span2"></s:select>
            </div>
        </div>
        <div class="control-group">
            <label for="image" class="control-label">图片:</label>

            <div class="controls">
                <input class="input-file" type="file" id="image" name="preview"/>
                <span class="help-inline">*单张图片</span>
            </div>
        </div>
        <div class="control-group">
            <label for="name" class="control-label">名称:</label>

            <div class="controls">
                <input type="text" id="name" name="name" maxlength="20" value="${name}" class="required"/>
            </div>
        </div>

        <div class="control-group">
            <label for="link" class="control-label">跳转链接:</label>

            <div class="controls">
                <input type="url" id="link" name="link" maxlength="255" value="${link}" class="required"/>
            </div>
        </div>

    </fieldset>

    <div class="form-actions">
        <input class="btn btn-primary" type="submit" value="保存">&nbsp;

        <input id="btn-cancel" class="btn" type="button" value="返回" onclick="history.back();">
    </div>

</form>

</body>
</html>