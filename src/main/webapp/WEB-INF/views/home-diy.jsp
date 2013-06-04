<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<% response.setHeader("remember", "true"); %>
<!DOCTYPE HTML>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
    <title>Fun主题-diy</title>

    <link rel="stylesheet" href="${ctx}/static/styles/siteV2.0.css"/>
    <style>
        .diy-top{
            padding-top: 20px;
            width: 100%;
        }

    </style>
</head>
<body>

<form action="home.action" method="get">
   <img src="${ctx}/static/images/2.0/DIY.png" class="diy-top">
    <div id="container">
        <div id="dk_game_slider"></div>

        <div class="content ajax-wrap">
            <ul class="v-list J_ajaxWrap">
                <s:iterator value="newestPage.result">

                    <%@include file="home-list.jsp"%>
                </s:iterator>
            </ul>
            <div class="footer">
                <div class="J_scrollLoadMore load-btn click-state" data-api="home!shelfJson.action?g=diy"></div>
                <a href="#" class="go-top"></a>
            </div>
            <%@include file="home-banner.jsp"%>
        </div>


    </div>

</form>
<script src="${ctx}/static/zepto/zepto.min.js"></script>
<script src="${ctx}/static/zepto/gameall.min.js"></script>
<script src="${ctx}/static/zepto/android.js"></script>
<script>
    $(document).ready(function(){
        $("#banner").attr("href","http://locker.uichange.com/UMS/log/redirect?url=http://54623.mmb.cn/wap/Column.do?columnId=456206");
        $("#banner > img").attr("src","${ctx}/static/images/2.0/ad/nvzhuang.jpg");
        var bars=${bars};
        $.ui.slider('#dk_game_slider', {
            index: bars.rand(),
            showArr: false,
            autoPlayTime: 2500,
            content: bars
        });
    });
</script>
</body>
</html>