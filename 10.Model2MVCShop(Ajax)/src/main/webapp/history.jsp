<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<html>
<head>
    <title>열어본 상품</title>
</head>
<body>
<h3>최근 열어본 상품</h3>

<c:set var="history" value="" />

<%
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("history".equals(cookie.getName())) {
                String decoded = java.net.URLDecoder.decode(cookie.getValue(), "UTF-8");
                pageContext.setAttribute("history", decoded);
                break;
            }
        }
    }
%>

<c:if test="${not empty history}">
    <c:forEach var="prodNo" items="${fn:split(history, ',')}">
        <a href="/product/getProduct.do?prodNo=${prodNo}" target="rightFrame">상품번호 ${prodNo}</a>
        <br/>
    </c:forEach>
</c:if>

<c:if test="${empty history}">
    <p>최근 본 상품이 없습니다.</p>
</c:if>

</body>
</html>
