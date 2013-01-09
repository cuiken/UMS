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
		<title>Locker</title>
		<link rel="stylesheet" href="${ctx}/css/style.css" media="screen"/>
  		<link rel="stylesheet" href="${ctx}/css/top.css" media="screen"/>
  		<link rel="stylesheet" href="${ctx}/css/reset.css" media="screen"/>
  		<style>
	  		a{
				text-decoration: none;
			}
			.content-title{
				color:#666;
			}
			.contents_txt >span{
				color:#aeaea6;
				padding-left: 10px;
				font-size: 80%;
			}
  		</style>
	</head>
	<body>
	
		<form action="locker.action" method="get">
			<div id="container"> 	
				<s:if test="adFile!=null">		
					 <div class="imgCenter">
						<s:if test="adFile!=null">
							<a href="#" data-id="${adFile.id}">
								<img alt="${adFile.title}" src="${ctx}/image.action?path=${adFile.adPath}" class="max-width_100">
							</a>
						</s:if><s:else>商店无内容</s:else>
					</div>
				 </s:if>					
				<s:iterator value="newestPage.result">
					<div class="contents_info" data-id="${theme.id}" >			
						<div class="contents_txt">
							<div class="content-title">
								<span>${title}</span>					
							</div>
							<span>${shortDescription}</span>
						</div>
						<div class="contents_image">						
							<img style="margin: 3px;" alt="${title}" src="${ctx}/image.action?path=${theme.iconPath}" width="72" height="72" class="contents_image_middle">						
						</div>
					</div>								
				</s:iterator>
				
				<div class="icon_set">
					<s:iterator value="hottestPage.result">				
						<a href="#" data-id="${theme.id}">
							<img alt="${title}" style="padding: 1px;" src="${ctx}/image.action?path=${theme.iconPath}" class="icon" width="72" height="72">
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
				
				var golocation=function(thiss){
					var id=$(thiss).attr("data-id");
					location.href="${ctx}/store/locker!render.action?${queryString}&id="+id;
				}
				
				$(".contents_info").click(function(){ 
					$(this).css("backgroundColor","#e7e6c8");
					golocation(this);
				});
				$("a").click(function(){
					golocation(this);
				});
			});
		</script>
	</body>
</html>