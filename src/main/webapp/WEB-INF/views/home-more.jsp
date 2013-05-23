<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<% response.setHeader("remember", "true"); %>
<!DOCTYPE HTML>
<html lang="en">
	<head>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
		<title>Fun主题-商店更多</title>
        <%--<link rel="stylesheet" href="${ctx}/static/styles/site.min.css?v=1.0"/>--%>
        <link rel="stylesheet" href="${ctx}/static/styles/siteV2.0.css"/>
        <style>
            .details a{
                width: 33.3%;
            }
            <%--.go-top{--%>
                <%--float: right;--%>
                <%--background: url(${ctx}/static/images/2.0/go-top.png) center no-repeat;--%>
                <%--/*background-size: 20px;*/--%>
                <%--width: 30px;--%>
                <%--height: 30px;--%>
            <%--}--%>
            <%--.ft-tool {--%>
                <%--position: relative;--%>
                <%--height: 40px;--%>
                <%--padding: 0 25px;--%>
                <%--/*background: #efefef;*/--%>
                <%--/*border-top: 1px solid #d7d7d7;*/--%>
                <%--line-height: 40px;--%>
                <%--margin-top: 10px;--%>
            <%--}--%>
        </style>
	</head>
	<body>
	
		<form action="home.action" method="get">
            <%--<div class="categoryback">--%>
                <%--<h3><a href="home!category.action?${queryString}"><span style="margin-left: 30px;height: 53px;line-height: 53px;">${categoryName}</span></a></h3>--%>
            <%--</div>--%>
                <nav class="details">
                    <a href="#" id="goToBack"><img src="${ctx}/static/images/2.0/back.png"></a>
                    <a>${categoryName}</a>
                    <a href="home.action"><img src="${ctx}/static/images/2.0/home.png"></a>
                </nav>
			<div id="container">
                <div class="content ajax-wrap">
                    <ul class="v-list J_ajaxWrap">
				<s:iterator value="catePage.result">
					<%--<div class="contents_info" id="content1" onclick="location.href='home!details.action?id=${theme.id}&${queryString}';">--%>
						<%--<div class="contents_image">						--%>
							<%--<img alt="${title}" onerror="this.src='${ctx}/static/images/default.png'" src="${ctx}/files/${theme.iconPath}" width="72" height="72" style="margin: 3px;">--%>
						<%--</div>--%>
						<%--<div class="contents_txt">--%>
							<%--<div style="margin-top: 10px;">--%>
								<%--<span class="title">${title}</span>--%>
								<%--<p>${shortDescription}</p>--%>
							<%--</div>--%>
						<%--</div>--%>
					<%--</div>--%>
                    <%@include file="home-list.jsp"%>
				</s:iterator>
                    </ul>
                    <div class="footer">
                        <div class="J_scrollLoadMore load-btn click-state" data-api="home!categoryMore.action?cid=${param.cid}"></div>
                        <a href="#" class="go-top"></a>
                    </div>
                </div>
			</div>
			<%--<nav id="page-nav">--%>
  				<%--<a href="?catePage.pageNo=${catePage.next}&cid=${categoryId}&${queryString}"></a>--%>
			<%--</nav>--%>
            <%--<div class="ft-tool">--%>
                <%--<a href="#" class="go-top"></a>--%>
            <%--</div>--%>
		</form>
		<%--<script src="${ctx}/static/jquery/1.7.2/jquery.min.js"></script>--%>
        <script src="${ctx}/static/zepto/zepto.min.js"></script>
		<script src="${ctx}/static/zepto/android.js"></script>

  		<script>
		  $(function(){

//              $(".contents_info").click(function(){
//                  $(this).css("backgroundColor","#e7e6c8");
//              });

              $("#goToBack").click(function(){
                  history.back();
                  return false;
              })
		  });
	</script>
	</body>
</html>