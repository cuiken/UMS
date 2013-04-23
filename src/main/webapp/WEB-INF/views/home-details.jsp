<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<% response.setHeader("remember", "true"); %>
<!DOCTYPE HTML>
<html lang="en">
	<head>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
		<title>Fun主题-商店详细</title>
        <link rel="stylesheet" href="${ctx}/static/styles/site.min.css" media="screen"/>
	</head>
	<body>

		<form action="home!details.action" method="get">
				<div class="title_bar">
					${info.title}
				</div>
				<div class="preview_img">
					<img alt="${info.title}" src="${ctx}/files/${info.theme.preWebPath}" width="180" height="300">
				</div>
				<div class="short_des">
					<p>${info.shortDescription}</p>
				</div>
				<div align="center">
					<a id="downfree">
						<img id="download" alt="download" src="${ctx}/static/images/dt.png">
					</a>
				</div>
				<div  class="long_des">
					<div style="float: left; width: 60%; margin-bottom: 15px;margin-top: 15px;">
						<s:text name="home.author"/>: ${info.author}
					</div>
					<div style="float:right;  width: 40%;margin-bottom: 15px;margin-top: 15px;">
						<s:text name="total.down"/>: ${totalDown}

					</div>
				</div>
				<div class="long_des" id="desc" style="line-height: 130%;">
					<s:text name="home.desc"/>: ${info.longDescription}
				</div>

				<div style="margin-top: 5px;">

                    <a href="home!shelf.action?sf=game&${queryString}"><img src="${ctx}/static/images/like.png"></a>
					<a style="float: right" href="home.action?${queryString}"><img id="gohome" alt="gohome" src="${ctx}/static/images/dhome.png"></a>
				</div>
				<div class="category" style="margin-top: 20px;">
					<div class="title_bar">
						<s:text name="home.category" >
							<s:param name="category" value="categoryName"/>
						</s:text>
					</div>
					<div class="icon_set">
						<s:iterator value="catePage.result">

							<a href="${ctx}/home!details.action?id=${theme.id}&${queryString}">
								<img alt="${theme.title}" src="${ctx}/files/${theme.iconPath}" onerror="${ctx}/static/images/default.png" style="margin: 2px;" width="72" height="72" class="icon"/>
							</a>

						</s:iterator>

						<a href="${ctx}/home!more.action?cid=${info.theme.categories[0].id}&${queryString}">
							<img id="more" alt="更多" src="${ctx}/static/images/more.png" width="72" height="72" style="margin: 2px;" class="icon" />
						</a>
					</div>
				</div>

				<%@include file="/common/footer.jsp" %>


		</form>
		<script src="${ctx}/static/jquery/1.7.2/jquery.min.js"></script>

		<script>
			$(function() {
			//	$("img").lazyload();
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
					}).done(function(){
						location.href='${info.theme.downloadURL}';
					});
                    if('${language}'=='zh'){
                        $("#download").attr("src","${ctx}/static/images/dt_zh.png");
                    }else{
                        $("#download").attr("src","${ctx}/static/images/en/dt_en.png");
                    }
                    $(this).unbind('click');
                    return false;
                });
	        })

		</script>
	</body>
</html>