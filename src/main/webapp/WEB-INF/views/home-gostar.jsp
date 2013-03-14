<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Fun主题-星座专题</title>
    <style >

        *{
            margin:0;
            padding: 0;
        }

        img{
            display: block;
            width: 100%;
        }

    </style>
</head>
<body>
<a href="home!star.action?${queryString}">
    <img alt="star" src="${ctx}/static/images/star/star_bg.jpg">
</a>
<a href="home.action?${queryString}">
    <img alt="home" src="${ctx}/static/images/star/go.jpg">
</a>
</body>
</html>