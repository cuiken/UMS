<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%response.setStatus(200);%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>404 - Not found</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        @-moz-keyframes crazylefteye {
            0% {left:125px;top:65px;}
            10% {left:118px;top:56px;}
            20% {left:118px;top:56px;}
            40% {left:148px;top:62px;}
            50% {left:145px;top:72px;}
            60% {left:121px;top:70px;}
            70% {left:125px;top:65px;}
            100%{left:125px;top:65px;}
        }
        @-moz-keyframes crazyrighteye {
            0% {left:231px;top:68px;}
            10% {left:212px;top:62px;}
            20% {left:212px;top:62px;}
            40% {left:239px;top:64px;}
            50% {left:240px;top:80px;}
            60% {left:215px;top:73px;}
            70% {left:231px;top:68px;}
            100% {left:231px;top:68px;}
        }
        @-webkit-keyframes crazylefteye {
            0% {left:125px;top:65px;}
            10% {left:118px;top:56px;}
            20% {left:118px;top:56px;}
            40% {left:148px;top:62px;}
            50% {left:145px;top:72px;}
            60% {left:121px;top:70px;}
            70% {left:125px;top:65px;}
            100%{left:125px;top:65px;}
        }
        @-webkit-keyframes crazyrighteye {
            0% {left:231px;top:68px;}
            10% {left:212px;top:62px;}
            20% {left:212px;top:62px;}
            40% {left:239px;top:64px;}
            50% {left:240px;top:80px;}
            60% {left:215px;top:73px;}
            70% {left:231px;top:68px;}
            100% {left:231px;top:68px;}
        }
        @keyframes crazylefteye {
            0% {left:125px;top:65px;}
            10% {left:118px;top:56px;}
            20% {left:118px;top:56px;}
            40% {left:148px;top:62px;}
            50% {left:145px;top:72px;}
            60% {left:121px;top:70px;}
            70% {left:125px;top:65px;}
            100%{left:125px;top:65px;}
        }
        @keyframes crazyrighteye {
            0% {left:231px;top:68px;}
            10% {left:212px;top:62px;}
            20% {left:212px;top:62px;}
            40% {left:239px;top:64px;}
            50% {left:240px;top:80px;}
            60% {left:215px;top:73px;}
            70% {left:231px;top:68px;}
            100% {left:231px;top:68px;}
        }
        #beast404re{
            -moz-animation-name: crazyrighteye;
            -moz-animation-delay: 6s;
            -moz-animation-duration: 7s;
            -moz-animation-iteration-count: infinite;
            -moz-animation-play-state: running;
            -webkit-animation-name: crazyrighteye;
            -webkit-animation-delay: 6s;
            -webkit-animation-duration: 7s;
            -webkit-animation-iteration-count: infinite;
            -webkit-animation-play-state: running;
            animation-name: crazyrighteye;
            animation-delay: 6s;
            animation-duration: 7s;
            animation-iteration-count: infinite;
            animation-play-state: running;
        }
        #beast404le{
            -moz-animation-name: crazylefteye;
            -moz-animation-delay: 6s;
            -moz-animation-duration: 7s;
            -moz-animation-iteration-count: infinite;
            -moz-animation-play-state: running;
            -webkit-animation-name: crazylefteye;
            -webkit-animation-delay: 6s;
            -webkit-animation-duration: 7s;
            -webkit-animation-iteration-count: infinite;
            -webkit-animation-play-state: running;
            animation-name: crazylefteye;
            animation-delay: 6s;
            animation-duration: 7s;
            animation-iteration-count: infinite;
            animation-play-state: running;
        }
        #beastainer{position:relative;}
        #beast404re, #beast404le{position:absolute;}
        #beast404le{left:125px;top:65px;}
        #beast404re{left:231px;top:68px;}
        .beast {

            margin: 20px 0 20px 20px;
        }
        body {
            font: 14px/1.286 "Lucida Grande","Lucida Sans Unicode","DejaVu Sans",Lucida,Arial,Helvetica,sans-serif;
            color: #333;
        }
        h1{
            font-size: 1.857em;
        }
        .wrap {
            position: relative;
            padding: 0 10px;
            margin: 0 auto;
        }
        .wrap:after {
            content: ".";
            display: block;
            clear: both;
            height: 0;
            visibility: hidden;
        }
    </style>
</head>

<body>
<div class="wrap">
	<h1>Not Found</h1>
    <div id="beastainer">
        <img id="beast404le" src="${ctx}/static/images/beast-404_LE.png" alt="">
        <img id="beast404re" src="${ctx}/static/images/beast-404_RE.png" alt="">
        <img class="beast 404" src="${ctx}/static/images/beast-404.png" alt="">
    </div>
</div>
</body>
</html>