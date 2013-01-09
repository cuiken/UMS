<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>

<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>更多导航</title>
<link href="${ctx}/css/nav/nav.min.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.2.0/jquery.mobile-1.2.0.min.css" />
<script src="http://code.jquery.com/jquery-1.7.2.min.js"></script>
<script src="http://code.jquery.com/mobile/1.2.0/jquery.mobile-1.2.0.min.js"></script>
<script>
	$(function(){
		$("li").click(function() {
			var nav=$(this);
			$.ajax({
				type : "POST",
				url : "${ctx}/nav/nav-homepage!logClick.action",
				dataType : "text",
				data : {id : nav.children(0).attr("data-id")},
			}).done(function(){
				location.href = nav.children(0).attr("data-orilink");
			});
			
		});
		var moudle = function(id, color) {
			var content = id;
			var bbstyle = "1px solid " + color;
			$(content + " .square").css("background", color);
			$(content + " .tag").css("border-bottom", bbstyle);
			$(content + " h4").css("color", color);
			$(content + " small").css("color", "#FFF");
			$(content + " .ui-btn-up-c").css("color", "#FFF");
			$(content + " .ui-btn-up-c").css("background", color);
		}
		//moudle("#news","#25aae1");
		//moudle("#travel", "#f68500");
		//moudle("#shop", "#e3519d");
		//moudle("#more", "#923aff");
		//moudle("#read", "#cdc000");
	});
</script>
<style>

.ui-btn-hover-c {
    background: linear-gradient(#F6F6F6, #E0E0E0) repeat scroll 0 0 #DFDFDF;
    border: 1px solid #BBBBBB;
    color: #222222;
    font-weight: bold;
    text-shadow: 0 0 0 #FFFFFF;
}

.ui-body-d .ui-link {
    color: black;
    font-weight: normal;
}

a:HOVER{
	text-decoration: none;
}

body {
    background-color: #FFFFFF;
    color: #333333;
    font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
    font-size: 14px;
    line-height: 30px;
	margin: 10px;
}

h3 small {
	font-size: 15px;
	
}

.tag {
	margin-top: 10px;
	border-bottom: 1px solid #25aae1;
	height: 30px;
}

.app-title {
	font-size: 18px;
	background: #25aae1;
	margin: 0 auto;
	margin-top: 10px;
	padding: 5px 10px;
	color: white;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
	text-shadow: 1px 1px 2px #333;
	-webkit-box-shadow: inset 0 0 50px rgba(0, 0, 0, 0.1);
	padding: 5px 10px;
}

.square {
	width: 10px;
	height: 10px;
	float: left;
	margin: 5px;
	background: #25aae1;
}

.rectangle {
	width: 5px;
	height: 25px;
	background: #FFF;
	margin-right: 10px;
	float: left;
}

.triangle-down {
	width: 0;
	height: 0;
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
	border-top: 20px solid #FFF;
	float: right;
	margin: 5px;
}

.triangle-left {
	width: 0;
	height: 0;
	border-top: 10px solid transparent;
	border-right: 20px solid #FFF;
	border-bottom: 10px solid transparent;
	margin: 5px;
	float: right;
}

.navitem {
	float: left;
	width:24.7% !important;
	height: 40px;
	margin: 1px 0 0 1px;
	text-align: center;
	-webkit-box-sizing: border-box;
	border-right: 1px dotted #E2E4E8;
	border-bottom: 1px dotted #E2E4E8;
	line-height: 40px;
	overflow: hidden;
	
}

.shop-color {
	color: #e3519d;
}

.shop-background {
	background: #e3519d;
}

.ui-collapsible-content {

    padding: 10px 0px;
 }
 
 
.ui-content {
    border-width: 0;
    overflow-x: hidden;
    overflow-y: visible;
    padding: 5px;;
}

.ui-body-c, .ui-overlay-c {
    color: #333333;
    text-shadow: 0 0 0 #FFFFFF;
}

.ui-btn-up-c {
    background: linear-gradient(#FFFFFF, #F1F1F1) repeat scroll 0 0 #EEEEEE;
    border: 1px solid #CCCCCC;
    color: #222222;
    font-weight: normal;
    text-shadow: 0 0 0 #FFF;
}
</style>
</head>
<body>
	<div data-role="page" id="home">
		<!-- <div data-role="header" class="ui-bar-b">
			<h1 class="ui-title">更多导航</h1>
			<a href="#" data-icon="home" data-iconpos="notext" title="Home"></a>
		</div> -->
		<div data-role="content">
			<div data-role="collapsible-set" data-iconpos="right" data-collapsed-icon="arrow-r" data-expanded-icon="arrow-d">
				<s:iterator value="boards">
					<div id="${value}" data-role="collapsible" data-collapsed="false" data-theme="c" data-content-theme="d">
						<h3>${name} / <small>${description}</small></h3>
						<ul class="ui-grid-c" style="padding: 0px;margin: 0px;">
							<s:iterator value="navigators">
								<li class="navitem"><a data-orilink="${navAddr}"
									data-id="${uuid}">${name}</a></li>
							</s:iterator>
						</ul>
						<s:iterator value="tags">
							<div class="tag">
								<div class="square"></div>		
								<h4 class="tag-title">${name} / <small>${description}</small></h4>
							</div>
							<ul class="ui-grid-c" style="padding: 0px;margin: 0px;">
								<s:iterator value="navigators">							
									<li class="navitem"><a data-orilink="${navAddr}"
									data-id="${uuid}">${name}</a></li>				
								</s:iterator>
							</ul>
						</s:iterator>
					</div>
				</s:iterator>
				
				<!--  
				<div data-role="collapsible" data-collapsed="false">
					<h3>新闻 / <small>娱乐、体育、财经等</small></h3>
					<div class="tag">
						<div class="square"></div>		
						<h4 class="tag-title">娱乐资讯 / <small>明星、影视、剧集及更多</small></h4>
					</div>
					<ul class="ui-grid-c" style="padding: 0px;margin: 0px;">
						<li class="navitem">新浪娱乐</li>
						<li class="navitem">新浪娱乐</li>
						<li class="navitem">新浪娱乐</li>
						<li class="navitem">新浪娱乐</li>

						<li class="navitem">新浪娱乐</li>
						<li class="navitem">新浪娱乐</li>
						<li class="navitem">新浪娱乐</li>
					</ul>
					
				</div>

				<div data-role="collapsible">
					<h3>出行 / <small>地图、票务、天气及更多</small></h3>
					<p>I'm the collapsible set content for section B.</p>
				</div>
				<div data-role="collapsible">
					<h3>购物 / <small>网购、团购、打折资讯</small></h3>
					<p>I'm the collapsible set content for section B.</p>
				</div>

				<div data-role="collapsible">
					<h3>阅读 / <small> 小说、漫画、大合辑</small></h3>
					<p>I'm the collapsible set content for section B.</p>
				</div>
				<div data-role="collapsible">
					<h3>其他 / <small> 游戏、音 乐、影视资讯</small></h3>
					<p>I'm the collapsible set content for section B.</p>
				</div>

-->
			</div>

		</div>
	</div>
</body>

</html>