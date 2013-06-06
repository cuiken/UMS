<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>厂商列表</title>
    <script>
        $(document).ready(function(){
            $("#message").fadeOut(3000);
            $("#coop-tab").addClass("active");
            $("#coop-tab a").append("<i class='icon-remove-circle'></i>");
        });
        function deleteThis(id){
            if(confirm("确定要删除吗?")){
                window.location="coop!delete.action?id="+id;
            }
        }
    </script>
</head>
<body>
<form action="coop.action" method="get">
    <h1>厂商列表</h1>
    <c:if test="${not empty actionMessages}">
        <div id="message" class="alert alert-success">${actionMessages}</div>
    </c:if>
    <div class="pull-right">
        <shiro:hasPermission name="category:edit">
            <a class="icon-plus" href="coop!input.action">新增</a>
        </shiro:hasPermission>
    </div>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>厂家名称</th>
            <th>厂家标识</th>
            <th>配给渠道号</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="page.result">
            <tr>
                <td>${name}</td>
                <td>${value}</td>
                <td>${channel}</td>
                <td><shiro:hasPermission name="category:edit">
                    <a href="coop!input.action?id=${id}"><i class="icon-edit"></i></a>&nbsp;&nbsp;
                    <a href="#" onclick="deleteThis(${id})"><i class="icon-trash"></i></a>
                </shiro:hasPermission></td>
            </tr>
        </s:iterator>
        </tbody>
    </table>
    <%@include file="/common/page.jsp"%>
</form>

</body>
</html>