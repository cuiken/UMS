<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%@ page import="com.tp.utils.ServletUtils" %>
<%response.setStatus(200);%>

<%
	Throwable ex = null;
	if (exception != null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Throwable) request.getAttribute("javax.servlet.error.exception");

	//记录日志
	String userAgent = request.getHeader("User-Agent");
	String ip=ServletUtils.getIpAddr(request);
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(ex.getMessage()+",ip:"+ip+",User-Agent:"+userAgent, ex);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>500 - </title>
</head>

<body>
<div><h1>服务器开小差了.</h1></div>
</body>
</html>
