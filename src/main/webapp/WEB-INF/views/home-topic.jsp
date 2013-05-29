<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html lang="zh">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
    <title>Fun主题-专题</title>
    <link rel="stylesheet" href="${ctx}/static/styles/siteV2.0.css"/>
  </head>
    <body>
    <%@include file="home-nav.jsp"%>
    <div id="container">
        <div class="topic-nav">
            <a href="${ctx}/home!cate.action?g=male&${queryString}"><i class="male"></i>男生专区</a>
            <a href="${ctx}/home!cate.action?g=female&${queryString}" class="female"><i></i>女生专区</a>
        </div>

           <div class="">
                <ul class="topic-content">
                    <s:iterator value="topics">
                        <li onclick="location.href='home!topicList.action?topicId=${id}'">
                            <div class="topic-icon topic-preview">
                                <img alt="${name}" src="${ctx}/image.action?path=${icon}">
                            </div>
                            <div class="topic-des">
                                ${description}
                            </div>
                        </li>
                    </s:iterator>
                </ul>

        </div>
    </div>
    <script src="${ctx}/static/zepto/zepto.min.js"></script>
    <script>
        $(function(){
            $("#topic").addClass("selected").removeAttr("href");
        })
    </script>
    </body>
</html>