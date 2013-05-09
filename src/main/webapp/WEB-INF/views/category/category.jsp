<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>解锁文件分类</title>
<script>
			$(document).ready(function(){
				$("#message").fadeOut(3000);	
				$("#category-tab").addClass("active");
                $("#category-tab a").append("<i class='icon-remove-circle'></i>");
			});
			function deleteThis(id){
				if(confirm("确定要删除吗?")){
					window.location="category!delete.action?id="+id;
				}
			}
		</script>
</head>
<body>
	<form action="category.action" method="get">
		<h1>解锁文件分类</h1>
		<c:if test="${not empty actionMessages}">
			<div id="message" class="alert alert-success">${actionMessages}</div>
		</c:if>
        <div class="pull-right" style="margin: 10px;">
            <shiro:hasPermission name="category:edit">
                <a class="icon-plus" href="category!input.action">新增</a>
            </shiro:hasPermission>
        </div>
		<table class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>序号</th>
					<th>预览图</th>
					<th>分类名称</th>
					<th>分类描述</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="categories">
					<tr>
						<td>${value}</td>
						<td><img alt="${name}" src="${ctx}/image.action?path=${icon}" style="height: 30px;width: 50px;"></td>
						<td>${name}</td>
						<td>${description}</td>
						<td><shiro:hasPermission name="category:edit">
								<a href="category!input.action?id=${id}">编辑</a>
								<a href="category-info.action?cid=${id}">语言</a>
								<a href="#" onclick="deleteThis(${id})">删除</a>
							</shiro:hasPermission></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>

	</form>

</body>
</html>