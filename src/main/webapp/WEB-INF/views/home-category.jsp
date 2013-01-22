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
	<title>商店分类</title>
		
	<link rel="stylesheet" href="${ctx}/css/style.css" media="screen"/>
  	<link rel="stylesheet" href="${ctx}/css/top.css" media="screen"/>
  	<link rel="stylesheet" href="${ctx}/css/reset.css" media="screen"/>
  	<style>
  		.preview img{
			width: 48.5%;
			margin-left:3px;
		}
		.preview{
			font-size: 0;
		}
  	</style>
 </head>
 <body>
 	<div class="slide_list_dot">
		<a href="home.action?${queryString}"><s:text name="nav.hot"/></a><a href="home!newest.action?${queryString}"><s:text name="nav.newest"/></a><a class="selected"><s:text name="nav.category"/></a>
	</div>
	
	<div class="preview">
		<s:iterator value="categories">
			<a href="${ctx}/home!more.action?cid=${id}&${queryString}">
				<img alt="${name}" src="${ctx}/image.action?path=${icon}">
			</a>
		</s:iterator>
	</div>		
 </body>
 </html>