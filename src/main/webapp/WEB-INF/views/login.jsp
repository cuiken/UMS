<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/common/taglibs.jsp" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>UMS2.0:登录页</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="Cache-Control" content="no-store" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
	<link href="${ctx}/static/bootstrap/2.1.1/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/bootstrap/2.1.1/css/bootstrap-responsive.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/css/mini-web.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" rel="stylesheet">
	<script src="${ctx}/static/jquery/1.7.2/jquery.min.js"></script>
	<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js"></script>
	<script src="${ctx}/static/jquery-validation/1.10.0/messages_bs_zh.js"></script>
	<style>
	 body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }
	</style>
	<script>
		$(document).ready(function() {
			$("#username").focus();
			$("#loginForm").validate();
		});
	</script>
</head>

<body data-spy="scroll" data-target=".subnav" data-offset="50">
	<!-- 
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
					<span class="icon-bar"></span>
		            <span class="icon-bar"></span>
		            <span class="icon-bar"></span>
				</a>
				<a class="brand" href="${ctx}/login.action">UMS2.0</a>
				<div class="nav-collapse">
					<ul class="nav">
						<li class="active">
							<a href="${ctx}/login.action">登录</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div> -->
	<div class="container">
		  <header class="jumbotron masthead">
		 	<div class="inner">
		    	<p></p>
			</div>
		</header>
	<div id="content" class="span12">
		<form id="loginForm" action="${ctx}/login.action" method="post" class="form-signin">
			<%
			String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
			if(error != null){
				if(error.contains("DisabledAccountException")){
			%>
				<div class="control-group">
				<div class="controls ">
				<div class="alert alert-error">
				<button class="close" data-dismiss="alert">×</button>
				用户已被屏蔽,请登录其他用户.</div>
				</div>
				</div>
			<%
				}else{
			%>	
				<div class="control-group">
				<div class="controls ">
				<div class="alert alert-error">
				<button class="close" data-dismiss="alert">×</button>
					登录失败，请重试.</div>
				</div>
				</div>
				
			<%
			}}
			%>
				<h2 class="form-signin-heading">登录</h2>
				<div class="control-group">
					
					<div class="controls">
						<input type="text" placeholder="帐号" id="username" name="username" size="50" value="${username}" class="required span3"/>
					</div>
				</div>
				<div class="control-group">
					
					<div class="controls">
						<input type="password" id="password" placeholder="密码" name="password" size="50"  class="required span3"/>
					</div>
				</div>
				<label class="checkbox" for="rememberMe"> <input type="checkbox" id="rememberMe" name="rememberMe" checked="checked"/> 下次自动登录</label>
				<div class="control-group">
					<div class="controls">
					<input id="submit_btn" class="btn btn-large btn-primary" type="submit" value="登录"/>
				</div>
			</div>		
		</form>
		<footer class="footer">
		<p align="center">&copy; 2012-2013 <a href="http://www.tpadsz.com/">tpadsz.com</a></p>
		</footer>
	</div>
	</div>
	<script src="${ctx}/static/bootstrap/2.1.1/js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>
