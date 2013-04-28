<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<% response.setHeader("remember", "true"); %>
<!DOCTYPE HTML>
<html lang="en">
	<head>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
		<title>Fun主题-商店首页</title>
        <link rel="stylesheet" href="${ctx}/static/styles/site.min.css" media="screen"/>
	</head>
	<body>
	
		<form action="home.action" method="get">
		    <%@include file="home-nav.jsp"%>
			<div id="container">
                <div id="dk_game_slider"></div>
				<s:iterator value="newestPage.result">
					<div class="contents_info" id="content1" onclick="location.href='${ctx}/home!details.action?id=${theme.id}&${queryString}';">			
						<div class="contents_txt">
							<div style="margin-top: 10px;">
								<span class="title">${title}</span>
								<p>${shortDescription}</p>
							</div>
						</div>
						<div class="contents_image">						
							<img style="margin: 3px;" alt="${title}" onerror="${ctx}/static/images/default.png" src="http://locker.uichange.com/UMS/files/${theme.iconPath}" width="72" height="72" class="contents_image_middle">
						</div>
					</div>								
				</s:iterator>
				
				<div class="icon_set">
					<s:iterator value="hottestPage.result">				
						<a href="${ctx}/home!details.action?id=${theme.id}&${queryString}">
							<img alt="${title}" style="padding: 1px;" onerror="${ctx}/static/images/default.png"  src="http://uichange.com/UMS/files/${theme.iconPath}" class="icon" width="72" height="72">
						</a>		
					</s:iterator>
				</div>
                <div class="soft_top">
                    <a href="http://uichange.com/UMS/home/shelf/app?${queryString}">
                        <span><s:text name="home.top"/></span>
                    </a>
                </div>
				<%@include file="/common/footer.jsp" %>

			</div>

		</form>
        <script src="${ctx}/static/zepto/zepto.min.js"></script>
        <script src="${ctx}/static/zepto/gameall.min.js"></script>
		<script>
			$(document).ready(function(){
                $("#home").addClass("selected").removeAttr("href");
                $(".contents_info").click(function(){
                    $(this).css("backgroundColor","#e7e6c8");
                });
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