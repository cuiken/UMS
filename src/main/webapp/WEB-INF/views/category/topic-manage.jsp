<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>专题文件管理</title>
    <link rel="stylesheet"
          href="${ctx}/js/jquery/themes/base/jquery.ui.all.css">
    <script src="${ctx}/js/jquery/ui/jquery.ui.core.js"></script>
    <script src="${ctx}/js/jquery/ui/jquery.ui.widget.js"></script>
    <script src="${ctx}/js/jquery/ui/jquery.ui.mouse.js"></script>
    <script src="${ctx}/js/jquery/ui/jquery.ui.draggable.js"></script>
    <script src="${ctx}/js/jquery/ui/jquery.ui.droppable.js"></script>
    <script src="${ctx}/js/jquery/ui/jquery.ui.sortable.js"></script>
    <script src="${ctx}/js/jquery/ui/jquery.ui.accordion.js"></script>

    <style>
        h1 {
            padding: .2em;
            margin: 0;
        }

        #products {
            float: left;
            margin-right: 2em;
        }

        #cart {
            float: left;
        }
            /* style the list to maximize the droppable hitarea */
        #sortable1 li,#sortable2 li {
            margin: 0 5px 0px 15px;
            padding: 0px;
            /*font-size: 1.2em;*/
            width: 150px;
        }

        #sortable2 li a {
            color: #0066CC;
        }
        .ui-widget-content{
            height: 420px;
            width: 260px;
            overflow-y: auto;
        }
    </style>

    <script>

        $(function() {
            $("#message").fadeOut(3000);
            $("#topic-tab").addClass("active");
            $("#topic-tab a").append("<i class='icon-remove-circle'></i>");
            $( "#sortable1, #sortable2" ).sortable({
                //revert: true, //缓冲效果
                cursor: 'move', //拖动的时候鼠标样式
                connectWith: ".connectedSortable"
            }).disableSelection();
            $("#btn").click(function(){
                var checked=$("#sortable2").children();
                for(var i=0;i<checked.length;i++){
                    $("#cart").append("<input type='hidden' name='checkedFileIds' value="+checked[i].id+">");
                }
                $("#cart").append("<input type='hidden' name='selectId' value="+$("#shelf").val()+">");
                $("#manageForm").submit();
            });

            $("#topic option[value="+'${param.id}'+"]").attr("selected",true);

            var getRemainFileData=function(topicId){
                $.ajax({
                    dataType:"json",
                    url:"topic!getRemainFile.action?id="+topicId,
                    success:function(data){
                        var li="";
                        $.each(data,function(i,val){
                            li+="<li id="+val.id+">"+val.name+"</li>";
                        });
                        $("#sortable1").append(li);
                    }
                });
            };
            var getShelfFileData=function(topicId){
                $.ajax({
                    dataType:"json",
                    url:"topic!getOnShelfFile.action?id="+topicId,
                    success:function(data){
                        var li="";

                        $.each(data,function(i,val){
                            li+="<li id="+val.id+">"+val.name+"</li>";
                        });
                        $("#sortable2").append(li);
                    }
                });
            };

            getRemainFileData('${param.id}');
            getShelfFileData('${param.id}');

            $("#topic").change(function(){
                $("#sortable1 li").remove();
                $("#sortable2 li").remove();
                var selectedId=$(this).children('option:selected').val();
                getRemainFileData(selectedId);
                getShelfFileData(selectedId);
                $("#topicId").val(selectedId);
            });

        });

    </script>
</head>
<body>
<form id="manageForm" action="topic!saveFile.action" method="post"
      class="form-horizontal">

    <input type="hidden" id="topicId" name="id" value="${id}" />

    <c:if test="${not empty actionMessages}">
        <div id="message" class="alert alert-success">${actionMessages}</div>
    </c:if>
    <fieldset>
        <legend><small>专题文件管理</small></legend>
        <div id="filter" style="margin: 5px;">
            专题名称:
            <s:select list="topics" listKey="id" listValue="name" name=""
                      id="topic" cssClass="span2"/>
        </div>
        <div id="products">
            <h2>仓库文件</h2>
            <div class="ui-widget-content">
                <ol id="sortable1" class="connectedSortable"
                    style="min-height: 300px;">

                </ol>
            </div>
        </div>

        <div id="cart">
            <h2>已上架文件</h2>
            <div class="ui-widget-content">
                <ol id="sortable2" class="connectedSortable"
                    style="min-height: 300px;">

                </ol>
            </div>
        </div>
    </fieldset>
    <div class="form-actions">
        <input class="btn btn-primary" type="button" value="保存" id="btn" />&nbsp;
        <input class="btn" type="button" value="返回" onclick="location.href='topic.action';">
    </div>
</form>

</body>
</html>
