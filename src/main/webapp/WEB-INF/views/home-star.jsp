<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<% response.setHeader("remember", "true"); %>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=2.0; user-scalable=0;">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <!--[if lt IE 9]><script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->

    <title>Fun主题-星座专题</title>
    <link rel="stylesheet" href="${ctx}/static/styles/site.min.css" media="screen"/>
</head>
<body>

<form action="home.action" method="get">
    <div class="dk_nav_back_gree_title_bar">
        <a class="dk_pos_left dk_back_page" href="javascript:history.back();">
            <img src="${ctx}/static/images/go_back.png">
        </a>
        <span>${categoryName}</span>
        <a class="dk_pos_right" href="home.action?${queryString}">
            <img src="${ctx}/static/images/go_home.png">
        </a>
    </div>
    <div id="container">
        <s:iterator value="newestPage.result">
            <div class="contents_info" id="content1" onclick="location.href='home!details.action?id=${theme.id}&f=star&${queryString}';">
                <div class="contents_image">
                    <img alt="${title}" onerror="this.src='${ctx}/static/images/default.png'" src="${ctx}/image.action?path=${theme.iconPath}" width="72" height="72" style="margin: 3px;">
                </div>
                <div class="contents_txt">
                    <div style="margin-top: 10px;">
                        <span class="title">${title}</span>
                        <p>${shortDescription}</p>
                    </div>
                </div>
            </div>
        </s:iterator>
    </div>

</form>
<script src="${ctx}/static/zepto/zepto.min.js"></script>

<script>
    $(function(){

        $(".contents_info").click(function(){
            $(this).css("backgroundColor","#e7e6c8");
        });

    });
</script>
</body>
</html>