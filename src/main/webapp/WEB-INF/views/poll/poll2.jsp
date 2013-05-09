<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>Polling Service</title>
    <script src="${ctx}/js/table.js"></script>
    <script>
        $(document).ready(function(){
            $("#message").fadeOut(3000);
            $("#poll2-tab").addClass("active");
            $("#poll2-tab a").append("<i class='icon-remove-circle'></i>");
        });

        function deleteThis(id){
            if(confirm("确定要删除吗?")){
                window.location="../poll/poll2!delete.action?id="+id;
            }
        }
        function changeStatus(id){
            window.location="../poll/poll2!change.action?id="+id;
        }

    </script>
</head>
<body>
<h1>广播列表</h1>
<form id="mainForm" action="poll2.action" method="get" class="form-horizontal">


    <c:if test="${not empty actionMessages}">
        <div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${actionMessages}</div>
    </c:if>
    <div id="filter" style="margin-bottom:5px;">
        广播类型:<s:select list="#{'0':'上线通知','1':'广告'}" id="p_dtype" name="filter_EQS_dtype" listKey="key" listValue="value" cssClass="span2"></s:select>
        <div class="pull-right">
            <shiro:hasPermission name="store:edit">
                <a class="icon-plus" href="poll2!input.action">新增</a>
            </shiro:hasPermission>
            &nbsp;<a href="../poll2/xml/lock"><i class="icon-rss"></i></a>
        </div>
    </div>
    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <th>广告图片</th>
            <th><a href="javascript:sort('contentName','asc')">广播名称</a></th>
            <th><a href="javascript:sort('dtype','asc')">广播类型</a></th>
            <th><a href="javascript:sort('status','asc')">状态</a></th>
            <th><a href="javascript:sort('percent','asc')">权重</a></th>
            <th><a href="javascript:sort('createTime','asc')">更新时间</a></th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="page.result">
            <tr>
                <td><img alt="广告图片" src="${ctx}/image.action?path=${imgLink}" style="height: 20px;width: 70px;"></td>
                <td><a href="poll2!input.action?id=${id}&pageNo=${page.pageNo}">${contentName}</a></td>
                <td>
                    <s:if test="dtype==0">
                        上线通知
                    </s:if>
                    <s:elseif test="dtype==1">
                        广告
                    </s:elseif>
                </td>
                <td>
                    <s:if test="status==0">
                        未发布
                    </s:if><s:elseif test="status==1">
                    已发布
                    </s:elseif>
                </td>
                <td>
                    <s:if test="dtype==0">
                        N/n
                    </s:if>
                    <s:elseif test="dtype==1">
                        ${percent}
                    </s:elseif>
                </td>
                <td>${createTime}</td>
                <td><shiro:hasPermission name="store:edit">
                    <s:if test="status==0">
                        <a href="#" onclick="changeStatus(${id})">发布</a>
                    </s:if><s:elseif test="status==1">
                    <a href="#" onclick="changeStatus(${id})">取消发布</a>
                </s:elseif>
                    <a href="#" onclick="deleteThis(${id})">删除</a>
                </shiro:hasPermission></td>
            </tr>
        </s:iterator>
        </tbody>
    </table>
    <%@include file="/common/page.jsp" %>
</form>
</body>
</html>