<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<% response.setHeader("remember", "true"); %>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
    <title>Fun主题-星座专题</title>
    <link rel="stylesheet" href="${ctx}/static/styles/site.min.css?v=1.0" media="screen"/>
</head>
<body>

<form action="home.action" method="get">
    <div class="categoryback">
        <h3><a href="home!category.action?${queryString}"><span style="margin-left: 30px;height: 53px;line-height: 53px;">${categoryName}</span></a></h3>
    </div>
    <div id="container">
        <s:iterator value="newestPage.result">
            <div class="contents_info" id="content1" onclick="location.href='home!details.action?id=${theme.id}&f=star&${queryString}';">
                <div class="contents_image">
                    <img alt="${title}" onerror="this.src='${ctx}/static/images/default.png'" src="${ctx}/files/${theme.iconPath}" width="72" height="72" style="margin: 3px;">
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