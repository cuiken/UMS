<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>
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
        background: #fff;
    }

</style>
<body>
<nav class="details">
    <a href="#" onclick="history.back();return false;"><img src="${ctx}/static/images/2.0/back.png"></a>
    <a>${info.title}</a>
    <a href="home.action?${queryString}"><img src="${ctx}/static/images/2.0/home.png"></a>
</nav>
<section id="container">
    <div class="preview">

        <img src="${ctx}/image.action?path=${info.theme.preWebPath}">
        <s:if test="info.theme.preClientPath!=null">
        <img src="${ctx}/image.action?path=${info.theme.preClientPath}">
        </s:if>

    </div>
    <div class="btns">
        <a href="${ctx}/home!details.action?id=${param.id}&offset=${info.offset-1}&${queryString}"><img  class="offset-r" src="${ctx}/static/images/2.0/offset-left.png"></a>
        <a id="download"  href="#"><span class="more-btn"><img src="${ctx}/static/images/2.0/down-split.png"><span style="display: block;margin-left: 15px;">免费下载</span></span></a>
        <a href="${ctx}/home!details.action?id=${param.id}&offset=${info.offset+1}&${queryString}"><img  class="offset-r"src="${ctx}/static/images/2.0/offset-right.png"></a>
    </div>
    <div class="description soft-info">
        <div class="size introduce J_info">
            <span>大小:
                <s:if test="info.theme.apkSize!=null">
                    ${fn:substring(info.theme.apkSize/1024/1024,0,4)}
                </s:if><s:else>
                    ${fn:substring(info.theme.uxSize/1024/1024,0,4)}
                </s:else>
                M</span>
            <s:if test="info.theme.dtype!=2">
            <span id="down" style="text-align: right"><s:text name="total.down"/>: ${totalDown}</span>
            </s:if>
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
            <s:if test="info.theme.dtype==2">
                <s:iterator value="appInfo">
                    <li>
                        <div class="icon">
                            <img alt="${title}" src="/UMS/image.action?path=${theme.iconPath}">
                        </div>
                        <div class="info">
                            <p>${title}</p>
                            <p class="txt">大小: ${fn:substring(theme.apkSize/1024/1024,0,4)} M</p>
                            <p>下载</p>
                        </div>
                        <a href="${ctx}/home!details.action?id=${theme.id}&${queryString}" class="down-area"></a>
                    </li>
                </s:iterator>
            </s:if><s:else>
            <s:iterator value="catePage.result">
                <li>
                    <div class="icon">
                        <img alt="${title}" src="/UMS/image.action?path=${theme.iconPath}">
                    </div>
                    <div class="info">
                        <p>${title}</p>
                        <p class="txt">大小:
                            <s:if test="theme.apkSize!=null">
                                ${fn:substring(theme.apkSize/1024/1024,0,4)}
                            </s:if><s:else>
                                ${fn:substring(theme.uxSize/1024/1024,0,4)}
                            </s:else>
                            M</p>
                        <p>下载</p>
                    </div>
                    <a href="${ctx}/home!details.action?id=${theme.id}&${queryString}" class="down-area"></a>
                </li>
            </s:iterator>
        </s:else>
        </ul>

        <ul class="webkit-box1 dk_split_item_continer even v-list">
            <s:if test="info.theme.dtype==2">
                <s:iterator value="gameInfo">
                    <li>
                        <div class="icon">
                            <img alt="${title}" src="/UMS/image.action?path=${theme.iconPath}">
                        </div>
                        <div class="info">
                            <p>${title}</p>
                            <p class="txt">大小: ${fn:substring(theme.apkSize/1024/1024,0,4)} M</p>
                            <p>下载</p>
                        </div>
                        <a href="${ctx}/home!details.action?id=${theme.id}&${queryString}" class="down-area"></a>
                    </li>
                </s:iterator>
            </s:if><s:else>
            <li>
                <div class="icon">
                    <img alt="${appInfo[0].title}" src="/UMS/image.action?path=${appInfo[0].theme.iconPath}">
                </div>
                <div class="info">
                    <p>${appInfo[0].title}</p>
                    <p class="txt">大小: ${fn:substring(appInfo[0].theme.apkSize/1024/1024,0,4)} M</p>
                    <p>下载</p>
                </div>
                <a href="${ctx}/home!details.action?id=${appInfo[0].theme.id}&${queryString}" class="down-area"></a>
            </li>
            <li>
                <div class="icon">
                    <img src="/UMS/image.action?path=${gameInfo[0].theme.iconPath}">
                </div>
                <div class="info">
                    <p>${gameInfo[0].title}</p>
                    <p class="txt">大小: ${fn:substring(gameInfo[0].theme.apkSize/1024/1024,0,4)} M</p>
                    <p>下载</p>
                </div>
                <a href="${ctx}/home!details.action?id=${gameInfo[0].theme.id}&${queryString}" class="down-area"></a>
            </li>
        </s:else>
        </ul>
    </div>
    <div class="webkit-box" style="margin: 10px auto;">
        <span class="more-btn">
            <s:if test="info.theme.dtype==2">
                <a href="${ctx}/home!shelf.action?sf=app&${queryString}">
                   更多应用</a>
            </s:if>
           <s:else>
               <a href="${ctx}/home!more.action?cid=${info.theme.categories[0].id}&${queryString}">
                   更多主题</a>
           </s:else>
            <div class="more-split"></div>
        </span>
        <span class="more-btn">
            <a href="${ctx}/home!shelf.action?sf=game&${queryString}">
                <s:if test="info.theme.dtype==2">更多游戏</s:if><s:else>更多精彩</s:else></a>
            <div class="more-split"></div>
        </span>
    </div>
</section>
<script src="${ctx}/static/zepto/zepto.min.js"></script>
<script src="${ctx}/static/zepto/android.js"></script>
<script>
    $(function(){
        $("#download").click(function(){
            $("#download > span").addClass("more-btn hasDown");
            $.ajax({
                type:"POST",
                url:"log/log!saveDownload.action?id=${info.theme.id}&${queryString}",
                dataType:"text",
                data:{queryString:'${info.theme.downloadURL}',cs:'${queryString}'},
                complete:function(){
                    location.href='${info.theme.downloadURL}';
                }
            });
        })
    });
</script>
</body>
</html>