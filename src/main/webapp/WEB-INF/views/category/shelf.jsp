<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>货架管理</title>

		<script>
			$(document).ready(function(){
				$("#message").fadeOut(3000);
				$("#shelf-tab").addClass("active");
				var sid=$("#store").val();
				
				var getData=function(sid){
						$.ajax({
							dataType:"json",
							url:"shelf!filterShelf.action?sid="+sid,
							success:function(data){
								
								var html="";
								$.each(data,function(i,val){		
									html+="<tr><td>"+val.name+"</td><td>"
									+val.value+"</td><td>"
									+val.description
									+"</td><td><a href=shelf!manage.action?id="
									+val.id+">管理</a></td></tr>";
								
								});
								
								$("#content tbody").append(html);
								
							}
						});
				};
				
				getData(sid);
				$("#store").change(function(){
					$("#content tr:not(:first)").remove();
					getData($(this).children('option:selected').val());
				});
			});
		</script>
	</head>
	<body>
		<form action="shelf.action" method="get" class="form-horizontal">
			<h1>货架列表</h1>
			<c:if test="${not empty actionMessages}">
				<div id="message" class="success">${actionMessages}</div>	
			</c:if>
			<div id="filter" style="margin-bottom: 5px;">
				商店:<s:select list="allStores" listKey="id" listValue="name" name="" id="store" cssClass="span2" />
			</div>
			<table id="content" class="table table-striped table-bordered table-condensed">
				<thead>
				<tr>
					<th>货架名称</th>
					<th>货架标识</th>
					<th>货架描述</th>
					<th>操作</th>
				</tr>	
				</thead>
				<tbody>
				</tbody>
				<!--  		
				<tr>
						<td>${name}</td>
						<td>${description}</td>
						<td>
							<a href="shelf!manage.action?id=${id}">管理</a>
							<a href="shelf!input.action?id=${id}">修改</a>
							<a href="shelf!delete.action?id=${id}">删除</a>
						</td>
				</tr>
				-->
			</table>		
			<!-- <a href="shelf!input.action">创建货架</a>	 -->		

		</form>
		
	</body>
</html>