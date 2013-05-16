<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>文件列表</title>
<script src="${ctx}/js/table.js"></script>
<script>
			$(document).ready(function(){
				$("#message").fadeOut(3000);
				$("#lock-tab").addClass("active");
                $("#lock-tab a").append("<i class='icon-remove-circle'></i>");
                var dtype='${param['filter_EQS_dtype']}';
                if(dtype==0){
                    $("#standard").addClass("active");
                }else if(dtype==1){
                    $("#model").addClass("active");
                }else{
                    $("#app").addClass("active");
                }
			});
			function deleteThis(id){
				if(confirm("确定要删除吗?")){
					window.location="file!delete.action?id="+id+"&dtype="+'${param['filter_EQS_dtype']}';
				}
			}
			
			function doupload(){
				window.location="file-upload.action?dtype="+'${param['filter_EQS_dtype']}';
			}
            function doHot(id,pageNo,type){
                window.location="file!hotTag.action?id="+id+"&page.pageNo="+pageNo+"&dtype="+type;
            }
            function doNew(id,pageNo,type){
               window.location="file!newTag.action?id="+id+"&page.pageNo="+pageNo+"&dtype="+type;
            }
		</script>
</head>
<body>
	<h1>文件列表</h1>
	<form id="mainForm" action="file.action" method="post" class="form-horizontal">
        <input type="hidden" name="filter_EQS_dtype" value="${param['filter_EQS_dtype']}">

		<c:if test="${not empty actionMessages}">
			<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${actionMessages}</div>
		</c:if>
        <ul class="nav nav-pills" style="margin-bottom: 2px;float: left;">
            <li id="standard">
                <a href="file.action?filter_EQS_dtype=0">标准解锁</a>
            </li>
            <li id="model">
               <a href="file.action?filter_EQS_dtype=1">模板解锁</a>
            </li>
            <li id="app">
               <a href="file.action?filter_EQS_dtype=2">广告包文件</a>
            </li>
        </ul>
        <div id="filter" style="display: inline;margin-left: 20px;">
            <input type="text" class="search-query input-medium" name="filter_LIKES_title" value="${param['filter_LIKES_title']}" size="20" placeholder="标题" />
            <input type="button" id="submit_btn" class="btn" value="查询" onclick="search();" />
            <div class="pull-right">
                <a class="icon-plus" href="#" onclick="doupload();">新增</a>
            </div>
        </div>
		<table class="table table-hover">
			<thead>
				<tr>
					<th><a href="javascript:sort('title','asc')">#标题</a></th>
					<th>文件名</th>
					<th>版本号</th>
                    <th>Tag</th>
					<th><a href="javascript:sort('createTime','asc')">添加时间</a></th>
					<th><a href="javascript:sort('modifyTime','asc')">更新时间</a></th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="page.result">
					<tr>
						<td><s:if test="ishot==1"><i class="icon-thumbs-up" style="color: #ff6600;"></i></s:if>
                            <s:if test="isnew==1"><i class="icon-time" style="color: #ff6600"></i></s:if>
                            <a href="file!input.action?id=${id}&page.pageNo=${page.pageNo}">${title}</a>
                        </td>
						<td>${name}</td>
						<td>${version}</td>
                        <td>${tagNames}</td>
						<td>${createTime}</td>
						<td>${modifyTime}</td>
						<td><shiro:hasPermission name="file:edit">
								<!-- <a href="file-info.action?themeId=${id}">语言</a> -->
                            <s:if test="isnew==1">
                                <a href="#" onclick="doNew('${id}','${page.pageNo}','${dtype}')"><i class="icon-star"></i></a>
                            </s:if><s:else>
                                <a href="#" onclick="doNew('${id}','${page.pageNo}','${dtype}')"><i class="icon-star-empty"></i></a>
                            </s:else>
                            &nbsp;
                            <s:if test="ishot==1">
                                <a href="#" onclick="doHot('${id}','${page.pageNo}','${dtype}')"><i class="icon-heart"></i></a>
                            </s:if><s:else>
                                <a href="#" onclick="doHot('${id}','${page.pageNo}','${dtype}')"><i class="icon-heart-empty"></i></a>
                            </s:else>
                            &nbsp;
							<a href="#" onclick="deleteThis('${id}')"><i class="icon-trash"></i></a>
							</shiro:hasPermission></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<%@include file="/common/page.jsp"%>

	</form>
</body>
</html>