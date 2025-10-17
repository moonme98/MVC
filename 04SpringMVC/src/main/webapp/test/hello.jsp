<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8"%>

<html>
<head>
	<title>Insert title here</title>
</head>
<body>
	<%--///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	<%
		String value01 = (String)request.getAttribute("request");
		String value02 = (String)session.getAttribute("session");
		String value03 = (String)application.getAttribute("application");
	%> 
	<%= value01 %><br/>
	<%= value02 %><br/>
	<%= value03 %><br/>
	JSP2.0에 추가된 기능인 EL(Expression Language)를 사용하면..
	위와 같은  <%%><%=%> 을 아래와 같이 단순하게 사용/출력할 수 있다.	
	 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////--%>
	■ Model/View 연결 : Object Scope 사용<br/> <br/>
	requestScope.request : ${ requestScope.request } <br/>
	sessionScope.session : ${ sessionScope.session } <br/>
	applicationScope.application : ${ applicationScope.application } <br/>

	<br/><br/><hr size="5" /><br/>	
	
	■ ModelAndView 객체에 넣어준(?) Model Data 는 : request Object Scope 사용<br/><br/>
	message : ${ message }<br/>
	reqeustScope.message : ${ requestScope.message }<br/>
	sessionScope.message :${ sessionScope.message }<br/>
	applicationScope.message : ${ applicationScope.message }<br/>
	
	<br/><br/><hr size="5" /><br/>
	
	<%=  request.getRequestURI() %><br/>
	<%=  request.getRequestURL() %>
	
</body>
</html>