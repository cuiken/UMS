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
		<title>Fun主题-商店首页</title>
		
		<link rel="stylesheet" href="${ctx}/css/style.css" media="screen"/>
  		<link rel="stylesheet" href="${ctx}/css/top.css" media="screen"/>
  		<link rel="stylesheet" href="${ctx}/css/reset.css" media="screen"/>
	</head>
	<body>
	
		<form action="home.action" method="get">
			<div class="slide_list_dot">
				<a class="selected"><s:text name="nav.hot"/></a><a href="home!newest.action?${queryString}"><s:text name="nav.newest"/></a><a href="home!category.action?${queryString}"><s:text name="nav.category"/></a>
			</div>
			<div id="container"> 	
			<!--  	
				<s:if test="adFile!=null">		
				 <div class="imgCenter">
					<s:if test="adFile!=null">
						<a href="${ctx}/home!details.action?id=${adFile.id}&${queryString}">
							<img alt="${adFile.title}" src="${ctx}/image.action?path=${adFile.adPath}" class="max-width_100">
						</a>
					</s:if><s:else>商店无内容</s:else>
				</div>
			 	</s:if>
			--> 	
				<div class="imgCenter">
					<a href="${ctx}/home!love.action?${queryString}">
						<img alt="love" src="${ctx}/static/images/love/temp-love.jpg" class="max-width_100">
					</a>
				</div>
				<s:iterator value="newestPage.result">
					<div class="contents_info" id="content1" onclick="location.href='${ctx}/home!details.action?id=${theme.id}&${queryString}';">			
						<div class="contents_txt">
							<div style="margin-top: 10px;">
								<font color="#666666">${title}</font>
								<p><font color="#aeaea6">${shortDescription}</font></p>
							</div>
						</div>
						<div class="contents_image">						
							<img style="margin: 3px;" alt="${title}" onerror="${ctx}/static/images/default.png" src="${ctx}/image.action?path=${theme.iconPath}" width="72" height="72" class="contents_image_middle">						
						</div>
					</div>								
				</s:iterator>
				
				<div class="icon_set">
					<s:iterator value="hottestPage.result">				
						<a href="${ctx}/home!details.action?id=${theme.id}&${queryString}">
							<img alt="${title}" style="padding: 1px;" onerror="${ctx}/static/images/default.png"  src="${ctx}/image.action?path=${theme.iconPath}" class="icon" width="72" height="72">
						</a>		
					</s:iterator>
				</div>
				<div style="font-size: 85%;">
					<%@include file="/common/footer.jsp" %>
				 </div>
			</div>

		</form>
		<script src="${ctx}/static/jquery/1.7.2/jquery.min.js"></script>
		
		<script>
			$(document).ready(function(){
				
				$("#content1").live("click",function(){ 
					$(this).css("backgroundColor","#e7e6c8");
				});
			});
		</script>
	</body>
</html>