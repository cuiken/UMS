<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="leftbar" class="span2">
	<shiro:hasPermission name="user:view">
		<h1>系统管理</h1>
		<div class="submenu">		
			<a id="account-tab" href="${ctx}/account/user.action">帐号管理</a> 		
			<shiro:hasPermission name="group:view">	
				<a id="group-tab" href="${ctx}/account/group.action">角色管理</a>
			</shiro:hasPermission>	
			<a id="viewsource-tab" href="${ctx}/report/view-source.action">系统工具</a> 
	</div>
	</shiro:hasPermission>
	
	<shiro:hasPermission name="file:view">
		<h1>文件管理</h1>
		<div class="submenu">
			<a id="lock-tab" href="${ctx}/file/file.action">解锁文件</a>		
			<a id="client-tab" href="${ctx}/file/funlocker-client.action">客户端文件</a>		
		</div>
	</shiro:hasPermission>
	
	<shiro:hasPermission name="category:view">
		<h1>文件分类管理</h1>
		<div class="submenu">
			<a id="category-tab" href="${ctx}/category/category.action">解锁类别</a> 
			<a id="ccategory-tab" href="${ctx}/category/client-type.action">客户端类别</a>
		</div>
	</shiro:hasPermission>
	
	<shiro:hasPermission name="store:view">
		<h1>商店市场管理</h1>
		<div class="submenu">
			<a id="store-tab" href="${ctx}/category/store.action">商店列表</a> 
			<a id="shelf-tab" href="${ctx}/category/shelf.action">货架列表</a> 
			<a id="market-tab" href="${ctx}/category/market.action">市场列表</a>
			<a id="poll-tab" href="${ctx}/poll/polling.action">广播列表</a>
            <a id="poll2-tab" href="${ctx}/poll/poll2.action">广播列表(enhancement)</a>
			<a id="advertisement-tab" href="${ctx}/poll/advertisement.action">广告条管理</a>
		</div>
	</shiro:hasPermission>
	
	<shiro:hasPermission name="report:view">
		<h1>报表管理</h1>
		<div class="submenu">	
			<a id="reportc-tab" href="${ctx}/report/report-client.action">客户端日报</a>
			<a id="reportcn-tab" href="${ctx}/report/report-content.action">内容日报</a>	
			<a id="report-getClient-tab" href="${ctx}/report/report-get-client.action">内容引导客户端日报</a>
			<a id="report-contentUnzip-tab" href="${ctx}/report/report-content-unzip.action">内容安装日报</a>	
			<a id="report-client-install-tab" href="${ctx}/report/report-client-install.action">客户端安装日报</a>	
			<a id="report-client-install-wc-tab" href="${ctx}/report/report-client-install-with-content.action">客户端通过内容安装日报</a>	
			<shiro:hasPermission name="feedback:view">		
				<a id="feedback-tab" href="${ctx}/report/feedback.action">用户意见反馈</a>
			</shiro:hasPermission>
		</div>
	</shiro:hasPermission>
	
	<shiro:hasPermission name="navigation:view">
		<h1>导航管理</h1>
		<div class="submenu">
			<a href="${ctx}/nav/board.action">导航相关</a>
		</div>
	</shiro:hasPermission>
</div>