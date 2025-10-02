<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="spring.domain.User" %>

<!-- 
	1. 로그인 유무확인 :: Work Flow Control
		ㅇ 비로그인 인경우  : 로그인 화면 display 
	    ㅇ 로그인한 경우 : 이미 로그인 한 회원임을 display
	2. 로그인 확인은...
		ㅇ 로그인한 회원은 session ObjectScope에 User객체를 갖고, active 는 true
		ㅇ User객체의 유무 및 User의 active 값을 true / false 값으로 판단
	3. 로그아웃처리 : 로그인 정보를 갖는 user instance Session Object Scope  에서 
		삭제 처리 후 home.do로 포워드
====================================================
==> logon.jsp는 view  역할 / Work Flow Control (방어적 코딩)를 수행
====================================================	
====================================================
==> Logout.jsp는 view 역할 없으며... 
		ㅇ Work Flow Control / 로그아웃 처리
====================================================	
-->

<!-- 	#. 비 로그인한 회원 -->
<%	User user = (User)session.getAttribute("user");	%>

<%if ( user == null  ||  ! user.isActive() )  { %>
		<jsp:forward page="/logon.do" />
<% } %>


<!-- 	#. 로그인한 회원이면 -->
<%	session.removeAttribute("user");%>

<jsp:forward page = "/home.do" />