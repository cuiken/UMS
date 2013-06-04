<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
    <title>Fun主题-商店排行</title>
    <link rel="stylesheet" href="${ctx}/static/styles/siteV2.0.css">

</head>
<body>
<form action="home!hottest.action" method="get">
    <%@include file="home-details-nav.jsp"%>
    <div id="container">
        <div class="content ajax-wrap">
            <ul class="v-list J_ajaxWrap">

                <s:iterator value="newestPage.result">
                    <%@include file="home-list.jsp"%>
                </s:iterator>
            </ul>
            <div class="footer">
                <div class="J_scrollLoadMore load-btn click-state" data-api="home!shelfJson.action?g=${param.g}&${queryString}"></div>
                <a href="#" class="go-top"></a>
            </div>
            <%@include file="home-banner.jsp"%>
        </div>
    </div>

</form>
<script src="${ctx}/static/zepto/zepto.min.js"></script>
<script src="${ctx}/static/zepto/android.js"></script>
<script>
$(function(){
        $("#hottest").addClass("selected").removeAttr("href");
        $("#banner").attr("href","http://locker.uichange.com/UMS/log/redirect?url=http://54629.mmb.cn/wap/Column.do?columnId=456471");
        $("#banner > img").attr("src","${ctx}/static/images/2.0/ad/qinglvzhuang.jpg");
    });
</script>
</body>
</html>