<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
    <title>Fun主题-商店详细</title>
    <link rel="stylesheet" href="${ctx}/static/styles/siteV2.0.css"/>
</head>
<style>
    body{
        background: #faf7f0;
    }
    .details a{
        width: 33.3%;
    }

    .btns .offset-r{
        margin: 10px 14px;
    }

    .description{
        width: 280px;
        height: 80px;
        margin: 0 10px;
        padding: 10px;
        border-radius: 4px;
        background-image: -webkit-linear-gradient(top, #f7f7f7,#e5e5e5);
        box-shadow: 0 0 5px #d7d4d4;
    }

    .description p{
        margin: 5px auto;
        clear: both;

    }
    .description .size span {
        display: block;
        float: left;
        width: 50%;
        margin-bottom: 5px;
    }

    .more{
        margin-top: 15px;
        height: 100%;
    }

    .more h3{
        margin: 2px auto;
        background: url(${ctx}/static/images/2.0/x-split.png) no-repeat 0 100%;
    }
    .webkit-box{
        display: -webkit-box;
    }

    .webkit-box1{
        -webkit-box-flex: 1;
    }

    .dk_split_item_continer{
        list-style: none;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        clear: both;
    }
    .dk_split_item_continer li {
        height: 50px;
        margin: 0;
        padding: 8px 0px;
        display: -webkit-box;
        -webkit-box-orient: horizontal;
        -webkit-box-pack: justify;
        -webkit-box-align: center;
        position: relative;
        background: url(${ctx}/static/images/2.0/x-split.png) no-repeat 0 100%;
    }

    .webkit-box .icon img{
        width: 50px;
        height: 50px;
        margin-left: 2px;
    }

    .txt {
        font-size: 12px;
    }
</style>
<body>
<nav class="details">
    <a href="home.action"><img src="${ctx}/static/images/2.0/back.png"></a>
    <a>${info.title}</a>
    <a href="home.action"><img src="${ctx}/static/images/2.0/home.png"></a>
</nav>
<section id="container">
    <div class="preview">

        <img src="${ctx}/static/images/2.0/preview.jpg">

    </div>
    <div class="btns">
        <img  class="offset-r" src="${ctx}/static/images/2.0/offset-left.png">
        <img  class="down-btn" src="${ctx}/static/images/2.0/down-btn.png">
        <img  class="offset-r"src="${ctx}/static/images/2.0/offset-right.png">
    </div>
    <div class="description">
        <div class="size">
            <span>大小: ${fn:substring(info.theme.apkSize/1024/1024,0,4)} M</span>
            <span id="down"><s:text name="total.down"/>: ${totalDown}</span>
        </div>
        <p><s:text name="home.author"/>: ${info.author}</p>
        <p><s:text name="home.desc"/>: ${fn:substring(info.longDescription,0,30)}...</p>
    </div>
</section>
<section class="description more">
    <h3>猜你喜欢</h3>
    <div class="webkit-box">
        <ul class="webkit-box1 dk_split_item_continer">
            <li>
                <div class="icon">
                    <img src="${ctx}/static/images/2.0/1.jpg">
                </div>
                <div class="info">
                    <p>薰衣草</p>
                    <p class="txt">大小: 0.3M <span>&nbsp;下载</span></p>
                </div>
            </li>
            <li>
                <div class="icon">
                    <img src="${ctx}/static/images/2.0/2.jpg">
                </div>
                <div class="info">
                    <p>薰衣草之</p>
                    <p class="txt">大小: 0.3M<span>&nbsp;下载</span></p>
                </div>
            </li>
            <li><img src="${ctx}/static/images/2.0/more.png"></li>
        </ul>
        <ul class="webkit-box1 dk_split_item_continer">
            <li>
                <div class="icon">
                    <img src="${ctx}/static/images/2.0/3.jpg">
                </div>
                <div class="info">
                    <p>薰衣</p>
                    <p class="txt">大小: 0.3M<span>&nbsp;下载</span></p>
                </div>
            </li>
            <li>
                <div class="icon">
                    <img src="${ctx}/static/images/2.0/4.jpg">
                </div>
                <div class="info">
                    <p>薰衣草之恋</p>
                    <p class="txt">大小: 0.3M<span>&nbsp;下载</span></p>
                </div>
            </li>
            <li><img src="${ctx}/static/images/2.0/more.png"></li>
        </ul>
    </div>
</section>
</body>
</html>