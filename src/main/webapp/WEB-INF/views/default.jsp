<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>UMS2.0:欢迎页</title>
    <script>
        $(function(){
            $("#home-tab").addClass("active");
        });
    </script>
    <style>
        p> i{
            color: #08c;
        }
    </style>
</head>
<body>
   <div>
       <h1>起始页</h1>
       <div>
           图示说明:
            <p>
                <i class="icon-thumbs-up icon-4x"></i>最热标记
                 <i class="icon-time icon-4x"></i>最新标记
            </p>
           <p>
               操作栏出现如下标记，实心表示当前已发布或以标记,点击可进行切换操作   <br>
               <i class="icon-star-empty icon-2x"></i>
               <i class="icon-star icon-2x"></i>
               是否标记为最新
           </p>
           <p>
               <i class="icon-heart-empty icon-2x"></i>
               <i class="icon-heart icon-2x"></i>
               是否标记为最热
           </p>
           <p>
               <i class="icon-circle-blank icon-2x"></i>
               <i class="icon-circle icon-2x"></i>
               是否标记为发布
           </p>
          其他 ...............................................................................
           <p>
               <i class="icon-edit icon-2x"></i>
               编辑 <br>
               <i class="icon-trash icon-2x"></i>
               删除 <br>
               <i class="icon-rss icon-2x"></i>
               与客户端交互XML接口<br>
               <i class="icon-copy icon-2x"></i>
               复制
           </p>
       </div>
   </div>
</body>