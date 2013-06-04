<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
    <title>Fun主题-专题列表</title>
    <link rel="stylesheet" href="${ctx}/static/styles/siteV2.0.css">
</head>
<body>
<form action="home!hottest.action" method="get">
    <%@include file="home-details-nav.jsp"%>
    <div class="topic-top">
        <img src="${ctx}/static/images/2.0/topic.png">
        <img src="${ctx}/static/images/2.0/y-split.png">
        <span>
            ${topicDescription}
        </span>
    </div>
    <div id="container">
        <div class="content ajax-wrap">
            <ul class="v-list J_ajaxWrap">

                <s:iterator value="newestPage.result">
                    <%@include file="home-list.jsp"%>
                </s:iterator>
            </ul>
            <div class="footer">
                <div class="J_scrollLoadMore load-btn click-state" data-api="home!topicListJson.action?topicId=${param.topicId}"></div>
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
        $("#banner").attr("href","http://locker.uichange.com/UMS/log/redirect?url=http://54623.mmb.cn/wap/Column.do?columnId=456206");
        $("#banner > img").attr("src","${ctx}/static/images/2.0/ad/nvzhuang.jpg");
    })
</script>
</body>
</html>