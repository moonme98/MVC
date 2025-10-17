<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="spring.domain.User" %>

<!-- 	#. 비 로그인한 회원 -->
<%	User sessionUser = (User)session.getAttribute("sessionUser");	%>

<!-- 	#. 로그인한 회원이면 -->
<html>
	<head><title>Home Page</title></head>
	<body>
	
		[info] :: ${requestScope.message}<br/><br/>
		
		<p>Simple Model2 Examples</p>
		<p> 환영합니다.  : <%= sessionUser.getUserId()%>님</p>

		<br/><br/>
		<a href = "logout.do">logout</a>
	
	</body>
</html>