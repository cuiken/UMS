<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
	<title>导航详细</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1.0" />
	<link href="${ctx}/static/bootstrap/2.1.1/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/css/nav/nav.min.css" type="text/css" rel="stylesheet" />
	<style>
		body{
			margin: 10px;
		}
		h3 small{
			font-size: 15px;
			color: white;
		}
		h4 small{
			color: #25aae1;
		}

		.tag{
			margin-top: 10px;
			border-bottom: 1px solid #25aae1;
			height: 30px;
		}
		.app-title {
			font-size: 18px;
			background: #25aae1;
			margin: 0 auto;
			padding: 5px 10px;
			color: white;
			-webkit-border-radius: 4px;
			-moz-border-radius: 4px;
			border-radius: 4px;
			text-shadow: 1px 1px 2px #333;
			-webkit-box-shadow: inset 0 0 50px rgba(0, 0, 0, 0.1);
		}

		.tag-title{
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
		    float: left;
		    margin-top:9px;
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
		    float: right;
		}
		



		body .menu_list_wrapper_low {
		  margin-left:5px;
		  margin-right:5px;
		}
		.itemlist {
			margin: 0;
			padding: 0;
		}
		ol, ul, span {
			list-style: none;
		}
		/*.menu_list_wrapper_low ul.itemlist li:nth-child(odd) {
			background: -webkit-gradient(linear, left top, left bottom, from(#FDFFF2), to(#E6F0BB));
			background: -moz-linear-gradient(center top , #FDFFF2, #E6F0BB) repeat scroll 0 0 transparent;
		}*/
		ul.itemlist > li {
			border-bottom: solid 1px #CCC;
			-webkit-box-shadow: 0 1px 0 white;
			-moz-box-shadow: 0 1px 0 #fff;
			box-shadow: 0 1px 0 white;
		}
		ul.itemlist > li.image {
			padding-left: 7px;
		}
		ul.itemlist > li > a {
			display: block;
			color: black;
			text-decoration: none;
			background: url(../static/images/nav/arrow_04_s.png) no-repeat 99% center;
		}

		/*
		a:link, a:active, a:hover, a:visited {
			color: #069;
			-webkit-tap-highlight-color: rgba(0,0,0,0.25);
			font-size: 100%;
			line-height: 100%;
			text-decoration: underline;
			margin: auto;
			padding: 0;
		}
		*/
		ul.itemlist > li > a > .wrapper {
			position: relative;
			display: block;
			padding: 8px 20px 8px 7px;
			min-height: 40px;
			/zoom:1;
		}

		ul.itemlist > li.image > a > .wrapper {
			padding: 0 0 0 42px;
			min-height: 53px;
		}
		ul.itemlist > li > a > .wrapper > .image {
			position: absolute;
			top: 7px;
			left: 0px;
			display: block;
			border:1px solid #fff;
			-moz-border-radius: 5px;
			-webkit-border-radius: 5px;
			border-radius: 5px;
			margin: 0 0 5px;
			width: 36px;
			height: 36px;
		}
		ul.itemlist > li > a > .wrapper > .info {
	display: table-cell;
	width: 100%;
	height: 40px;
	padding: 0;
	vertical-align: middle;
}

ul.itemlist > li.image > a > .wrapper > .info {
	display: block;
	height: auto;
	padding: 8px 0;
	text-indent: -.5em;
	vertical-align: baseline;
}

ul.itemlist > li > a > .wrapper > .info > .title {
	display: table-cell;
	padding-left: 5px;
	/*color: #008800;*/
	font-size: 100%;
	line-height: 1.1;
	vertical-align: middle;
}

ul.itemlist > li.image > a > .wrapper > .info > .title {
	position: relative;
	margin-right: 0;
	padding-left: 6px;
	padding-top: 1px;
	overflow: hidden;
	display: block;
	/*color: #008800;*/
}

ul.itemlist > li > a > .wrapper > .info > .detail {
	display: block;
	margin-top: 8px;
	padding-left: 0;
	font-size: 100%;
	vertical-align: middle;
}

ul.itemlist > li.image > a > .wrapper > .info > .detail {
	margin-top: 0;
	margin-right: 8px;
	padding-top: 6px;
	padding-right: 12px;
	overflow: hidden;
	text-indent: 0em;
	color: #AEAEA6;
}
	</style>
</head>
<body id="accordion">

	<section>
		<s:if test="board!=null">
			<h3 class="app-title" data-target="#newscontent" data-toggle="collapse" data-parent="#accordion" style="background-color: ${board.color};">
			<span class="rectangle"></span>
			${board.name} / <small>${board.description}</small>
			<span class="triangle-down"></span>
			</h3>
			<div class="menu_list_wrapper_low">
			<ul class="itemlist">
				<s:iterator value="board.navigators">	
				<s:if test="status=='enabled'">		
				<li class="lpNormal image">
					<a href="${navAddr}" data-id="${uuid}">
						<span class="wrapper">
							<span class="image">
								<img src="${ctx}/image.action?path=${icon}">
							</span>
							<span class="info">
								<span class="title">${name}</span>
								<span class="detail">${description}</span>
							</span>
						</span>
					</a>
				</li>		
				</s:if>		
				</s:iterator>
			</ul>
			<s:iterator value="board.tags">
				<div class="tag" style="border-bottom: 1px solid ${board.color};">
					<span class="square" style="background-color:${board.color};"></span>		
					<h4 class="tag-title" style="color: ${board.color};">${name} / <small style="color: ${board.color};">${description}</small></h4>
				</div>
				<ul class="itemlist">
					<s:iterator value="navigators">
					<s:if test="status=='enabled'">
					<li class="lpNormal image">
						<a href="${navAddr}" data-id="${uuid}">
							<span class="wrapper">
								<span class="image">
									<img src="${ctx}/image.action?path=${icon}">
								</span>
								<span class="info">
									<span class="title">${name}</span>
									<span class="detail">${description}</span>
								</span>
							</span>
						</a>
					</li>
					</s:if>
				</s:iterator>
				</ul>
			</s:iterator>
		</div>
		</s:if>
		<s:elseif test="tag!=null">
			<h3 class="app-title" data-target="#newscontent" data-toggle="collapse" data-parent="#accordion" style="background-color: ${tag.board.color};">
			<span class="rectangle"></span>
			${tag.name} / <small>${tag.description}</small>
			<span class="triangle-down"></span>
			</h3>
			<div class="menu_list_wrapper_low">
			<ul class="itemlist">
				<s:iterator value="tag.navigators">		
				<s:if test="status=='enabled'">		
					<li class="lpNormal image">
						<a href="${navAddr}" data-id="${uuid}">
							<span class="wrapper">
								<span class="image">
									<img src="${ctx}/image.action?path=${icon}">
								</span>
								<span class="info">
									<span class="title">${name}</span>
									<span class="detail">${description}</span>
								</span>
							</span>
						</a>
					</li>		
					</s:if>		
				</s:iterator>
			</ul>
			</div>
		</s:elseif>					
		
	</section>
	<script src="${ctx}/static/jquery/1.7.2/jquery.min.js"></script>
	<script>
	$("a").click(function() {
		var nav=$(this);
		try{
			TPNavigator.clickButton(nav.attr("data-id"));
		}catch(e){
			//alert(e);
		}
		
		$.ajax({
			type : "POST",
			url : "${ctx}/nav/nav-homepage!logClick.action",
			dataType : "text",
			data : {id : nav.attr("data-id")},
		}).done(function(){
			location.href = nav.attr("href");
		});
		return false;
	});
	</script>
</body>
</html>