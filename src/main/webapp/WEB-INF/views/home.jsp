<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<% response.setHeader("remember", "true"); %>
<!DOCTYPE HTML>
<html lang="zh">
	<head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
		<title>Fun主题-商店首页</title>
        <link rel="stylesheet" href="${ctx}/static/styles/site.min.css"/>
        <link rel="stylesheet" href="${ctx}/static/styles/siteV2.0.css"/>
        <style>
            .nav_second_level{
                background: url(${ctx}/static/images/2.0/female-selected.png);
            }
            .nav_second_level_male{
                background: url(${ctx}/static/images/2.0/male-selected.png);
            }
            .female_default{
                color: #6f6e6e;
            }
            .male_selected{
                color:#1d9efe;
            }

            .female_selected{
                color: #ed3d41;
            }
        </style>
	</head>
	<body>
	
		<form action="home.action" method="get">
		    <%@include file="home-nav.jsp"%>
			<div id="container">
                <div id="dk_game_slider"></div>
                <div id="nav_second_level" class="nav_second_level">
                    <span id="male">男生专区</span>
                    <span id="female">女生专区</span>
                </div>
                <div class="content">
                <ul class="v-list">
				<s:iterator value="newestPage.result">

                    <%@include file="home-list.jsp"%>
				</s:iterator>
                </ul>
                </div>


			</div>

		</form>
        <script src="${ctx}/static/zepto/zepto.min.js"></script>
        <script src="${ctx}/static/zepto/gameall.min.js"></script>
		<script>
			$(document).ready(function(){
                $("#home").addClass("selected").removeAttr("href");
                $("#female").addClass("female_selected");
                $("#male").addClass("female_default");
                $("#female").click(function(){

                    $(this).addClass("female_selected");
                    $("#male").removeClass("male_selected").addClass("female_default");
                    $("#nav_second_level").removeClass("nav_second_level_male");

                });
                $("#male").click(function(){

                    $(this).addClass("male_selected");
                    $("#female").removeClass("female_selected").addClass("female_default");
                    $("#nav_second_level").addClass("nav_second_level_male");
                });
//                $(".contents_info").click(function(){
//                    $(this).css("backgroundColor","#e7e6c8");
//                });
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