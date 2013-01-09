<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
<title>导航首页</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${ctx}/static/bootstrap/2.1.1/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/css/nav/nav.min.css" type="text/css" rel="stylesheet" />
<style>
	input{
		background-color: #FAFFBD;
		background-image: none;
		color: black;
		margin-bottom: 0;
	}
	form{
		margin:0;
	}
	/*
	.thumbnail img {
    	-webkit-animation: show 2s linear;
	       -moz-animation: show 0.5s ease-in;
	        -ms-animation: show 0.5s ease-in;
	         -o-animation: show 0.5s ease-in;
	            animation: show 0.5s ease-in;
	    -webkit-animation-duration :2s;    
	   /* -webkit-animation-delay : 1s;    */   
      }
    */
    .play{
	    -webkit-animation: show 2s linear;
		       -moz-animation: show 0.5s ease-in;
		        -ms-animation: show 0.5s ease-in;
		         -o-animation: show 0.5s ease-in;
		            animation: show 0.5s ease-in;
		    -webkit-animation-duration :2s;    
		   /* -webkit-animation-delay : 1s;       */
    }  

	@-webkit-keyframes show{
	
	  0% { 
	    -webkit-transform: scale(0.8); 
	            transform: scale(0.8); 
	  }
	  
	  50% { 
	    -webkit-transform: scale(1); 
	            transform: scale(1); 
	  }
	  
	  75% { 
	    -webkit-transform: scale(1.02);
	            transform: scale(1.02);
	  }
	  
	  100% { 
	    -webkit-transform: scale(1);
	            transform: scale(1);
	  }
	} 
</style>
</head>
<body>
	<header style="margin-top:10px;">
	<form id="fsearch" action="${ctx}/nav/search-log!search.action" method="post">
		<ul style="margin:1px;">
			<li style="width:15%;display:table-cell;">
				<img src="${ctx}/static/images/nav/zhidao.png" style="margin-bottom: 5px;" id="zhidao">
			</li>	
			<li style="width:70%;display:table-cell;text-align:center;vertical-align: middle;">
				
				<input type="search" id="keyword" name="word" placeholder="左知道，右百度">
			</li>
			<li style="width:15%;display:table-cell;">
				<img src="${ctx}/static/images/nav/baidu.png" style="margin-bottom: 5px;" id="isearch">
			</li>
		</ul>
		<input type="hidden" name="type" id="type" >
	</form>	
	</header>
	<section id="thumbnails">
		<div id="top">
			<ul class="thumbnails">
				<s:if test="tops.template==1">
					<%@include file="/common/nav-top/nav-top-temp.jsp"%>
				</s:if>
				<s:if test="tops.template==2">
					<%@include file="/common/nav-top/nav-top-temp2.jsp"%>
				</s:if>
				<s:if test="tops.template==3">
					<%@include file="/common/nav-top/nav-top-temp3.jsp"%>
				</s:if>
				<s:if test="tops.template==4">
					<%@include file="/common/nav-top/nav-top-temp4.jsp"%>
				</s:if>
			</ul>
		</div>
		<div id="center">
			<ul class="thumbnails">
				<s:if test="centerLeft.template==1">
					<%@include file="/common/nav-cl/nav-temp1.jsp"%>
				</s:if>
				<s:if test="centerLeft.template==2">
					<%@include file="/common/nav-cl/nav-temp2.jsp"%>
				</s:if>
				<s:if test="centerLeft.template==3">
					<%@include file="/common/nav-cl/nav-temp3-top1.jsp"%>
				</s:if>
				<s:if test="centerLeft.template==4">
					<%@include file="/common/nav-cl/nav-temp4.jsp"%>
				</s:if>
				
				<s:if test="centerRight.template==1">
					<%@include file="/common/nav-cr/nav-temp1.jsp"%>
				</s:if>
				<s:if test="centerRight.template==2">
					<%@include file="/common/nav-cr/nav-temp2.jsp"%>
				</s:if>
				<s:if test="centerRight.template==3">
					<%@include file="/common/nav-cr/nav-temp3-top1.jsp"%>
				</s:if>
				<s:if test="centerRight.template==4">
					<%@include file="/common/nav-cr/nav-temp4.jsp"%>
				</s:if>
			</ul>
		</div>
		<div id="bottom">
			<ul class="thumbnails">
				<%@include file="/common/nav-bottom/nav-temp4.jsp"%>
				
				<%@include file="/common/nav-bottom/nav-temp3-top2.jsp"%>
			</ul>
		</div>
	</section>
	<script src="${ctx}/static/jquery/1.7.2/jquery.min.js"></script>
	<script src="${ctx}/static/bootstrap/2.1.1/js/bootstrap.min.js" type="text/javascript"></script>
	<script>
		function playA(){
			$(".thumbnail img").addClass("play");
		}
		
		$(function(){
			$("#isearch").click(function(){
				$("#type").val("baidu");
				$("#fsearch").submit();
			});
			$("#zhidao").click(function(){
				$("#type").val("zhidao");
				$("#fsearch").submit();
			});
			
			$("img").addClass("show");
			
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
		})
		
	</script>
</body>
</html>