<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html manifest="ums.appcache">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Fun主题-星座专题</title>
    <style >

        *{
            margin:0;
            padding: 0;
        }
        body{
            margin: 0 auto;
            max-width: 320px;
            overflow-x: hidden;
            background: #474646;
            background: url(${ctx}/static/images/man/BG112.jpg);
            background-size:  93px 100%;
            font: 14px 'Helvetica Neue',Arial,sans-serif;

        }
        .warp{
            background: url(${ctx}/static/images/man/bg.jpg) no-repeat;
            width: 100%;
            margin: 0 auto 0;
        }

        #top{
            margin: 10px;
            float: right;
        }

        #center{
            clear: both;
            margin: 10px;
            float: right;

        }

        #footer{
            margin: 20px auto 0;
            clear: both;
            width: 100%;
            font-size: 0;
            letter-spacing: normal;
            word-spacing: normal;
            vertical-align: top;

        }

        ul li{
            list-style-type: none;
            line-height: 73px;
            height: 73px;
        }

        #footer ul li{
            width: 49%;
            height: 98px;
            display: inline-block;

        }

        .title{
            clear: both;
            text-align: right;
            margin: 10px 5px ;
            max-height: 26px;
        }
        .title img{
            max-height: 26px;
            max-width: 100%;

        }

        li img{
            width: 150px;
        }

        .title span{
            font-style: italic;
            font-weight: bolder;
            text-shadow:0 0 20px #ff6600;
            color:#fff;
            text-shadow: #ff6600 0px 0px 10px;
            text-shadow: #ff6600 0px 0px 10px 10px;
        }
        .btn_home{
            clear: both;
            text-align: right;
            width: 100%;
            height: 59px;
            padding-bottom: 20px;
        }
        .btn_home img{
            max-height: 100%;
        }
    </style>
</head>
<body >
<div class="warp">
    <div style="height:95px;"></div>
    <div style="margin-top: 95px;">
        <div class="title">
            <img src="${ctx}/static/images/man/t_tec.png">
        </div>
        <div id="top">
            <ul>
                <li><a href="${ctx}/home!details.action?id=353&f=man&${queryString}"><img alt="指纹解锁" src="${ctx}/static/images/man/zhiwen.png"></a></li>
                <li><a href="${ctx}/home!details.action?id=237&f=man&${queryString}"><img alt="定时炸弹" src="${ctx}/static/images/man/bomb.png"></a></li>
                <li><a href="${ctx}/home!details.action?id=236&f=man&${queryString}"><img alt="沙漠之鹰" src="${ctx}/static/images/man/gun.png"></a></li>
            </ul>
        </div>
        <div class="title">
            <img src="${ctx}/static/images/man/t_car.png">
        </div>
        <div id="center">
            <ul>
                <li><a href="${ctx}/home!details.action?id=156&f=man&${queryString}"><img alt="女人" src="${ctx}/static/images/man/mali.png"></a></li>
                <li><a href="${ctx}/home!details.action?id=158&f=man&${queryString}"><img alt="兰博基尼" src="${ctx}/static/images/man/car.png"></a></li>
                <li><a href="${ctx}/home!details.action?id=349&f=man&${queryString}"><img alt="美女" src="${ctx}/static/images/man/youhuo.png"></a></li>
            </ul>
        </div>
        <div class="title">
            <img src="${ctx}/static/images/man/t_daily.png">
        </div>
        <div id="footer">
            <ul>
                <li><a href="${ctx}/home!details.action?id=348&f=man&${queryString}"><img alt="骷髅骑士" src="${ctx}/static/images/man/kulou.png"></a></li>
                <li><a href="${ctx}/home!details.action?id=325&f=man&${queryString}"><img alt="一支烟" src="${ctx}/static/images/man/smoke.png"></a></li>
                <li><a href="${ctx}/home!details.action?id=358&f=man&${queryString}"><img alt="男表" src="${ctx}/static/images/man/watch.png"></a></li>
                <li><a href="${ctx}/home!details.action?id=106&f=man&${queryString}"><img alt="啤酒" src="${ctx}/static/images/man/beer.png"></a></li>
            </ul>
        </div>
        <div class="btn_home">
            <a href="home.action?${queryString}"><img alt="首页" src="${ctx}/static/images/man/home.png"></a>
        </div>
    </div>
</div>
</body>
</html>