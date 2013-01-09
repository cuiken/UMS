<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>市场管理</title>

		<script>
			$(document).ready(function(){
				$("#message").fadeOut(3000);
				$("#market-tab").addClass("active");
			});
			function deleteThis(id){
				if(confirm("该操作会删除所有上市文件!确定要删除吗?")){
					window.location="market!delete.action?id="+id;
				}
			}
		</script>
	</head>
	<body>
		<form action="market.action" method="get">
			<h1>市场列表</h1>
			<c:if test="${not empty actionMessages}">
				<div id="message" class="success">${actionMessages}</div>	
			</c:if>
			
			<table class="table table-striped table-bordered table-condensed">
				<thead>
				<tr>
					<th>市场名称</th>
					<th>商店标识</th>
					<th>市场apk包名</th>
					<th>市场url key</th>
					<!--  <th>所有货架</th>-->
					<th>操作</th>
				</tr>
				</thead>
				<tbody>
				<s:iterator value="markets">
					<tr>
						<td>${name}</td>
						<td>${value}</td>
						<td>
							<c:choose>
								<c:when test="${fn:length(pkName)>30}">
									${fn:substring(pkName,0,30)}....
								</c:when>
								<c:otherwise>
									${pkName}
								</c:otherwise>
							</c:choose>	
						</td>
						<td>
							<c:choose>
								<c:when test="${fn:length(marketKey)>30}">
									${fn:substring(marketKey,0,30)}....
								</c:when>
								<c:otherwise>
									${marketKey}
								</c:otherwise>
							</c:choose>						
						</td>
						<td>
							<shiro:hasPermission name="store:edit">
								<a href="market!input.action?id=${id}">编辑</a>&nbsp;
								<a href="#" onclick="deleteThis(${id})">删除</a>
							</shiro:hasPermission>
						</td>
					</tr>
				</s:iterator>
				</tbody>
			</table>
			<shiro:hasPermission name="store:edit">
				<a class="btn" href="market!input.action">创建市场</a>&nbsp;
				<a class="btn" href="market!manage.action">管理市场</a>
			</shiro:hasPermission>

		</form>
		
	</body>
</html>