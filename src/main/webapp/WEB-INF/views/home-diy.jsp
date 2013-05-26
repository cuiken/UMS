<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<% response.setHeader("remember", "true"); %>
<!DOCTYPE HTML>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
    <title>Fun主题-diy</title>

    <link rel="stylesheet" href="${ctx}/static/styles/siteV2.0.css"/>
    <style>
        a:active{
            background: rgba(0,0,0,0);
        }
        .nav_second_level{
            background: url(${ctx}/static/images/2.0/female-selected.png) no-repeat;
            background-size: 100% 100%;
        }
        .nav_second_level_male{
            background: url(${ctx}/static/images/2.0/male-selected.png) no-repeat;
            background-size: 100% 100%;
        }
        .female_default{
            color: #6f6e6e;
        }
        .male_selected{
            color:#1d9efe;
        }

        .female_selected{
            color: #ed3d41;
        }
        .diy-top{
            padding-top: 20px;
            width: 100%;
        }

    </style>
</head>
<body>

<form action="home.action" method="get">
   <img src="${ctx}/static/images/2.0/DIY.png" class="diy-top">
    <div id="container">
        <div id="dk_game_slider"></div>

        <div class="content ajax-wrap">
            <ul class="v-list J_ajaxWrap">
                <s:iterator value="newestPage.result">

                    <%@include file="home-list.jsp"%>
                </s:iterator>
            </ul>
            <div class="footer">
                <div class="J_scrollLoadMore load-btn click-state" data-api="home!shelfJson.action?g=diy"></div>
                <a href="#" class="go-top"></a>
            </div>
        </div>


    </div>

</form>
<script src="${ctx}/static/zepto/zepto.min.js"></script>
<script src="${ctx}/static/zepto/gameall.min.js"></script>
<script src="${ctx}/static/zepto/android.js"></script>
<script>
    $(document).ready(function(){
        $("#home").addClass("selected").removeAttr("href");

        var app='${param.g}';
        if(app ==='' ||app ==='female'){
            $("#female").addClass("female_selected").removeAttr("href");
            $("#male").removeClass("male_selected").addClass("female_default");
            $("#nav_second_level").removeClass("nav_second_level_male");
        }else if(app === 'male'){
            $("#male").addClass("male_selected").removeAttr("href");
            $("#female").removeClass("female_selected").addClass("female_default");
            $("#nav_second_level").addClass("nav_second_level_male");
        };

        var bars=${bars};
        $.ui.slider('#dk_game_slider', {
            index: bars.rand(),
            showArr: false,
            autoPlayTime: 2500,
            content: bars
        });
    });
</script>
</body>
</html>