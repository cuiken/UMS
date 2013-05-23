<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<% response.setHeader("remember", "true"); %>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
    <title>Fun主题-商店排行</title>
    <%--<link rel="stylesheet" href="${ctx}/static/styles/site.min.css"/>--%>
    <link rel="stylesheet" href="${ctx}/static/styles/siteV2.0.css">
</head>
<body>
<form action="home!hottest.action" method="get">
    <%@include file="home-nav.jsp"%>
    <%--<div id="dk_game_slider"></div>--%>
    <div id="container">
        <div class="content ajax-wrap">
           <ul class="v-list J_ajaxWrap">

            <s:iterator value="sorts" id="ff">
                <%--<div class="contents_info" id="content1" onclick="location.href='home!details.action?id=${f_id}&${queryString}';">--%>
                    <%--<div class="contents_image">--%>
                        <%--<img alt="${title}" onerror="this.src='${ctx}/static/images/default.png'" src="${ctx}/files/${icon_path}" width="72" height="72" style="margin: 3px;">--%>
                    <%--</div>--%>
                    <%--<div class="contents_txt">--%>
                        <%--<div style="margin-top: 10px;">--%>
                            <%--<span class="title">${title}</span>--%>
                            <%--<p>${short_description}</p>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%@include file="home-list2.jsp"%>
            </s:iterator>
           </ul>
            <div class="footer">
               <div class="J_scrollLoadMore load-btn click-state" data-api="home!jsonMore.action"></div>
                <a href="#" class="go-top"></a>
            </div>
        </div>
    </div>
    <%--<nav id="page-nav">--%>
        <%--<a href="?pageNo=${nextPage}&${queryString}"></a>--%>
    <%--</nav>--%>
    <%--<div class="ft-tool">--%>
        <%--<a href="#" class="go-top"></a>--%>
    <%--</div>--%>

</form>
<%--<script src="${ctx}/static/jquery/1.7.2/jquery.min.js"></script>--%>
<script src="${ctx}/static/zepto/zepto.min.js"></script>
<%--<script src="${ctx}/static/zepto/gameall.min.js"></script>--%>
<script src="${ctx}/static/zepto/android.js"></script>
<script>
    $(function(){
        $("#hottest").addClass("selected").removeAttr("href");
//        $(".contents_info").click(function(){
//            $(this).css("backgroundColor","#e7e6c8");
//        });
    });
</script>
</body>
</html>