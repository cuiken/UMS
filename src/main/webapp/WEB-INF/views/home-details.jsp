<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<% response.setHeader("remember", "true"); %>
<!DOCTYPE HTML>
<html lang="en">
	<head>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
		<title>Fun主题-商店详细</title>
        <link rel="stylesheet" href="${ctx}/static/styles/site.min.css" media="screen"/>
        <style>
            .down{
                font-size: 22px;
                color: #fff;
                background: #EA2B74;
                border-radius: 10px;
                letter-spacing: 7px;
                margin: auto;
                text-align: center;
                display: block;
                width: 207px;
                height: 38px;
                line-height: 38px;
                font-weight: bold;
            }
            .hasDown{
                background:#d6d4d9;
            }

            .contents_info{
                /*display: inline-block;*/
                border-top: 1px solid #e1e0c1;
                border-bottom: 1px solid #e1e0c1;
                margin:15px 0 3px auto;
                width: 98%;
            }

            .contents_image{
                position: relative;
                max-width: 72px;
                max-height: 72px;
            }
            .contents_txt{
                float: left;
                width: 50%;
                margin-left: 20px;
            }

            .g_down{
                display: inline-block;
                margin: 10px auto;
            }
            .contents_image:before{
                position: absolute;
                z-index: 2;
                content: '';
                /*top: -12px;*/
                left: 50%;
                width: 49px;
                height: 32px;
                margin-left: -35px;
                background: url(${ctx}/static/images/recommend.png);
            }
        </style>
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
				<%--<div align="center">--%>

					<%--<img id="download" alt="download" src="${ctx}/static/images/dt.png">--%>
                    <span id="download" class="down"><s:text name="home.download"/></span>
				<%--</div>--%>
				<div  class="long_des">
					<div style="float: left; width: 50%; margin-bottom: 15px;margin-top: 15px;">
						<s:text name="home.author"/>: ${info.author}
					</div>
					<div style="float:right;  width: 50%;margin-bottom: 15px;margin-top: 15px;">
						<s:text name="total.down"/>: ${totalDown}

					</div>
				</div>
				<div class="long_des" id="desc" style="line-height: 130%;">
					<s:text name="home.desc"/>: ${info.longDescription}
				</div>

                <div class="contents_info">
                    <span onclick="location.href='${ctx}/home!details.action?id=${gameInfo.theme.id}&${queryString}';">
                        <span class="contents_image">
                            <img alt="${gameInfo.title}" src="${ctx}/files/${gameInfo.theme.iconPath}">
                        </span>
                        <span class="contents_txt">
                            <span class="title">${gameInfo.title}</span>
                            <p>${gameInfo.shortDescription}</p>
                        </span>
                    </span>
                    <span class="g_down">
                        <img id="g_img_down" src="${ctx}/static/images/g_down.jpg">
                    </span>
                </div>

				<div style="margin-top: 5px;">

                    <a href="${ctx}/home/shelf/game?${queryString}"><img id="like" src="${ctx}/static/images/like.png"></a>
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
        <script src="${ctx}/static/zepto/zepto.min.js"></script>
        <script src="${ctx}/static/zepto/touch.js"></script>
		<script>
			$(function() {
			//	$("img").lazyload();
				if('${language}'=='zh'){
					$("#gohome").attr("src","${ctx}/static/images/dhome.png");
					$("#more").attr("src","${ctx}/static/images/more.png");
				}else{
					$("#gohome").attr("src","${ctx}/static/images/en/dhome.png");
					$("#more").attr("src","${ctx}/static/images/en/more.png");
                    $("#like").attr("src","${ctx}/static/images/en/like_en.png");
				}
				$("#download").tap(function(e){
					$.ajax({
						type:"POST",
						url:"log/log!saveDownload.action?id=${info.theme.id}&${queryString}",
						dataType:"text",
						data:{queryString:'${info.theme.downloadURL}',cs:'${queryString}'},
                        complete:function(){
                            location.href='${info.theme.downloadURL}';
                        }
					});

                    $(this).addClass("down hasDown");

                    e.preventDefault();
                });
                $(".g_down").click(function(e){
                    $.ajax({
                        type:"POST",
                        url:"log/log!saveDownload.action?id=${gameInfo.theme.id}&${queryString}",
                        dataType:"text",
                        data:{queryString:'${gameInfo.theme.downloadURL}',cs:'${queryString}'},
                        complete:function(){
                            location.href='${gameInfo.theme.downloadURL}';
                        }
                    });
                    <%--$("#g_img_down").attr("src","${ctx}/static/images/g_downed.jpg");--%>
                    e.preventDefault();
                });
	        })

		</script>
	</body>
</html>