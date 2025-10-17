<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
	<title>구매 목록조회</title>

	<link rel="stylesheet" href="/css/admin.css" type="text/css">
	
	<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
	<script type="text/javascript">
	
		//검색 / page 두가지 경우 모두 Form 전송을 위해 JavaScrpt 이용  
		function fncGetUserList(currentPage) {
		    //document.getElementById("currentPage").value = currentPage;
		    //document.detailForm.submit();	
			$("#currentPage").val(currentPage)
			$("form").attr("method" , "POST").attr("action" , "/purchase/listPurchase").submit();
		}
		
		$(function() {
			$("td[data-tranno]").on("click", function() {
		        var tranNo = $(this).data("tranno");
		        
		        if(tranNo){
		            location.href = "/purchase/getPurchase?tranNo=" + tranNo;
		        }
		    });

		    $("td[data-userid]").on("click", function() {
		        var userId = $(this).data("userid");
		        
		        if(userId){
		            location.href = "/user/getUser?userId=" + userId;
		        }
		    });

		    $(".arrival-btn").on("click", function() {
		        var tranNo = $(this).data("tranno");
		        
		        if(tranNo){
		            location.href = "/purchase/updateTranCode?tranNo=" + tranNo + "&tranCode=3";
		        }
		    });
			
			// 임시 LINK Event 색 구분
			$( ".ct_list_pop td:nth-child(3)" ).css("color" , "red");
			$(".ct_list_pop:nth-child(4n+6)" ).css("background-color" , "whitesmoke");
		});
		
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm">
<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">구매 목록조회</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">
			전체  ${resultPage.totalCount } 건수, 현재 ${resultPage.currentPage}  페이지
		</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">전화번호</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">배송현황</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">정보수정</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<c:set var="i" value="0" />
	<c:forEach var="purchase" items="${list}">
		<c:set var="i" value="${ i+1 }" />
		<tr class="ct_list_pop">
			<!-- No -->
			<td align="center" data-tranno="${purchase.tranNo}" style="cursor:pointer;">${i}</td>
			<td></td>
			
			<!-- 회원ID -->
			<td align="left" data-userid="${purchase.buyer.userId}" style="cursor:pointer;">${purchase.buyer.userId}</td>
			<td></td>
			
			<!-- 회원명 -->
			<td align="left">${purchase.receiverName}</td>
			<td></td>
			
			<!-- 전화번호 -->
			<td align="left">${purchase.receiverPhone}</td>
			<td></td>
			
			<!-- 배송현황 -->
			<td align="left">
			    현재 ${purchase.tranCodeName}상태 입니다.
			</td>
			<td></td>
			
			<!-- 정보수정 -->
			<td align="left">
			    <c:if test="${purchase.tranCode.trim() eq '2'}">
			    	<span class="arrival-btn" data-tranno="${purchase.tranNo}" style="cursor:pointer;">물건도착</span>
				</c:if>
			</td>
		</tr>
		<tr>
			<td colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>
	</c:forEach>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
	<tr>
		<td align="center">
		 	<input type="hidden" id="currentPage" name="currentPage" value=""/>
		 	
			<jsp:include page="../common/pageNavigator.jsp" />
			
		</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>