<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div id="header" class="row" style="margin-top: 35px;">
	<div class="navbar navbar-fixed-top" style="opacity:0.8">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="brand" href="${ctx}">UMS2.0</a>
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
								<li><a href="#" onclick="javascript:alert('功能未实现');">Edit Profile</a></li>
								<li><a href="${ctx}/logout.action">Logout</a></li>
							</ul>
						</div>

					</shiro:user>

				</div>
			</div>
		</div>
	</div>

</div>