<%@page import="org.apache.shiro.SecurityUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
	if (SecurityUtils.getSubject().isAuthenticated()) {
		response.sendRedirect("/snd/stat/1");
	}
%>
<!DOCTYPE html>
<html lang="en" class="no-js">

<head>
	<meta charset="utf-8">
	<title>中农普惠</title>
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	<link type="image/x-icon" rel="shortcut icon" href="/static/favicon.ico" />
	<!-- CSS -->
	<link rel="stylesheet" href="/static/src/login/css/reset.css">
	<link rel="stylesheet" href="/static/src/login/css/supersized.css">
	<link rel="stylesheet" href="/static/src/login/css/style.css">
	<style>
		.page-container {
			margin: 220px auto 0 auto;
		}
	</style>

</head>

<body>

	<div class="page-container">
		<!--<h1>Login</h1> -->
		<input type="text" name="username" class="username"
			placeholder="请输入账号"><br> <input type="password"
			name="password" class="password" placeholder="请输入密码"><br>
		<button type="submit" id="loginbtn">登录</button>
		<br>
		<c:if test="${ status == false }">
			<div class="error">
				<span><c:out value="登录失败，请检查用户名和密码！" /></span>
			</div>
		</c:if>

	</div>


	<!-- Javascript -->
	<script src="/static/src/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="/static/src/login/js/supersized.3.2.7.min.js"></script>
	<script src="/static/src/login/js/supersized-init.js"></script>
	<script src="/static/src/login/js/scripts.js"></script>

</body>
<script type="text/javascript">
	$(function() {

		$('#loginbtn').click(
				function() {

					var un = $(".username").val();

					var password = $(".password").val();

					if (un == null) {
						return;
					}
					if (password == null) {
						return;
					}
					var param = "{ 'username': '" + un + "', 'pwd': '"
							+ password + "' }";
					$.ajax({
						type : "POST",
						url : "/sys/authUser/auth",
						data : param,
						contentType : "application/json; charset=utf-8",
						dataType : "json",
						success : function(data) {
							if (data.code != "0") {
								alert(data.message);
							} else {
								//登录成功
								window.location.href = "/snd/stat/1";
							}
						},
						error : function(data) {
							alert("调用失败....");
						}
					});
				});

	})
</script>

</html>
