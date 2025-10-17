<%@ page contentType="text/html;charset=UTF-8" %>

<%
	String userId = "아이디입력";
	String password = "패스워드입력";
%>

<html>
	<head><title>Logon Page</title></head>
	<body>
	
		[info] :: ${requestScope.message}<br/><br/>

		<form  method="post" action="logonAction.do">
	
			아  이  디 : <input type="text" name="userId" value="<%= userId %>"><br/><br/>
			패스워드 : <input type="text" name="password" value="<%= password %>"><br/><br/>
			<input type="submit" name="submit" value="Enter"/>
	
		</form>

	</body>
</html>