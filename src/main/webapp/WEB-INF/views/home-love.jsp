<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html manifest="ums.appcache">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Fun主题-情人节专题</title>
<style>
	body{
			background-repeat: no-repeat;
			background-position: center 0;
			background-image: url("${ctx}/static/images/love/bg.jpg");
			background-size: 100% auto;
			background-color: #f6dce7;
			width: 100%;
			margin: 0;
		}
		.content{
			padding-top: 35%;
			margin-left: 10px;
		}
		.content > ul,.icon_set > ul{
			list-style-type: none;
			padding: 0;
			margin: 0;
		}
		.content > ul > li{
			width: 50%;
			float: left;
			margin: 0;
		}

		.content  > ul > li > a > img,.icon_set > ul > li > a >img{
			max-width: 100%;
		}
		.icon_set > ul > li{
			width: 33%;
			float: left;
			margin: 0;
		}
		.icon_set{
			margin-top: 10px;
			margin-left: 25px;
		}

</style>
</head>
<body>
	<div class="content">
		<ul>
			<li>
				<a href="${ctx}/home!details.action?id=208&f=love&${queryString}">
					<img src="${ctx}/static/images/love/love1.png">
				</a>
			</li>	
			<li>
				<a href="${ctx}/home!details.action?id=211&f=love&${queryString}">
					<img src="${ctx}/static/images/love/love2.png">
				</a>	
			</li>
			<li>
				<a href="${ctx}/home!details.action?id=209&f=love&${queryString}">
					<img src="${ctx}/static/images/love/love3.png">
				</a>	
			</li>
			<li>
				<a href="${ctx}/home!details.action?id=210&f=love&${queryString}">
					<img src="${ctx}/static/images/love/love4.png">
				</a>
			</li>
		</ul>
	</div>
	<div class="icon_set">
		<ul>
			<li>
				<a href="${ctx}/home!details.action?id=206&f=love&${queryString}">
					<img src="${ctx}/static/images/love/icon_1.png">
				</a>
			</li>
			<li>
				<a href="${ctx}/home!details.action?id=207&f=love&${queryString}">
					<img src="${ctx}/static/images/love/icon_2.png">
				</a>
			</li>
			<li>
				<a href="${ctx}/home!details.action?id=179&f=love&${queryString}">
					<img src="${ctx}/static/images/love/icon_3.png">
				</a>	
			</li>
			<li>
				<a href="${ctx}/home!details.action?id=183&f=love&${queryString}">
					<img src="${ctx}/static/images/love/icon_4.png">
				</a>	
			</li>
			<li>
				<a href="${ctx}/home!details.action?id=89&f=love&${queryString}">
					<img src="${ctx}/static/images/love/icon_5.png">
				</a>	
			</li>
			<li>
				<a href="${ctx}/home!details.action?id=204&f=love&${queryString}">
					<img src="${ctx}/static/images/love/icon_6.png">
				</a>	
			</li>
			<li>
				<a href="${ctx}/home!details.action?id=205&f=love&${queryString}">
					<img src="${ctx}/static/images/love/icon_7.png">
				</a>	
			</li>
			<li>
				<a href="${ctx}/home!details.action?id=54&f=love&${queryString}">
					<img src="${ctx}/static/images/love/icon_8.png">
				</a>	
			</li>
			<li>
				<a href="${ctx}/home!details.action?id=52&f=love&${queryString}">
					<img src="${ctx}/static/images/love/icon_9.png">
				</a>	
			</li>
		</ul>

	</div>
	<div style="float: right;margin-top: 10px;">
		<a href="home.action?${queryString}">
			<img src="${ctx}/static/images/love/home.png">	
		</a>
	</div>
</body>
</html>