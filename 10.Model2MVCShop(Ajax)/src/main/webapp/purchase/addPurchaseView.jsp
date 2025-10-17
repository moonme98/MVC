<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>

<head>
	<title>상품상세조회</title>
	
	<link rel="stylesheet" href="/css/admin.css" type="text/css">
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	
	<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	
	<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
	<script src="https://developers.kakaopay.com/sdk/kakaopay.min.js"></script>
	
	<script type="text/javascript">

	function fncAddPurchase() {
	    var name = $("input[name='receiverName']").val();
	    var phone = $("input[name='receiverPhone']").val();
	    var addr = $("input[name='divyAddr']").val();
	    var paymentOption = $("select[name='paymentOption']").val(); // 추가
	
	    if (!name || !phone || !addr) {
	        alert("이름, 연락처, 주소는 반드시 입력해야 합니다.");
	        return;
	    }
	
	    if(paymentOption === "2"){ // 신용구매
	        // 서버에 결제 준비 요청
	        $.ajax({
	            url: '/purchase/kakaoReady',  // 서버 컨트롤러에서 결제 준비
	            method: 'POST',
	            contentType: 'application/json',
	            data: JSON.stringify({
	                prodNo: $("input[name='prodNo']").val(),
	                userId: $("input[name='buyer.userId']").val(),
	                itemName: "${product.prodName}", // JSP에서 출력된 상품명
	                totalAmount: "${product.price}"   // JSP에서 출력된 가격
	            }),
	            success: function(res){
	                // 결제 페이지 URL로 이동
	                if(res.next_redirect_pc_url){
	                    window.location.href = res.next_redirect_pc_url;
	                } else {
	                    alert("결제 준비 중 오류 발생");
	                    console.error(res);
	                }
	            },
	            error: function(err){
	                alert("카카오페이 요청 오류");
	                console.error(err);
	            }
	        });
	    } else {
	        // 현금 구매는 바로 주문 제출
	        $("form[name='addPurchase']")
	            .attr("method", "POST")
	            .attr("enctype", "multipart/form-data")
	            .attr("action", "/purchase/addPurchase")
	            .submit();
	    }
	}
	
	$(function() {
	    $("td.ct_btn01:contains('구매')").on("click", fncAddPurchase);
	    $("td.ct_btn01:contains('취소')").on("click", function(){ history.go(-1); });
	});
	</script>

	
</head>

<body>

<form name="addPurchase">

<table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td width="15" height="37">
            <img src="/images/ct_ttl_img01.gif" width="15" height="37">
        </td>
        <td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="93%" class="ct_ttl01">상품상세조회</td>
                    <td width="20%" align="right">&nbsp;</td>
                </tr>
            </table>
        </td>
        <td width="12" height="37">
            <img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
        </td>
    </tr>
</table>

<input type="hidden" name="prodNo" value="${product.prodNo}" />

<table width="600" border="0" cellspacing="0" cellpadding="0" align="center" style="margin-top: 13px;">
    <tr>
        <td height="1" colspan="3" bgcolor="D6D6D6"></td>
    </tr>
    <tr>
        <td width="300" class="ct_write">상품번호 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/></td>
        <td bgcolor="D6D6D6" width="1"></td>
        <td class="ct_write01" width="299">${product.prodNo}</td>
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <tr>
        <td class="ct_write">상품명 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/></td>
        <td bgcolor="D6D6D6"></td>
        <td class="ct_write01">${product.prodName}</td>
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <tr>
        <td class="ct_write">상품상세정보 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/></td>
        <td bgcolor="D6D6D6"></td>
        <td class="ct_write01">${product.prodDetail}</td>
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <tr>
        <td class="ct_write">제조일자</td>
        <td bgcolor="D6D6D6"></td>
        <td class="ct_write01">${product.manuDate}</td>
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <tr>
        <td class="ct_write">가격</td>
        <td bgcolor="D6D6D6"></td>
        <td class="ct_write01">${product.price}</td>
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <tr>
        <td class="ct_write">등록일자</td>
        <td bgcolor="D6D6D6"></td>
        <td class="ct_write01">${product.regDate}</td>
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <tr>
        <td class="ct_write">구매자아이디 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/></td>
        <td bgcolor="D6D6D6"></td>
        <td class="ct_write01">${purchase.buyer.userId}</td>
        <input type="hidden" name="buyer.userId" value="${purchase.buyer.userId}" />
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <tr>
        <td class="ct_write">구매방법</td>
        <td bgcolor="D6D6D6"></td>
        <td class="ct_write01">
            <select name="paymentOption" class="ct_input_g" style="width: 100px; maxLength="20">
                <option value="1" selected="selected">현금구매</option>
                <option value="2">신용구매</option>
            </select>
        </td>
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <tr>
        <td class="ct_write">구매자이름</td>
        <td bgcolor="D6D6D6"></td>
        <td class="ct_write01">
            <input type="text" name="receiverName" class="ct_input_g" style="width: 100px; height: 19px" maxLength="20" value="${purchase.receiverName}" />
        </td>
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <tr>
        <td class="ct_write">구매자연락처</td>
        <td bgcolor="D6D6D6"></td>
        <td class="ct_write01">
            <input type="text" name="receiverPhone" class="ct_input_g" style="width: 100px; height: 19px" maxLength="20" value=""/>
        </td>
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <tr>
        <td class="ct_write">구매자주소</td>
        <td bgcolor="D6D6D6"></td>
        <td class="ct_write01">
            <input type="text" id="addr" name="divyAddr" class="ct_input_g" style="width: 100px; height: 19px" maxLength="20" value=""/>
            <button type="button" id="btnAddr">우편번호 검색</button>
        </td>
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <tr>
        <td class="ct_write">구매요청사항</td>
        <td bgcolor="D6D6D6"></td>
        <td class="ct_write01">
            <input type="text" name="divyRequest" class="ct_input_g" style="width: 100px; height: 19px" maxLength="20" />
        </td>
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <tr>
        <td class="ct_write">배송희망일자</td>
        <td bgcolor="D6D6D6"></td>
        <td class="ct_write01">
            <input type="text" readonly="readonly" name="divyDate" class="ct_input_g" style="width: 100px; height: 19px" maxLength="20"/>
            <img src="../images/ct_icon_date.gif" width="15" height="15" style="cursor:pointer;"
                 onclick="$('input[name=divyDate]').datepicker('show')"/>
        </td>
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
    <tr>
        <td width="53%"></td>
        <td align="center">
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="17" height="23"><img src="/images/ct_btnbg01.gif" width="17" height="23"/></td>
                    <td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top: 3px;">구매</td>
                    <td width="14" height="23"><img src="/images/ct_btnbg03.gif" width="14" height="23"/></td>
                    <td width="30"></td>
                    <td width="17" height="23"><img src="/images/ct_btnbg01.gif" width="17" height="23"/></td>
                    <td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top: 3px;">취소</td>
                    <td width="14" height="23"><img src="/images/ct_btnbg03.gif" width="14" height="23"/></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</form>

</body>
</html>
