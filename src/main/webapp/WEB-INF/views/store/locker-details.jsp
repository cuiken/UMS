<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<% response.setHeader("remember", "true"); %>
<!DOCTYPE HTML>
<html>
	<head>
	 	<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=2.0; user-scalable=0;">
	 	<meta name="apple-mobile-web-app-capable" content="yes"> 
	  	<meta name="apple-mobile-web-app-status-bar-style" content="black">  
	  	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  		<!--[if lt IE 9]><script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
		<title>Locker Details</title>
  		<link rel="stylesheet" href="${ctx}/css/details.css" media="screen"/>
  		<link rel="stylesheet" href="${ctx}/css/style.css" media="screen"/>
  		<style>
  			img{
				border: 0;
			}
			body{
				margin: 0;
				padding: 0;
				font-size: 100%;
			}
			a{
				text-decoration: none;
			}
  		</style>		
	</head>
	<body>
	
		<form action="locker!details.action" method="get">
			<div> 
				<div class="title_bar">
					${info.title}
				</div>
				<div class="contents_img">
					<img alt="${info.title}" src="${ctx}/image.action?path=${info.theme.preWebPath}" width="180" height="300">
				</div>
				<div class="short_des">
					<p>${info.shortDescription}</p>
				</div>
				<div align="center">
					<a href="#" id="downfree">
						<img id="download" alt="download" src="${ctx}/static/images/dt.png">
					</a>
				</div>
				<div  class="contents_txt">
					<div style="float: left; width: 50%; margin-bottom: 15px;margin-top: 15px;">
						<s:text name="home.author"/> : ${info.author}
					</div>				
					<div style="float:right;  width: 50%;margin-bottom: 15px;margin-top: 15px;">
						<s:text name="home.size"/> : ${fn:substring(info.theme.apkSize/1024/1024, 0, 4)}M
						
					</div>
				</div>
				<div class="contents_txt" id="desc" style="line-height: 130%;">
					<s:text name="home.desc"/> : ${info.longDescription}					
				</div>		
				<div style="float: right;margin: 15px;">
					<a href="${ctx}/store/locker.action?${queryString}"><img id="gohome" alt="gohome" src="${ctx}/static/images/dhome.png"></a>
				</div>	
				<div class="category" style="background: #FFF;">
					<div class="title_bar">
						
					</div>
				</div>
				
				<%@include file="/common/footer.jsp" %>

			</div>

		</form>
		<script src="${ctx}/static/jquery/1.7.2/jquery.min.js"></script>

		<script>
			$(function() {
			
				if('${language}'=='zh'){
					$("#download").attr("src","${ctx}/static/images/dt.png");
					$("#gohome").attr("src","${ctx}/static/images/dhome.png");
					$("#more").attr("src","${ctx}/static/images/more.png");
				}else{
					$("#download").attr("src","${ctx}/static/images/en/dt.png");
					$("#gohome").attr("src","${ctx}/static/images/en/dhome.png");
					$("#more").attr("src","${ctx}/static/images/en/more.png");
				}
				$("#downfree").click(function(){
					$.ajax({
						type:"POST",
						url:"log/log!saveDownload.action?id=${info.theme.id}&${queryString}",
						dataType:"text",
						data:{queryString:'${info.theme.downloadURL}',cs:'${queryString}'}
					});
					location.href='${info.theme.downloadURL}';
					
				});
				
				var golocation=function(thiss){
					var id=$(thiss).attr("data-id");
					location.href="${ctx}/store/locker!render.action?${queryString}&id="+id;
				}
				
				$(".contents_info").click(function(){ 
					$(this).css("backgroundColor","#e7e6c8");
					golocation(this);
				});
				$(".categoryResult").click(function(){
					golocation(this);
				});
	        })

		</script>
	</body>
</html>