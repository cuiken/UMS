<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Fun主题-星座专题</title>
    <style>

        * {
            margin: 0;
            padding: 0;
        }

        .start_bg {
            display: block;
            width: 100%;

        }

        #outer {
            width: auto;
            height: auto;
            position: relative;

        }

        #inner {
            position: absolute;
            top: 650px;
            left: 190px;

        }
    </style>
</head>
<body>
<div id="outer">
    <a href="home!star.action?${queryString}">
        <img class="start_bg" src="${ctx}/static/images/star/star_bg.jpg">
    </a>
    <div id="inner">
        <a href="home.action?${queryString}">
            <img class="icon" src="${ctx}/static/images/star/go.png">
        </a>
    </div>
</div>
</body>
</html>