<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html lang="zh">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
    <title>Fun主题-专题</title>
    <link rel="stylesheet" href="${ctx}/static/styles/siteV2.0.css"/>
    <style>
        .topic-nav{
            display: -webkit-box;
            margin: 10px 20px;
            -webkit-box-pack:justify;
        }
        .topic-nav a{
            display: block;
            height: 40px;
            line-height: 40px;
            width: 40%;
            box-shadow: 0 0 3px #eee;
            text-align: center;
            color: #0c97ff;
            border-radius: 4px;
            background: -webkit-linear-gradient(top,#e8f8fd,#b5e4f5);
            -webkit-box-pack:center;
        }
        .topic-nav .female{
            color: #ec3438;
            background: -webkit-linear-gradient(top,#fde8eb,#f5b5d0);
        }
        .topic-preview{
            border: 3px solid #fff;
            box-shadow: 0 0 3px #ccc;
        }
        .topic-icon{
            height: 90%;
            width: 60%;
            float: left;
            /*padding: 5px 0;*/

        }
        .topic-icon img{
            width: 100%;
            height: 100%;
        }
        .topic-des{
            width: 30%;
            float: left;
            margin: 5px 0 10px;
            padding: 10px;

        }
        .topic-content li{
            height: 80px;
            border-bottom: 1px solid #eee;
        }
    </style>
  </head>
    <body>
    <%@include file="home-nav.jsp"%>
    <div id="container">
        <div class="topic-nav">
            <a href="${ctx}/home!cate.action?g=male&${queryString}">男生专区</a>
            <a href="${ctx}/home!cate.action?g=female&${queryString}" class="female">女生专区</a>
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