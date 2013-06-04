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
            $("#poll2-tab").addClass("active");
            $("#poll2-tab a").append("<i class='icon-remove-circle'></i>");
            $("#inputForm").validate();
            $("#submit-nopublish").click(function(){
                $("#pub-status").val('0');
                $("#inputForm").submit();
            });
            $("#btn-submit").click(function(){
                $("#pub-status").val('1');
                $("#inputForm").submit();
            });

            if($("#percent").val()==''){
                $("#percent").val("0");
            }
            $('#percent').slider("setValue",$("#percent").val());

            var isShow=function(){
                if($("#dtype").val()=='0'){
                    $("#spec").hide();
                }else{
                    $("#spec").show();
                }
            }
            isShow();
            $("#dtype").change(function(){
                isShow();
            });
            if($("#pid").val()!=""){
                $("#dtype").attr("disabled","disabled");
            }
        });
    </script>
</head>
<body>
<h1>广播管理</h1>
<form id="inputForm" action="poll2!save.action" method="post" class="form-horizontal" enctype="multipart/form-data">
    <input type="hidden" id="pid" name="id" value="${id}">
    <input type="hidden" name="store" value="${store}">
    <input type="hidden" name="dtype" id="dtype" value="${dtype}">
    <input type="hidden" name="status" value="${status}" id="pub-status">
    <fieldset>
        <legend><small>广播编辑</small></legend>
        <div id="messageBox" class="alert alert-error" style="display:none">输入有误，请先更正。</div>
        <div class="control-group">
            <label for="dtype" class="control-label">类型:</label>
            <div class="controls">
                <s:if test="dtype==1">
                    <input type="text" value="抽屉广告" disabled="disabled">
                </s:if><s:else>
                    <input type="text" value="上线通知" disabled="disabled">
                </s:else>
            </div>
        </div>
        <div class="control-group">
            <label for="name" class="control-label">广播名称:</label>
            <div class="controls">
                <input type="text" id="name" name="contentName" maxlength="50" value="${contentName}" class="required"/>
            </div>
        </div>
        <div class="control-group">
            <label for="image" class="control-label">广播图片:</label>
            <div class="controls">
                <input class="input-file" type="file" id="image" name="image" />
            </div>
        </div>
        <div class="control-group">
            <label for="link" class="control-label">广播链接:</label>
            <div class="controls">
                <textarea class="input-xlarge url required" id="link" name="appUrl" maxlength="255" rows="3">${appUrl}</textarea>
            </div>
        </div>
        <section id="spec">
            <div class="control-group">
                <label for="appk" class="control-label">包名:</label>
                <div class="controls">
                    <input type="text" id="appk" name="appk" value="${appk}" maxlength="255" class="required">
                </div>
            </div>
            <div class="control-group">
                <label for="fm" class="control-label">市场:</label>
                <div class="controls">
                    <input type="text" id="fm" name="fm" value="${fm}" maxlength="255">
                </div>
            </div>
            <div class="control-group">
                <label for="percent" class="control-label">权重:</label>
                <div class="controls">
                    <input type="text" id="percent" name="percent" data-slider-step="10" data-slider-min="0" data-slider-max="100" data-slider-value="${percent}" value="${percent}" />
                </div>
            </div>
        </section>
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