<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>

<head>
	<title>상품 상세조회</title>
	
	<!-- TailwindCSS -->
  	<script src="https://cdn.tailwindcss.com"></script>
	
	<!-- jQuery & jQuery UI -->
	<link rel="stylesheet" href="/css/admin.css" type="text/css">
	
	<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
	<script type="text/javascript">
	
		const menu = "<c:out value='${sessionScope.menu}' />";
		$(function() {
			$(".ct_btn01:contains('목록으로 가기')").on("click", function() {
			    console.log("menu=", menu);
			    location.href = "/product/listProduct?menu=" + menu;
			});
			
			$( ".ct_btn01:contains('바로 구매하기')" ).on("click" , function() {
				console.log("${product.prodNo}");
				self.location = "/purchase/addPurchase?prodNo=${product.prodNo}";

			});
		});

	</script>
</head>

<body bgcolor="#ffffff" text="#000000">

	<div class="max-w-[1200px] mx-auto flex flex-col md:flex-row gap-10 py-12 px-6">
	  	<!-- 이미지 슬라이더 -->
	    <div class="md:w-1/2 flex flex-col items-center">
	        <c:if test="${not empty product.fileName}">
	            <c:set var="images" value="${fn:split(product.fileName, ',')}"/>
	
	            <!-- 메인 슬라이더 -->
	            <div id="main-slider" class="w-full max-w-md relative overflow-hidden rounded-md border">
	                <div id="slider-inner" class="flex transition-transform duration-300">
				        <c:forEach var="fname" items="${images}">
				            <img src="/images/uploadFiles/${fname}" alt="상품 이미지"
				                 class="flex-shrink-0 w-full object-cover rounded-md border">
				        </c:forEach>
				    </div>
	
	                <!-- 이전/다음 버튼 -->
	                <button id="prevBtn"
	                        class="absolute top-1/2 left-2 -translate-y-1/2 bg-gray-200 text-gray-700 px-2 py-1 rounded hover:bg-gray-300">
	                    &#10094;
	                </button>
	                <button id="nextBtn"
	                        class="absolute top-1/2 right-2 -translate-y-1/2 bg-gray-200 text-gray-700 px-2 py-1 rounded hover:bg-gray-300">
	                    &#10095;
	                </button>
	            </div>
	
	            <!-- 썸네일 -->
	            <div id="thumbnail-bar" class="flex mt-2 gap-2 overflow-x-auto">
				    <c:forEach var="fname" items="${images}" varStatus="status">
				        <img src="/images/uploadFiles/${fname}" class="thumbnail w-20 h-20 cursor-pointer"
				             data-index="${status.index}">
				    </c:forEach>
				</div>
	        </c:if>
	    </div>
	
	  <!-- 상품 정보 영역 -->
	  <div class="md:w-1/2 flex flex-col gap-4">
	    <!-- 상품명 -->
	    <h2 class="text-xl md:text-2xl font-semibold text-gray-900 border-b pb-2">
	      ${product.prodName}
	    </h2>
	
	    <!-- 가격 -->
	    <div class="flex flex-col gap-1">
	      <span class="text-gray-400 line-through text-sm">18,000원</span>
	      <span class="text-lg font-bold text-gray-900">${product.price} <span class="text-red-500 font-semibold text-base">(50%)</span></span>
	    </div>
	
	    <!-- 적립금, 무이자 할부, 배송정보, 배송비 -->
	    <div class="flex flex-col gap-2 text-sm text-gray-700">
	      <div><span class="font-semibold">상품번호:</span> ${product.prodNo}</div>
	      <div><span class="font-semibold">제조일자:</span> ${product.manuDate}</div>
	      <div><span class="font-semibold">등록일자:</span> ${product.regDate}</div>
	      
	      <div><span class="font-semibold">배송비:</span> 3,000원 (70,000원 이상 구매 시 무료배송)</div>
	    </div>
	
	   <!-- 상품상세정보 -->
	   <div class="mt-6">
	    <h3 class="text-base font-semibold text-gray-900 mb-2 flex items-center gap-1">
	      상품상세정보 
	      <img src="/images/ct_icon_red.gif" width="3" height="3" alt="필수">
	    </h3>
	    <div class="text-sm text-gray-700 leading-relaxed border-t pt-3">
	      ${product.prodDetail}
	    </div>
	   </div>
	
	    <!-- 버튼 -->
	    <div class="flex gap-3 mt-4">
	      <button type="button" class="ct_btn01 flex-1 border border-gray-900 text-gray-900 font-semibold py-2 rounded hover:bg-gray-100 transition">
	        목록으로 가기
	      </button>
	      <button type="button" class="ct_btn01 flex-1 bg-black text-white font-semibold py-2 rounded hover:bg-gray-800 transition">
	        바로 구매하기
	      </button>
	    </div>
	  </div>
	</div>
	
	<script>
	window.addEventListener('load', () => {
	    const slider = document.getElementById('slider-inner');
	    const slides = slider.children;
	    let idx = 0;

	    // 슬라이드 너비 계산
	    const slideWidth = slides[0].offsetWidth;

	    const updateSlider = () => {
	        slider.style.transform = `translateX(-${idx * slideWidth}px)`;
	    };

	    document.getElementById('prevBtn').addEventListener('click', () => {
	        idx = (idx === 0) ? slides.length - 1 : idx - 1;
	        updateSlider();
	    });

	    document.getElementById('nextBtn').addEventListener('click', () => {
	        idx = (idx === slides.length - 1) ? 0 : idx + 1;
	        updateSlider();
	    });

	    document.querySelectorAll('.thumbnail').forEach((thumb, i) => {
	        thumb.addEventListener('click', () => {
	            idx = i;
	            updateSlider();
	        });
	    });
	});
	</script>
</body>
</html>