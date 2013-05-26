<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
    <title>Fun主题-商店排行</title>
    <link rel="stylesheet" href="${ctx}/static/styles/siteV2.0.css">
    <style>
        .details a{
            width: 33.3%;
            background: none;
        }
    </style>
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
                <div class="J_scrollLoadMore load-btn click-state" data-api="home!shelfJson.action?g=${param.g}"></div>
                <a href="#" class="go-top"></a>
            </div>
        </div>
    </div>

</form>
<script src="${ctx}/static/zepto/zepto.min.js"></script>
<script src="${ctx}/static/zepto/android.js"></script>
<script>

</script>
</body>
</html>