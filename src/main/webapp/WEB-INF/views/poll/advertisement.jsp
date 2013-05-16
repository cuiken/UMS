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

                location.href="../poll/advertisement!input.action?store="+$('#f_store').val()+"&dtype="+'${param['filter_EQS_dtype']}';

            });

            $("#f_store").val('${param['filter_EQS_store']}');
            var dtype='${param['filter_EQS_dtype']}';
            if(dtype=='client'){
                $("#c_ad").addClass('active');
            }else if(dtype=='store'){
                $("#s_ad").addClass('active');
            }else if(dtype=='sbot'){
                $("#sbot_ad").addClass('active');
            }else if(dtype=='diy'){
                $("#diy_ad").addClass('active');
            }
        });

        function deleteThis(id) {
            if (confirm("确定要删除吗?")) {
                var st=$("#f_store").val();
                window.location = "../poll/advertisement!delete.action?id=" + id+"&store="+st+"&dtype="+'${param['filter_EQS_dtype']}';
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
    <input type="hidden" name="filter_EQS_dtype" value="${param['filter_EQS_dtype']}">
    <c:if test="${not empty actionMessages}">
        <div id="message" class="alert alert-success">
            <button data-dismiss="alert" class="close">×</button>
                ${actionMessages}</div>
    </c:if>
    <ul class="nav nav-pills" style="margin-bottom: 2px;float: left;">
        <li id="c_ad">
            <a href="advertisement.action?filter_EQS_store=${param['filter_EQS_store']}&filter_EQS_dtype=client">客户端广告</a>
        </li>
        <li id="s_ad">
            <a href="advertisement.action?filter_EQS_store=${param['filter_EQS_store']}&filter_EQS_dtype=store">商店广告</a>
        </li>
        <li id="sbot_ad">
            <a href="advertisement.action?filter_EQS_store=${param['filter_EQS_store']}&filter_EQS_dtype=sbot">商店底部广告</a>
        </li>
        <li id="diy_ad">
            <a href="advertisement.action?filter_EQS_store=${param['filter_EQS_store']}&filter_EQS_dtype=diy">DIY广告</a>
        </li>
    </ul>
    <div id="filter" style="margin-bottom:5px; display: inline;margin-left: 10px;">
        商店: <s:select list="stores" id="f_store" name="filter_EQS_store" listKey="value" listValue="name" cssClass="span2"></s:select>
        <div class="pull-right">
            <shiro:hasPermission name="store:edit">
                <a class="icon-plus" id="addpoll" href="#">新增</a>
            </shiro:hasPermission>
            <s:if test="dtype=='client'">
            &nbsp;<a href="#" onclick="goXml();"><i class="icon-rss"></i></a>
            </s:if>
        </div>
    </div>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>编号</th>
            <th>预览图</th>
            <th>名称</th>
            <th><a href="javascript:sort('status','asc')">状态</a></th>
            <th><a href="javascript:sort('createTime','asc')">发布时间</a></th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="page.result">
            <tr>
                <td>${sort}</td>
                <td class="thumbnail_td"><img alt="图片" class="ad" src="${ctx}/image.action?path=${imgLink}"></td>
                <td><a href="advertisement!input.action?id=${id}&pageNo=${page.pageNo}">${name}</a></td>
                <%--<td>--%>
                    <%--<s:if test="dtype=='client'">--%>
                        <%--客户端--%>
                    <%--</s:if>--%>
                    <%--<s:elseif test="dtype=='store'">--%>
                        <%--商店首页--%>
                    <%--</s:elseif>--%>
                    <%--<s:elseif test="dtype=='store-hot'">--%>
                        <%--商店最热--%>
                    <%--</s:elseif>--%>
                    <%--<s:elseif test="dtype=='sbot'">--%>
                        <%--商店底部--%>
                    <%--</s:elseif>--%>
                    <%--<s:elseif test="dtype=='diy'">--%>
                        <%--DIY--%>
                    <%--</s:elseif>--%>
                <%--</td>--%>
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
                        <a href="#" onclick="changeStatus(${id})"><i class="icon-circle-blank"></i></a>
                    </s:if><s:elseif test="status==1">
                    <a href="#" onclick="changeStatus(${id})"><i class="icon-circle"></i></a>
                </s:elseif>
                    &nbsp;
                    <a href="#" onclick="deleteThis(${id})"><i class="icon-trash"></i></a>
                </shiro:hasPermission></td>
            </tr>
        </s:iterator>
        </tbody>
    </table>
    <%@include file="/common/page.jsp" %>
</form>
</body>
</html>