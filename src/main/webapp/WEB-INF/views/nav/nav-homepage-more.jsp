<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en" manifest="../ums.appcache">
<head>
<title>导航更多</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${ctx}/static/bootstrap/2.1.1/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/css/nav/nav.min.css" type="text/css" rel="stylesheet" />
<style>
a {
	height:40px;
	color: black;
	display: block;
}
a:hover {
	background-color: #DDD;
}

body {
	margin: 10px;
}

h3 small {
	font-size: 15px;
	color: white;
}

h4 small {
	color: #25aae1;
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

.tag-title {
	color: #25aae1;
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
	height: 23px;
	background: #FFF;
	margin-right: 10px;
	margin-top:8px;
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
	margin-top:10px;
}

.triangle-left {
	width: 0;
	height: 0;
	border-top: 10px solid transparent;
	border-right: 20px solid #FFF;
	border-bottom: 10px solid transparent;
	margin: 5px;
	margin-top:10px;
	float: right;
}

.navitem {
	float: left;
	width: 24.7%;
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
</style>
</head>
<body id="accordion">


	<s:iterator value="boards">
		<section id="${value}">
			<h3 class="app-title" data-target="#${value}content"
				data-toggle="collapse" data-parent="#accordion" style="background-color: ${color};">
				<span class="rectangle"></span>
				${name} / <small>${description}</small>
				<span class="triangle-down"></span>
			</h3>

			<div id="${value}content" class="in">

				<ul class="thumbnails" style="margin-top: 10px;">
					<s:iterator value="navigators">
					<s:if test="status=='enabled'">
						<li class="navitem"><a data-orilink="${navAddr}"
							data-id="${uuid}">${name}</a></li>
					</s:if>		
					</s:iterator>
				</ul>

				<s:iterator value="tags">
					<div id="${value}">
						<div class="tag" style="border-bottom: 1px solid ${color};">
							<span class="square" style="background-color:${color};"></span>
							<h4 class="tag-title" style="color: ${color};">
								${name} / <small style="color:${color}">${description}</small>
							</h4>
						</div>

						<ul class="thumbnails">
							<s:iterator value="navigators">
							<s:if test="status=='enabled'">
								<li class="navitem"><a data-orilink="${navAddr}"
									data-id="${uuid}">${name}</a></li>
							</s:if>		
							</s:iterator>
						</ul>

					</div>
				</s:iterator>
			</div>
		</section>
	</s:iterator>
	<script src="${ctx}/static/jquery/1.7.2/jquery.min.js"></script>
	
	<script>
		//	document.getElementById("travel_header").scrollIntoView();

		$(function() {
			$("li").click(function() {
				var nav=$(this);
				try{
					TPNavigator.clickButton(nav.children(0).attr("data-id"));
				}catch(e){
					//alert(e);
				}
				
				$.ajax({
					type : "POST",
					url : "${ctx}/nav/nav-homepage!logClick.action",
					dataType : "text",
					data : {id : nav.children(0).attr("data-id")},
				}).done(function(){
					location.href = nav.children(0).attr("data-orilink");
				});
				return false;
			});
			/*
			$(".in").hide();
			$(".app-title").toggle(function(){			
				var $this=$(this);
				var $tr = $($this.children()[2]);		
				$tr.attr("class", "triangle-down");
				var cid=$this.next();
				if(cid.length>=1){
					var content=$(cid).get(0);
					$(content).show();
				}
			},function(){
					
				var $this=$(this);
				var $tr = $($this.children()[2]);	
				$tr.attr("class", "triangle-left");
				
				var cid=$this.next();
				if(cid.length>=1){
					var content=$(cid).get(0);
					$(content).hide();
				}
			});
			*/
		//	var lh=location.hash;
		
			/*
			var moudle = function(id, color) {
				var content = id + "content";
				var bbstyle = "1px solid " + color;
				$(id + " h3").css("background", color);
				$(content + " .square").css("background", color);
				$(content + " .tag").css("border-bottom", bbstyle);
				$(content + " h4").css("color", color);
				$(content + " small").css("color", color);
			}
			moudle("#travel", "#f68500");
			moudle("#shop", "#e3519d");
			moudle("#more", "#923aff");
			moudle("#read", "#cdc000");
*/
		});
	</script>

</body>
</html>