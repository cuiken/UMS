<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<% response.setHeader("remember", "true"); %>
<!DOCTYPE HTML>
<html lang="en">
	<head>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
		<title>Fun主题-货架</title>
        <link rel="stylesheet" href="${ctx}/static/styles/siteV2.0.css"/>
        <style>

            .nav_second_level{
                background: url(${ctx}/static/images/2.0/game-selected.png) no-repeat;
                background-size: 100% 100%;
            }
            .nav_second_level a{
                padding-top: 4px;
            }
             .app{
                background: url(${ctx}/static/images/2.0/app-selected.png) no-repeat;
                 background-size: 100% 100%;
            }
        </style>
	</head>
	<body>
	
		<form action="home.action" method="get">
            <%@include file="home-details-nav.jsp"%>
			<div id="container">
                <div id="nav_second_level" class="nav_second_level">
                    <a id="male" href="${ctx}/home!shelf.action?sf=game&${queryString}">精品游戏</a>
                    <a id="female" href="${ctx}/home!shelf.action?sf=app&${queryString}">免费软件</a>
                </div>
                <div class="content ajax-wrap">
                    <ul class="v-list J_ajaxWrap">
                        <s:iterator value="newestPage.result">
                            <%@include file="home-app.jsp"%>
                        </s:iterator>
                    </ul>
                    <div class="footer">
                        <div class="J_scrollLoadMore load-btn click-state" data-api="home!shelfJson.action?g=${param.sf}"></div>
                        <a href="#" class="go-top"></a>
                    </div>
                </div>
			</div>

		</form>
		<script src="${ctx}/static/zepto/zepto.min.js"></script>
        <script src="${ctx}/static/zepto/android.js"></script>
  		<script>
		  $(function(){

              var reg=function (name)
              {
                  var reg = new RegExp("(^|\\?|&)"+ name +"=([^&]*)(\\s|&|$)", "i");
                  if (reg.test(location.href)) return decodeURIComponent(RegExp.$2.replace(/\+/g, " ")); return "";
              };

              if(reg("sf")=='game'||location.href.indexOf("game")>0){
                  $("#nav_second_level").addClass("nav_second_level");
              }else{
                  $("#nav_second_level").addClass("app");
              }
			 $("#content1").live("click",function(){ 
				$(this).css("backgroundColor","#e7e6c8");
			});

		  });
	</script>
	</body>
</html>