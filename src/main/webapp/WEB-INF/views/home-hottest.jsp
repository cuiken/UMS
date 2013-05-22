<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<% response.setHeader("remember", "true"); %>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
    <title>Fun主题-商店排行</title>
    <link rel="stylesheet" href="${ctx}/static/styles/site.min.css"/>
    <link rel="stylesheet" href="${ctx}/static/styles/siteV2.0.css">
    <style>
        .load-btn{
            position: relative;
            height: 40px;
            line-height: 40px;
            text-align: center;
            background-image: -webkit-gradient(linear,left top,left bottom,color-stop(0,#eee),color-stop(1,#f9f9f9));
            text-shadow: 0 1px 1px #fff;
            border-top: 1px solid #d7d7d7;
            border-bottom: 1px solid #fff;
            color: #333;
            font-size: 14px;
        }
        .click-state::before {
            content: "";
            display: inline-block;
            width: 10px;
            height: 10px;
            margin-right: 5px;
            background: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABUAAAAaCAMAAABvn+dxAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAAZQTFRFd3d3AAAADlogTAAAAAJ0Uk5T/wDltzBKAAAAU0lEQVR42tySAQrAIAwDL///9KRstSe+YEWxXkMwILkVtQ6yNgjXlSpBeCmCBKm/XibdMRV7vDXDihhn0Bh2sOOB0+SSuI8/JY4Ti0YJr7/kEWAAa1MBS66ah5oAAAAASUVORK5CYII=") no-repeat;
            <%--background: url(${ctx}/static/images/2.0/list-more.png) no-repeat;--%>
            background-size: contain;
        }
    </style>
</head>
<body>
<form action="home!hottest.action" method="get">
    <%@include file="home-nav.jsp"%>
    <%--<div id="dk_game_slider"></div>--%>
    <div id="container">
        <div class="content ajax-wrap">
           <ul class="v-list J_ajaxWrap">

            <s:iterator value="sorts" id="ff">
                <%--<div class="contents_info" id="content1" onclick="location.href='home!details.action?id=${f_id}&${queryString}';">--%>
                    <%--<div class="contents_image">--%>
                        <%--<img alt="${title}" onerror="this.src='${ctx}/static/images/default.png'" src="${ctx}/files/${icon_path}" width="72" height="72" style="margin: 3px;">--%>
                    <%--</div>--%>
                    <%--<div class="contents_txt">--%>
                        <%--<div style="margin-top: 10px;">--%>
                            <%--<span class="title">${title}</span>--%>
                            <%--<p>${short_description}</p>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%@include file="home-list2.jsp"%>
            </s:iterator>
           </ul>
           <div class="J_scrollLoadMore load-btn click-state" data-api="home!jsonMore.action"></div>
        </div>
    </div>
    <%--<nav id="page-nav">--%>
        <%--<a href="?pageNo=${nextPage}&${queryString}"></a>--%>
    <%--</nav>--%>

</form>
<script src="${ctx}/static/jquery/1.7.2/jquery.min.js"></script>
<script src="${ctx}/static/zepto/zepto.min.js"></script>
<script src="${ctx}/static/zepto/gameall.min.js"></script>
<script>
    $(function(){
        $("#hottest").addClass("selected").removeAttr("href");
//        $(".contents_info").click(function(){
//            $(this).css("backgroundColor","#e7e6c8");
//        });
        var appList=new SQ.LoadMore({
            eventType:"click",
            triggerTarget:window,
            ajaxBox:".J_ajaxWrap",
            stateBox:".J_scrollLoadMore",
            stateStyle:"load-btn",
            startPageIndex: 2,
            scrollMaxPage : 0,
            loadingTip:"正在加载请稍后...",
            initTips : "点击加载更多",
            clickTips : "点击加载更多",
            loadedError : "加载错误请重试"
        });
        $(".go-top").click(function(e){
            e.preventDefault();
            window.scrollTo(0,0);
        });



    });

    var SQ = SQ || {};

    (function($,window){

        function LoadMore(config){
            var me=this;
            me.config=config;
            me.$triggerTarget = $(me.config.triggerTarget);
            me.$ajaxBox = $(me.config.ajaxBox);
            me.$stateBox = $(me.config.stateBox).addClass(me.config.stateStyle).text(me.config.initTips);
            me.api = me.$stateBox.attr("data-api");
            me.page = me.config.startPageIndex || 0;
            me.maxPage = me.config.scrollMaxPage + me.page;
            me.currentState = "none";
            me._init();
        }
        LoadMore.prototype={
            constructor:LoadMore,

            _init:function(){
                var me=this;
                if(me.$triggerTarget.length > 0 && me.$ajaxBox.length > 0 && me.$stateBox.length > 0){
                    me._bind(me.$stateBox, me.config.eventType);
                }
            },
            _bind:function($el,eventType){
                var me=this;
                $el.bind(eventType,function(){
                    me._trigger(eventType);
                });
            },
            _unbind:function($el,eventType){
                $el.unbind(eventType);
            },
            _trigger:function(eventType){
                var me=this;
                var connector=me.api.indexOf("?") === -1 ? "?" : "&";
                var isLoading=me.$stateBox.hasClass("J_loading");
                var isNoMore=me.$stateBox.hasClass("J_noMore");
                if(isLoading && isNoMore){
                    return;
                }
                if(eventType === "click"){
                    me._loadDate(me.api+connector+"pageNo="+me.page);
                }
            },
            _changeState:function(state){
                var me=this;
                if(me.currentState === state){
                    return;
                }
                me.currentState = state;
                if(me.config.eventType === "click"){
                    switch (state){
                        case "loading":
                            me.$stateBox.addClass("J_loading").show().text(me.config.loadingTip);
                            break;
                        case "loaded":
                            me.$stateBox.removeClass("J_loading").text(me.config.clickTips);
                            me.page += 1;
                            break;

                    }
                }
                switch (state){
                    case "noMore":
                        me.$stateBox.addClass("J_noMore").hide();
                        break;
                    case "loadedError":
                        me.$stateBox.removeClass("J_loading").text(me.config.loadedError);
                        break;
                    case "unknowError":
                        me.$stateBox.removeClass("J_loading").text("未知错误，请重试");
                        break;
                }
            },
            _loadDate:function(api){
                var me=this;
                me._changeState("loading");
                var XHR= $.ajax({
                    type:"POST",
                    url:api,
                    timeout:5000,
                    success:function(data){
                        me._render(data);
                    },
                    error:function(){
                        me._changeState("loadedError");
                    }
                })
            },
            _render:function(data){
                var me=this;
                var jsonData= typeof data === "string" ? $.parseJSON(data) : data;
                var code=parseInt(jsonData.code,10);
                switch (code){
                    case 200:
                        me.$ajaxBox.append(jsonData.data);
                        me._changeState("loaded");
                        break;
                    case 900:
                        me.$ajaxBox.append(jsonData.data);
                        me._changeState("noMore");
                        break;
                    default :
                        me._changeState("unknowError");
                }
            }
        };
        SQ.LoadMore=LoadMore;
    }(jQuery,window));
</script>
</body>
</html>