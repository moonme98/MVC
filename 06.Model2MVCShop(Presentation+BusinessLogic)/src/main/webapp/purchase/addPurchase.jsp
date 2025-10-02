<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>구매 내역</title>
</head>

<body>

<form name="updatePurchase" action="/updatePurchaseView.do?tranNo=${purchase.tranNo}" method="post">

    다음과 같이 구매가 되었습니다.

    <table border="1">
        <tr>
            <td>물품번호</td>
            <td>${product.prodNo}</td>
            <td></td>
        </tr>
        <tr>
            <td>구매자아이디</td>
            <td>${purchase.buyer.userId}</td>
            <td></td>
        </tr>
        <tr>
            <td>구매방법</td>
            <td>
                <c:choose>
                    <c:when test="${purchase.paymentOption == '1'}">현금구매</c:when>
                    <c:when test="${purchase.paymentOption == '2'}">신용구매</c:when>
                    <c:otherwise>기타</c:otherwise>
                </c:choose>
            </td>
            <td></td>
        </tr>
        <tr>
            <td>구매자이름</td>
            <td>${purchase.buyer.userName}</td>
            <td></td>
        </tr>
        <tr>
            <td>구매자연락처</td>
            <td>${purchase.receiverPhone}</td>
            <td></td>
        </tr>
        <tr>
            <td>구매자주소</td>
            <td>${purchase.divyAddr}</td>
            <td></td>
        </tr>
        <tr>
            <td>구매요청사항</td>
            <td>${purchase.divyRequest}</td>
            <td></td>
        </tr>
        <tr>
            <td>배송희망일자</td>
            <td>${purchase.divyDate}</td>
            <td></td>
        </tr>
    </table>
</form>

</body>
</html>
