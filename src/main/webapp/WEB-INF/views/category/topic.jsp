<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>专题列表</title>
    <script>
        $(document).ready(function(){
            $("#message").fadeOut(3000);
            $("#topic-tab").addClass("active");
            $("#topic-tab a").append("<i class='icon-remove-circle'></i>");
        });
        function deleteThis(id){
            if(confirm("确定要删除吗?")){
                window.location="topic!delete.action?id="+id;
            }
        }
    </script>
</head>
<body>
<form action="topic.action" method="get">
    <h1>专题列表</h1>
    <c:if test="${not empty actionMessages}">
        <div id="message" class="alert alert-success">${actionMessages}</div>
    </c:if>
    <div class="pull-right">
        <shiro:hasPermission name="category:edit">
            <a class="icon-plus" href="topic!input.action">新增</a>
        </shiro:hasPermission>
    </div>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>序号</th>
            <th>预览图</th>
            <th>专题名称</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="topics">
            <tr>
                <td>${sort}</td>
                <td class="thumbnail_td"><img class="ad" alt="${name}" src="${ctx}/image.action?path=${icon}"></td>
                <td>${name}</td>
                <td><shiro:hasPermission name="category:edit">
                    <a href="topic!input.action?id=${id}"><i class="icon-edit"></i></a>&nbsp;&nbsp;
                    <a href="#" onclick="deleteThis(${id})"><i class="icon-trash"></i></a>
                </shiro:hasPermission></td>
            </tr>
        </s:iterator>
        </tbody>
    </table>
</form>

</body>
</html>