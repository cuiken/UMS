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
	<title>解锁模板</title>
		
	<link rel="stylesheet" href="${ctx}/css/style.css" media="screen"/>
  	<link rel="stylesheet" href="${ctx}/css/top.css" media="screen"/>
  	<link rel="stylesheet" href="${ctx}/css/reset.css" media="screen"/>
 </head>
 <body>
 	<form action="home!newest.action" method="get">
 		<div class="slide_list_dot">
			<a href="home.action?${queryString}"><s:text name="nav.hot"/></a><a class="selected"><s:text name="nav.diy"/></a><a href="home!category.action?${queryString}"><s:text name="nav.category"/></a>
		</div>
 		<div id="container" class="transitions-enabled infinite-scroll clearfix"> 
				<s:iterator value="newestPage.result">
					<div class="contents_info" id="content1" onclick="location.href='home!details.action?id=${theme.id}&${queryString}';">
						<div class="contents_image">						
							<img alt="${title}" onerror="this.src='${ctx}/static/images/default.png'" src="${ctx}/image.action?path=${theme.iconPath}" width="72" height="72" style="margin: 3px;">							
						</div>
						<div class="contents_txt">
							<div style="margin-top: 10px;">
								<font color="#666666">${title}</font>
								<p><font color="#aeaea6">${shortDescription}</font></p>
							</div>
						</div>
					</div>
				</s:iterator>
			</div>
			<nav id="page-nav">
  				<a href="?newestPage.pageNo=${newestPage.nextPage}"></a>
			</nav>	
 	
 	</form>
 	<script src="${ctx}/static/jquery/1.7.2/jquery.min.js"></script>
	<script src="${ctx}/js/jquery/jquery.masonry.min.js"></script>
	<script src="${ctx}/js/jquery/jquery.infinitescroll.min.js"></script>
	<script>
	 $(function(){
		 $(".contents_info").click(function(){ 
				$(this).css("backgroundColor","#e7e6c8");				
		});

	    var $container = $('#container');
	    
	    $container.imagesLoaded(function(){
	      $container.masonry({
	        itemSelector: '.contents_info',
	        columnWidth: 100
	      });
	    });
	    
	    $container.infinitescroll({
	      navSelector  : '#page-nav',    
	      nextSelector : '#page-nav a',  
	      itemSelector : '.contents_info',     
	      loading: {
	         	finishedMsg:"<s:text name='home.finishedMsg'/>",
	         	msgText: "<s:text name='home.msgText'/>",
	        	img: '${ctx}/static/images/loading.gif'
	        }
	      },
	     
	      function( newElements ) {  
	        var $newElems = $( newElements ).css({ opacity: 0 });    
	        $newElems.imagesLoaded(function(){        
	          $newElems.animate({ opacity: 1 });
	          $container.masonry( 'appended', $newElems, true ); 
	        });
	      }
	    );
	  
	  });
	</script>
 </body>
 </html>