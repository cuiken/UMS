<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>

<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
	<title>商店分类</title>

    <link rel="stylesheet" href="${ctx}/static/styles/siteV2.0.css"/>
  	<style>
  		.category-preview img{
			width: 48.5%;
			margin-left:3px;
		}
		.category-preview{
			font-size: 0;
            margin-top: 10px;
		}
  	</style>
 </head>
 <body>
    <%@include file="home-nav.jsp"%>
	<div class="category-preview">
		<s:iterator value="categories">
			<a href="${ctx}/home!more.action?cid=${id}&${queryString}">
				<img alt="${name}" src="${ctx}/image.action?path=${icon}">
			</a>
		</s:iterator>

	</div>
    <script src="${ctx}/static/zepto/zepto.min.js"></script>
     <script>
         $(function(){
             $("#category").addClass("selected").removeAttr("href");
         })
     </script>
 </body>
 </html>