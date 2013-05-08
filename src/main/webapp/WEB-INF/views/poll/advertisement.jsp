<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Advertisement List</title>
    <script src="${ctx}/js/table.js"></script>
    <script>
        $(document).ready(function () {
            $("#message").fadeOut(3000);
            $("#advertisement-tab").addClass("active");
            $("#advertisement-tab a").append("<i class='icon-remove-circle'></i>");
            $("#f_store").change(function(){
                search();
            });
            $("#addpoll").click(function(){

                location.href="../poll/advertisement!input.action?store="+$('#f_store').val();

            });

            $("#f_store").val('${param['filter_EQS_store']}');
        });

        function deleteThis(id) {
            if (confirm("确定要删除吗?")) {
                var st=$("#f_store").val();
                window.location = "../poll/advertisement!delete.action?id=" + id+"&store="+st;
            }
        }
        function changeStatus(id) {
           var st=$("#f_store").val();
           window.location = "../poll/advertisement!change.action?id=" + id+"&store="+st;
        }

        function goXml(){
            var sid=$("#f_store").val();
            window.location="../poll/advertisement!generateXml.action?st="+sid;
        }
    </script>
</head>
<body>
<h1>广告条列表</h1>

<form id="mainForm" action="advertisement.action" method="get" class="form-horizontal">

    <c:if test="${not empty actionMessages}">
        <div id="message" class="alert alert-success">
            <button data-dismiss="alert" class="close">×</button>
                ${actionMessages}</div>
    </c:if>
    <div id="filter" style="margin-bottom:5px;">
        商店: <s:select list="stores" id="f_store" name="filter_EQS_store" listKey="value" listValue="name" cssClass="span2"></s:select>
        <div class="pull-right"><a href="#" onclick="goXml();"><i class="icon-rss"></i>XML</a></div>
    </div>
    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <th>编号</th>
            <th>预览图</th>
            <th>名称</th>
            <th><a href="javascript:sort('dtype','asc')">类型</a></th>
            <th><a href="javascript:sort('status','asc')">状态</a></th>
            <th><a href="javascript:sort('createTime','asc')">发布时间</a></th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="page.result">
            <tr>
                <td>${sort}</td>
                <td><img alt="" src="${ctx}/image.action?path=${imgLink}" style="height: 20px;width: 70px;"></td>
                <td><a href="advertisement!input.action?id=${id}&pageNo=${page.pageNo}">${name}</a></td>
                <td>
                    <s:if test="dtype=='client'">
                        客户端
                    </s:if>
                    <s:elseif test="dtype=='store'">
                        商店首页
                    </s:elseif>
                    <s:elseif test="dtype=='store-hot'">
                        商店最热
                    </s:elseif>
                </td>
                <td>
                    <s:if test="status==0">
                        未发布
                    </s:if><s:elseif test="status==1">
                    已发布
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
    <shiro:hasPermission name="store:edit">
        <a class="btn" id="addpoll" href="#">新增广告</a>
    </shiro:hasPermission>
</form>
</body>
</html>