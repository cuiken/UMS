$(function(){

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
                    me.$stateBox.addClass("J_noMore").text("已经到底了");
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
}(Zepto,window));

$(document).ready(function(){
//应用信息展开&关闭
    $(".toggle-btn").bind("click",function(){
        var $toggleBtn = $(this);
        var $info = $toggleBtn.prev();
        var h = $info.height();
        toggleInfo( $info, $toggleBtn, h )
    });

//应用信息隐藏
    $(".J_info").each(function(){
        var $info = $(this);
        var h = $info.height();
        var $toggleBtn = $(this).next();
        toggleInfo( $info, $toggleBtn, h )
    });
    function toggleInfo( $info, $toggleBtn, h ){
        if( h > 103 ){
            hide();
        }else if( h == 103 ){
            $info.height( "auto" );
            $toggleBtn.addClass("arr-d");
        }else{
            $toggleBtn.hide();
        }
        function hide(){
            $info.height("103px");
            $toggleBtn.removeClass("arr-d");
            $toggleBtn.show();
        }
    }
});