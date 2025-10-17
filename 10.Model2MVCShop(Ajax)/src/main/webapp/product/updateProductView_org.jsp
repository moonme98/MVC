<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>

<head>
	<title>상품수정</title>
	
	<!-- TailwindCSS -->
  	<script src="https://cdn.tailwindcss.com"></script>
  	
  	<!-- jQuery & jQuery UI -->
	<link rel="stylesheet" href="/css/admin.css" type="text/css">
	
	<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
	<script type="text/javascript">
	
		function fncUpdateProduct(){
			//Form 유효성 검증
		 	//var name = document.detailForm.prodName.value;
			//var detail = document.detailForm.prodDetail.value;
			//var manuDate = document.detailForm.manuDate.value;
			//var price = document.detailForm.price.value;
			
			var name=$("input[name='prodName']").val();
			var detail=$("textarea[name='prodDetail']").val();
			var manuDate=$("input[name='manuDate']").val();
			var price=$("input[name='price']").val();
		
/* 			if(name == null || name.length<1){
				alert("상품명은 반드시 입력하여야 합니다.");
				return;
			}
			if(detail == null || detail.length<1){
				alert("상품상세정보는 반드시 입력하여야 합니다.");
				return;
			}
			if(manuDate == null || manuDate.length<1){
				alert("제조일자는 반드시 입력하셔야 합니다.");
				return;
			}
			if(price == null || price.length<1){
				alert("가격은 반드시 입력하셔야 합니다.");
				return;
			} */
				
			//document.detailForm.action='/product/updateProduct';
			//document.detailForm.submit();
			$("form[name='detailForm']").attr("method" , "POST").attr("enctype" , "multipart/form-data").attr("action" , "/product/updateProduct").submit();
		}
		
		//==> 추가된부분 : "취소" "수정"  Event 연결 및 처리
		$(function() {
			$( "td.ct_btn01:contains('취소')" ).on("click" , function() {
				//Debug..
				//alert(  $( "td.ct_btn01:contains('취소')" ).html() );
				history.go(-1);
			});
			
			$( ".btn-update" ).on("click" , function() {
				//Debug..
				//alert(  $( "td.ct_btn01:contains('수정')" ).html() );
				fncUpdateProduct();			
			});
		});
		
		// 캘린더
		$(function() {
	      $("input[name='manuDate']").datepicker({
	        dateFormat: "yy-mm-dd"
	      });
	    });

	</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<form name="detailForm">

	<input type="hidden" name="prodNo" value="${product.prodNo }"/>
	
	<div class="w-full min-h-screen bg-white px-14 py-12">
	  <div class="w-full max-w-[800px] space-y-6">
	
	    <!-- 제목 -->
	    <h2 class="text-2xl font-semibold text-gray-800 mb-6 border-b-2 pb-2" style="border-color:#3d3d4e;">
	      상품수정
	    </h2>
	
	    <!-- 상품명 -->
	    <input type="text" name="prodName" value="${product.prodName}" placeholder="상품명 *"
	           class="w-full border-b border-gray-300 px-2 py-3 text-sm text-gray-600 placeholder-gray-400 focus:outline-none focus:border-[#3d3d4e]" />
	
	    <!-- 상품상세정보 -->
	    <textarea name="prodDetail" placeholder="상품상세정보 *" rows="4"
	              class="w-full border-b border-gray-300 px-2 py-3 text-sm text-gray-600 placeholder-gray-400 focus:outline-none focus:border-[#3d3d4e]">${product.prodDetail}</textarea>
	
	    <!-- 가격 -->
	    <input type="number" name="price" value="${product.price}" placeholder="가격 (원) *"
	           class="w-full border-b border-gray-300 px-2 py-3 text-sm text-gray-600 placeholder-gray-400 focus:outline-none focus:border-[#3d3d4e]" />
	
	    <!-- 제조일자 -->
	    <input type="date" name="manuDate" value="${product.manuDate}" placeholder="yy-mm-dd *"
	           class="w-full border-b border-gray-300 px-2 py-3 text-sm text-gray-600 placeholder-gray-400 focus:outline-none focus:border-[#3d3d4e]" />
	
	    <!-- 상품이미지 -->
	    <div>
	      <label for="file-upload" 
	             class="flex flex-col items-center justify-center w-full h-28 border border-gray-300 rounded-md cursor-pointer hover:border-[#3d3d4e] hover:bg-gray-50 transition">
	        <span class="text-sm text-gray-400">이미지 업로드</span>
	      </label>
	      <input type="file" id="file-upload" name="uploadFiles" class="hidden" accept="image/*" multiple />
	      <!-- 미리보기 -->
	      <div id="preview" class="mt-3 flex gap-3">
	        <c:if test="${not empty product.fileName}">
	        	<c:forEach var="fname" items="${fn:split(product.fileName, ',')}">
	        		<img src="/images/uploadFiles/${fname}" alt="상품 이미지" class="h-24 rounded-md border" />
	        	</c:forEach>
	        </c:if>
	      </div>
	    </div>
	
	    <!-- 버튼 -->
	    <div class="flex justify-end gap-3 pt-6">
	      <button type="button" onclick="history.back()"
	              class="px-5 py-2 rounded-md border border-gray-300 text-sm text-gray-500 hover:bg-gray-50 transition">
	        취소
	      </button>
	      <button type="button"
	              class="btn-update px-5 py-2 rounded-md text-white text-sm font-medium transition"
	              style="background-color:#3d3d4e;">
	        수정
	      </button>
	    </div>
	
	  </div>
	</div>
	
	<script>
	  // 파일 미리보기
	  const fileInput = document.getElementById('file-upload');
	  const preview = document.getElementById('preview');
	
	  fileInput.addEventListener('change', () => {
	    preview.innerHTML = '';
	    if (fileInput.files.length > 0) {
	      [...fileInput.files].forEach(file => {
	        const reader = new FileReader();
	        reader.onload = (e) => {
	          const img = document.createElement('img');
	          img.src = e.target.result;
	          img.className = "h-24 rounded-md border";
	          preview.appendChild(img);
	        };
	        reader.readAsDataURL(file);
	      });
	    }
	  });
	</script>

</form>

</body>
</html>