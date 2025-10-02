<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="spring.domain.User" %>
<%@ page import="spring.service.user.impl.UserDAO" %>

<!-- 
	1. 로그인 유무확인 :: Work Flow Control
		ㅇ 비로그인 인경우  : 로그인 화면 display 
	    ㅇ 로그인한 경우 : 이미 로그인 한 회원임을 display
	2. 로그인 확인은...
		ㅇ 로그인한 회원은 session ObjectScope에 User객체를 갖고, active 는 true
		ㅇ User객체의 유무 및 User의 active 값을 true / false 값으로 판단
====================================================
==> LogonAction.jsp는 view 역할 없으며... 
		ㅇ Work Flow Control / 폼데이터 처리 및  및 Navigation
		ㅇ Business Logic 수행 
====================================================			
-->

<%	
	User user = (User)session.getAttribute("user");
	if(user == null){
		user = new User();
	}
%>

<!-- 	#. 로그인한 회원이면...	-->
<%	if ( user.isActive() )  { %>
		<jsp:forward page="/home.do" />
<% } %>
<!-- 	#. 비로그인한 회원이면...-->
<%
	// 한글 encoding처리
	request.setCharacterEncoding("UTF-8");
	
	//Client Form Data처리
	String userId = request.getParameter("userId");
	String password = request.getParameter("password");
	
	//포워드 할 디폴트 페이지 지정
	String targetAction = "/logon.do";
	
	//ValueObject에 Client Form Data Binding
	user.setUserId(userId);
	user.setPassword(password);
	
	//DAO 생성및 DB확인
	UserDAO userDAO = new UserDAO();
	userDAO.getUser(user);
	
	//로그인 유무 확인...
	if( user.isActive() ){
		//로그인 학인 후 : session ObjectScope에 User 저장.
		session.setAttribute("user",user);
		targetAction = "/home.do";
	}
%>

<!-- targetAction 페이지로 포워드 -->
<jsp:forward page='<%= targetAction %>' />