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
                $("#shelf-tab a").append("<i class='icon-remove-circle'></i>");
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
									+val.id+"><i class='icon-cog'></i></a>&nbsp;&nbsp;"+
                                            "<a href=shelf!input.action?id="+val.id+"><i class='icon-edit'></i></a>&nbsp;&nbsp;" +
                                            "<a href=shelf!delete.action?id="+val.id+"><i class='icon-trash'></i></a></td></tr>";
								
								});
								
								$("#content tbody").append(html);
								
							}
						});
				};
				
				getData(sid);
				$("#store").change(function(){
					$("#content tr:not(:first)").remove();
					getData($(this).children('option:selected').val());
					sid=$("#store").val();
				});
				
				$("#createShelf").click(function(){
					location.href="shelf!input.action?sid="+sid;
				})
			});
		</script>
	</head>
	<body>
		<form action="shelf.action" method="get" class="form-horizontal">
			<h1>货架列表</h1>
			<c:if test="${not empty actionMessages}">
				<div id="message" class="alert alert-success">${actionMessages}</div>	
			</c:if>
			<div id="filter" style="margin-bottom: 5px;">
				商店:<s:select list="allStores" listKey="id" listValue="name" name="" id="store" cssClass="span2" />
                <div class="pull-right">
                    <a class="icon-plus" href="#" id="createShelf">新增</a>
                </div>
			</div>
			<table id="content" class="table table-striped table-bordered">
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
			</table>

		</form>
		
	</body>
</html>