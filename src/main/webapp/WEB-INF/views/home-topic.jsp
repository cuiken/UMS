<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html lang="zh">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
    <title>Fun主题-主题</title>
     <link href="${ctx}/static/bootstrap/2.1.1/css/bootstraped.min.css" type="text/css" rel="stylesheet" />
    <%--<link rel="stylesheet" href="${ctx}/static/styles/site.min.css"/>--%>
    <link rel="stylesheet" href="${ctx}/static/styles/siteV2.0.css"/>

  </head>
    <body>
    <%@include file="home-nav.jsp"%>
    <div id="container">
        <div style="text-align: center;"><i style="font-size: 180px;" class="icon-fighter-jet"></i><h1>努力建设中!</h1></div>
    </div>
    <script src="${ctx}/static/zepto/zepto.min.js"></script>
    <script>
        $(function(){
            $("#topic").addClass("selected").removeAttr("href");
        })
    </script>
    </body>
</html>