<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
    <title>Mpb-商店详细</title>
    <link rel="stylesheet" href="${ctx}/static/styles/siteV2.0.css"/>
</head>
<style>
    body {
        background: #fff;
    }
    .more{
        padding: 0;
    }
    footer{
        background: #e1e1e1;
        padding-bottom: 10px;
    }
    footer a{
        display: block;
    }
    footer img{
        width: 99%;
        height: auto;
    }

    footer li{
        text-align: center;
    }
    footer li span{
        display: block;
        margin: 10px 0;

    }
    footer ul:first-child span{
        border-right: 1px solid #a1a1a1;
    }
</style>
<body>
<nav class="details">
    <a href="#" onclick="history.back();return false;"><img src="${ctx}/static/images/2.0/back.png"></a>
    <a>${info.title}</a>
    <a href="mpb-store.action?${queryString}"><img src="${ctx}/static/images/2.0/home.png"></a>
</nav>
<section id="container">
    <div class="preview">

        <img src="${ctx}/files/${info.theme.preWebPath}">
        <s:if test="info.theme.preClientPath!=null">
            <img src="${ctx}/files/${info.theme.preClientPath}">
        </s:if>

    </div>
    <div class="btns">
        <a href="${ctx}/store/mpb-store!details.action?id=${param.id}&offset=${info.offset-1}&${queryString}"><img class="offset-r"
                                                                                                        src="${ctx}/static/images/2.0/offset-left.png"></a>
        <a id="download" href="#"><span class="more-btn"><img src="${ctx}/static/images/2.0/down-split.png"><span
                style="display: block;margin-left: 15px;">免费下载</span></span></a>
        <a href="${ctx}/store/mpb-store!details.action?id=${param.id}&offset=${info.offset+1}&${queryString}"><img class="offset-r"
                                                                                                        src="${ctx}/static/images/2.0/offset-right.png"></a>
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