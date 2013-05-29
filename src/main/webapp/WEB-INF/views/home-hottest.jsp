<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<% response.setHeader("remember", "true"); %>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
    <title>Fun主题-商店排行</title>
    <link rel="stylesheet" href="${ctx}/static/styles/siteV2.0.css">
</head>
<body>
<form action="home!hottest.action" method="get">
    <%@include file="home-nav.jsp"%>
    <div id="container">
        <div class="content ajax-wrap">
           <ul class="v-list J_ajaxWrap">

            <s:iterator value="sorts" id="ff">

                <%@include file="home-list2.jsp"%>
            </s:iterator>
           </ul>
            <div class="footer">
               <div class="J_scrollLoadMore load-btn click-state" data-api="home!jsonMore.action"></div>
                <a href="#" class="go-top"></a>
            </div>
        </div>
    </div>

</form>
<script src="${ctx}/static/zepto/zepto.min.js"></script>
<script src="${ctx}/static/zepto/android.js"></script>
<script>
    $(function(){
        $("#hottest").addClass("selected").removeAttr("href");

    });
</script>
</body>
</html>