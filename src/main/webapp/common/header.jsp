<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div id="header" class="span-24 last">
	<div class="title">Mopita</div>
	<span class="subtitle">--文件管理</span>
	<shiro:user>
		<span class="pull-right" style="float: right;">Hello, <shiro:principal property="name"/>!!</span>
	</shiro:user>
</div>