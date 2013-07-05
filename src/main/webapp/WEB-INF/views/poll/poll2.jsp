<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>Polling Service</title>
    <script src="${ctx}/static/zepto/table.js"></script>
    <script>
        $(document).ready(function(){
            $("#message").fadeOut(3000);
            $("#poll2-tab").addClass("active");
            $("#poll2-tab a").append("<i class='icon-remove-circle'></i>");

            var dtype='${param['filter_EQS_dtype']}';
            if(dtype==0){
                $("#pollNT").addClass("active");
                $(".thumbnail_td img").addClass("ad");
            }else if(dtype==1){
                $("#pollAD").addClass("active");
            }
        });

        function deleteThis(id){
            if(confirm("确定要删除吗?")){
                window.location="../poll/poll2!delete.action?id="+id+"&dtype="+'${param['filter_EQS_dtype']}';
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
    <input type="hidden" name="filter_EQS_dtype" value="${param['filter_EQS_dtype']}">

    <c:if test="${not empty actionMessages}">
        <div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${actionMessages}</div>
    </c:if>
    <ul class="nav nav-pills" style="margin-bottom: 2px;float: left;">
        <li id="pollNT">
            <a href="poll2.action?filter_EQS_dtype=0">上线通知</a>
        </li>
        <li id="pollAD">
            <a href="poll2.action?filter_EQS_dtype=1">抽屉广告</a>
        </li>
    </ul>
    <div id="filter" style="margin-bottom:5px;">
         <div class="pull-right">
            <shiro:hasPermission name="store:edit">
                <a class="icon-plus" href="poll2!input.action?dtype=${param['filter_EQS_dtype']}">新增</a>
            </shiro:hasPermission>
            &nbsp;<a href="../poll2/xml/lock"><i class="icon-rss"></i></a>
        </div>
    </div>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>预览图</th>
            <th><a href="javascript:sort('contentName','asc')">名称</a></th>
            <th><a href="javascript:sort('status','asc')">状态</a></th>
            <th><a href="javascript:sort('percent','asc')">权重</a></th>
            <th><a href="javascript:sort('createTime','asc')">更新时间</a></th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="page.result">
            <tr>
                <td class="thumbnail_td"><img alt="图片" src="${ctx}/image.action?path=${imgLink}"></td>
                <td><a href="poll2!input.action?id=${id}&pageNo=${page.pageNo}">${contentName}</a></td>
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
                    <s:else>
                        ${percent}
                    </s:else>
                </td>
                <td>${createTime}</td>
                <td><shiro:hasPermission name="store:edit">
                    <s:if test="status==0">
                        <a href="#" onclick="changeStatus(${id})"><i class="icon-circle-blank"></i></a>
                    </s:if><s:elseif test="status==1">
                    <a href="#" onclick="changeStatus(${id})"><i class="icon-circle"></i></a>
                </s:elseif>
                    &nbsp;<a href="#" onclick="deleteThis(${id})"><i class="icon-trash"></i></a>
                </shiro:hasPermission></td>
            </tr>
        </s:iterator>
        </tbody>
    </table>
    <%@include file="/WEB-INF/layouts/page.jsp" %>
</form>
</body>
</html>