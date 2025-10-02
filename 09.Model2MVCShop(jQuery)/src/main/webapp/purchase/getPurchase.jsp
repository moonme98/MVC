<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
	<title>구매상세조회</title>
	
	<link rel="stylesheet" href="/css/admin.css" type="text/css">
	
	<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
	<script type="text/javascript">
	
		//==> 추가된부분 : "확인" "수정"  Event 연결 및 처리
		$(function() {
			$( "td.ct_btn01:contains('확인')" ).on("click" , function() {
				//Debug..
				//alert(  $( "td.ct_btn01:contains('확인')" ).html() );
				//history.go(-1);
				location.href = "/purchase/listPurchase";
			});
			
			$( "td.ct_btn01:contains('수정')" ).on("click" , function() {
				//Debug..
				//alert(  $( "td.ct_btn01:contains('수정')" ).html() );
				self.location = "/purchase/updatePurchase?tranNo=${purchase.tranNo}"
			});
		});
	</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif"	width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">구매상세조회</td>
					<td width="20%" align="right">&nbsp;</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif"	width="12" height="37"/>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 13px;">
	<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
	<tr>
		<td width="104" class="ct_write">물품번호 <img src="/images/ct_icon_red.gif" width="3" height="3"/></td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><c:out value="${product.prodNo}"/></td>
	</tr>
	<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

	<tr>
		<td width="104" class="ct_write">구매자아이디 <img src="/images/ct_icon_red.gif" width="3" height="3"/></td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><c:out value="${purchase.buyer.userId}"/></td>
	</tr>
	<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

	<tr>
		<td width="104" class="ct_write">구매방법</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<c:choose>
				<c:when test="${purchase.paymentOption == '1'}">현금구매</c:when>
				<c:when test="${purchase.paymentOption == '2'}">신용구매</c:when>
				<c:otherwise>기타</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

	<tr>
		<td width="104" class="ct_write">구매자이름</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><c:out value="${purchase.receiverName}"/></td>
	</tr>
	<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

	<tr>
		<td width="104" class="ct_write">구매자연락처</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><c:out value="${purchase.receiverPhone}"/></td>
	</tr>
	<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

	<tr>
		<td width="104" class="ct_write">구매자주소</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><c:out value="${purchase.divyAddr}"/></td>
	</tr>
	<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

	<tr>
		<td width="104" class="ct_write">구매요청사항</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><c:out value="${purchase.divyRequest}"/></td>
	</tr>
	<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

	<tr>
		<td width="104" class="ct_write">배송희망일</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><c:out value="${purchase.divyDate}"/></td>
	</tr>
	<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

	<tr>
		<td width="104" class="ct_write">주문일</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><c:out value="${purchase.orderDate}"/></td>
	</tr>
	<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
	<tr>
		<td width="53%"></td>
		<td align="right">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23"><img src="/images/ct_btnbg01.gif" width="17" height="23"/></td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top: 3px;">수정</td>
					<td width="14" height="23"><img src="/images/ct_btnbg03.gif" width="14" height="23"/></td>
					<td width="30"></td>
					<td width="17" height="23"><img src="/images/ct_btnbg01.gif" width="17" height="23"/></td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top: 3px;">확인</td>
					<td width="14" height="23"><img src="/images/ct_btnbg03.gif" width="14" height="23"/></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

</body>
</html>
