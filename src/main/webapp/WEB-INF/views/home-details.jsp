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
        background: none;
    }

    .preview{
        text-align: center;
    }
    .btns
    {
        margin: 10px auto;
        display: -webkit-box;
        -webkit-box-pack:center;
    }
    .btns .more-btn{
        margin: 0 auto;
        height: 40px;
        width: 150px;
        line-height: 40px;
        font-weight: bold;
        font-size: 16px;
    }
    .btns .offset-r{
        margin: 10px 14px;
    }

    .description{
        /*width: 280px;*/
        position: relative;
        margin: 0 10px;
        padding: 10px;
        border-radius: 4px;
        background-image: -webkit-linear-gradient(top, #f7f7f7,#e5e5e5);
        box-shadow: 0 0 5px #d7d4d4;
        overflow: hidden;
    }

    .description p{
        margin: 5px auto;
        clear: both;
        overflow: hidden;

    }
    .description .size span {
        display: block;
        float: left;
        width: 50%;
        margin-bottom: 5px;
    }

    .more{
        margin: 15px 0;
        height: 100%;
    }

    .more h3{
        margin: 2px auto;
        background: url(${ctx}/static/images/2.0/x-split.png) no-repeat 0 100%;
    }
    .webkit-box{
        display: -webkit-box;
        -webkit-box-pack:justify;
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
        background: url(${ctx}/static/images/2.0/x-split-l.png) no-repeat 0 100%;
    }

     .even li{
        background: url(${ctx}/static/images/2.0/x-split-r.png) no-repeat 0 100%;
    }
    .webkit-box .icon img{
        width: 50px;
        height: 50px;
        margin-left: 2px;
    }

    .more-btn{
        display: block;
        /*margin-left: 7px;*/
        /*width: 103px;*/
        height: 32px;
        line-height: 32px;
        text-align: center;
        background: -webkit-linear-gradient(top,#5dccf6,#195b9e);
        -webkit-border-radius: 4px;
        box-shadow: 0 0 5px #052e6b;
        color: #fff;
        width: 40%;
    }
    .more-btn a{
        color: #fff;
        display: block;
    }
    .webkit-box .info{
        -webkit-box-flex:1;
        overflow: hidden;
        line-height: 18px;
        max-width: 80px;
        font-size: 12px;
    }

    .txt {
        font-size: 12px;
    }

    .preview img{
        border: 3px solid #fff;
        box-shadow: 0 0 3px #ccc;
    }
    .toggle-btn{
        display: block;
        background: url(${ctx}/static/images/2.0/icon-down.png) no-repeat 0 0;
        width: 23px;
        height: 28px;
        position: absolute;
        right: 10px;
        bottom: 2px;
        background-size: 100% 100%;
    }
    .soft-info {
        position: relative;
        padding: 5px 10px 27px;
        border-top: 1px solid #fff;
        text-shadow: 0 1px 0 #fefefe;
        overflow: hidden;
    }
    .soft-info .introduce {
        margin: 5px 0;
        line-height: 19px;
        word-break: break-all;
        overflow: hidden;
    }
    .arr-d{
        background: url(${ctx}/static/images/2.0/icon-up.png) no-repeat 0 0;
        background-size: 100% 100%;
    }
</style>
<body>
<nav class="details">
    <a href="#" onclick="history.back();return false;"><img src="${ctx}/static/images/2.0/back.png"></a>
    <a>${info.title}</a>
    <a href="home.action"><img src="${ctx}/static/images/2.0/home.png"></a>
</nav>
<section id="container">
    <div class="preview">

        <img src="http://locker.uichange.com/UMS/files/${info.theme.preWebPath}">

    </div>
    <div class="btns">
        <img  class="offset-r" src="${ctx}/static/images/2.0/offset-left.png">
        <a style="text-decoration: none;display: block;height: 45px;margin: 10px 0;" href="http://locker.uichange.com/UMS/${info.theme.downloadURL}"><span class="more-btn">免费下载</span></a>
        <img  class="offset-r"src="${ctx}/static/images/2.0/offset-right.png">
    </div>
    <div class="description soft-info">
        <div class="size introduce J_info">
            <span>大小: ${fn:substring(info.theme.apkSize/1024/1024,0,4)} M</span>
            <span id="down" style="text-align: right"><s:text name="total.down"/>: ${totalDown}</span>
            <p><s:text name="home.author"/>: ${info.author}</p>
            <p><s:text name="home.desc"/>: ${info.longDescription}</p>
        </div>
        <span class="toggle-btn"></span>
    </div>
</section>
<section class="description more">
    <h3>猜你喜欢</h3>
    <div class="webkit-box">
        <ul class="webkit-box1 dk_split_item_continer odd v-list">
            <s:iterator value="catePage.result">
                <li>
                    <div class="icon">
                        <img alt="${theme.title}" src="http://locker.uichange.com/UMS/files/${theme.iconPath}">
                    </div>
                    <div class="y-split"></div>
                    <div class="info">
                        <p>${theme.title}</p>
                        <p class="txt">大小: ${fn:substring(theme.apkSize/1024/1024,0,4)} M</p>
                    </div>
                    <a href="${ctx}/home!details.action?id=${theme.id}&${queryString}" class="down-area"></a>
                </li>
            </s:iterator>

        </ul>
        <ul class="webkit-box1 dk_split_item_continer even v-list">
            <li>
                <div class="icon">
                    <img alt="${appInfo.theme.title}" src="http://locker.uichange.com/UMS/files/${appInfo.theme.iconPath}">
                </div>
                <div class="y-split"></div>
                <div class="info">
                    <p>${appInfo.theme.title}</p>
                    <p class="txt">大小: ${fn:substring(appInfo.theme.apkSize/1024/1024,0,4)} M</p>
                </div>
                <a href="${ctx}/home!details.action?id=${appInfo.theme.id}&${queryString}" class="down-area"></a>
            </li>
            <li>
                <div class="icon">
                    <img src="http://locker.uichange.com/UMS/files/${gameInfo.theme.iconPath}">
                </div>
                <div class="y-split"></div>
                <div class="info">
                    <p>${gameInfo.theme.title}</p>
                    <p class="txt">大小: ${fn:substring(gameInfo.theme.apkSize/1024/1024,0,4)} M</p>
                </div>
                <a href="${ctx}/home!details.action?id=${gameInfo.theme.id}&${queryString}" class="down-area"></a>
            </li>

        </ul>
    </div>
    <div class="webkit-box" style="margin: 10px auto;">
        <span class="more-btn"><a href="${ctx}/home!more.action?cid=${info.theme.categories[0].id}&${queryString}">更多主题</a></span>
        <span class="more-btn"><a href="${ctx}/home!shelf.action?sf=game&${queryString}">更多精彩</a></span>
    </div>
</section>
<script src="${ctx}/static/zepto/zepto.min.js"></script>
<script src="${ctx}/static/zepto/android.js"></script>
</body>
</html>