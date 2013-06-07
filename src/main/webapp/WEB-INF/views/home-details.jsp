<%@ page contentType="text/html;charset=UTF-8" %>
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
    body {
        background: #fff;
    }
    .more{
        padding: 0;
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

        <img src="${ctx}/files/${info.theme.preWebPath}">
        <s:if test="info.theme.preClientPath!=null">
            <img src="${ctx}/files/${info.theme.preClientPath}">
        </s:if>

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
    <div class="btns">
        <a href="${ctx}/home!details.action?id=${param.id}&offset=${info.offset-1}&${queryString}"><img class="offset-r"
                                                                                                        src="${ctx}/static/images/2.0/offset-left.png"></a>
        <a id="download" href="#"><span class="more-btn"><img src="${ctx}/static/images/2.0/down-split.png"><span
                style="display: block;margin-left: 15px;">免费下载</span></span></a>
        <a href="${ctx}/home!details.action?id=${param.id}&offset=${info.offset+1}&${queryString}"><img class="offset-r"
                                                                                                        src="${ctx}/static/images/2.0/offset-right.png"></a>
    </div>
</section>
<section class="description more">
    <h3>猜你喜欢</h3>

    <div class="content ajax-wrap">

        <ul class="v-list J_ajaxWrap">

            <li>
                <div class="icon">
                    <img src="${ctx}/files/${appInfo[0].theme.iconPath}">
                </div>
                <div class="y-split"></div>
                <div class="info">
                    <p class="title">${appInfo[0].title}</p>

                    <p class="txt">${appInfo[0].shortDescription}</p>

                    <div class="down-btn">

                        <img src="${ctx}/static/images/2.0/down.png">
                        <span><s:text name="home.down"></s:text></span>

                    </div>
                </div>
                <s:if test="theme.ishot==1">
                    <span class="icon_n"></span>
                </s:if><s:elseif test="theme.isnew==1">
                <span class="icon_n icon_n_new"></span>
            </s:elseif>
                <a href="#" onclick="goDownload('${appInfo[0].theme.id}','${appInfo[0].theme.downloadURL}');"
                   class="down-area"></a>
            </li>

            <li>
                <div class="icon">
                    <img src="${ctx}/files/${gameInfo[0].theme.iconPath}">
                </div>
                <div class="y-split"></div>
                <div class="info">
                    <p class="title">${gameInfo[0].title}</p>

                    <p class="txt">${gameInfo[0].shortDescription}</p>

                    <div class="down-btn">

                        <img src="${ctx}/static/images/2.0/down.png">
                        <span><s:text name="home.down"></s:text></span>

                    </div>
                </div>
                <s:if test="theme.ishot==1">
                    <span class="icon_n"></span>
                </s:if><s:elseif test="theme.isnew==1">
                <span class="icon_n icon_n_new"></span>
            </s:elseif>
                <a href="#" onclick="goDownload('${gameInfo[0].theme.id}','${gameInfo[0].theme.downloadURL}');"
                   class="down-area"></a>
            </li>

        </ul>
    </div>
    <div class="webkit-box" style="margin: 10px auto;">
        <span></span>
        <span class="more-btn">
            <a href="${ctx}/home!shelf.action?sf=game&${queryString}">更多精彩</a>
            <div class="more-split"></div>
        </span>
    </div>
</section>
<script src="${ctx}/static/zepto/zepto.min.js"></script>
<script src="${ctx}/static/zepto/android.js"></script>
<script>
    $(function () {
        $("#download").click(function () {
            $("#download > span").addClass("more-btn hasDown");
            $.ajax({
                type: "POST",
                url: "log/log!saveDownload.action?id=${info.theme.id}&${queryString}",
                dataType: "text",
                data: {queryString: '${info.theme.downloadURL}', cs: '${queryString}'},
                complete: function () {
                    location.href = '${info.theme.downloadURL}';
                }
            });
        })
    });
</script>
</body>
</html>