<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="navbar navbar-fixed-top" style="opacity:0.8">
	<div class="navbar-inner">
		<div class="container">
			<a class="btn btn-navbar" data-toggle="collapse"
				data-target=".nav-collapse"> <span class="icon-bar"></span> <span
				class="icon-bar"></span> <span class="icon-bar"></span>
			</a> <a style="float: left;padding-right: 20px;" class="brand" href="${ctx}">导航管理</a>
			<div class="nav-collapse">
				<ul class="nav">
					<li id="one"><a href="${ctx}/nav/board.action">一级分类</a></li>
					<li id="two"><a href="${ctx}/nav/nav-tag.action">二级分类 </a></li>
						
					<li id="three"><a href="${ctx}/nav/navigator.action">具体链接</a></li>
					<li id="readme"><a href="${ctx}/nav/readme.action">说明</a></li>
				</ul>
			</div>
			<div class="pull-right">
					<shiro:guest>
						<a href="${ctx}/login">登录</a>
					</shiro:guest>
					<shiro:user>

						<div class="btn-group pull-right">
							<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
								<i class="icon-user"></i> <shiro:principal property="name" /> <span
								class="caret"></span>
							</a>

							<ul class="dropdown-menu">								
								<li><a href="${ctx}/logout.action">Logout</a></li>
							</ul>
						</div>

					</shiro:user>

				</div>
		</div>
	</div>
</div>