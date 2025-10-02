<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <title>상품 목록조회</title>
  <script src="https://cdn.tailwindcss.com"></script>
  <script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
  
  <script type="text/javascript">
    function fncGetUserList(currentPage) {
      $("#currentPage").val(currentPage);
      $("form").attr("method" , "POST").attr("action" , "/product/listProduct").submit();
    }
    
    $(function() {
      $("#btnSearch").on("click", function() {
        fncGetUserList(1);
      });

   	  // 대표이미지 클릭 시 이동 처리
      $(".prod-img").each(function() {
        var $el = $(this);
        var prodNo = $el.data("prodno");
        var menu = $el.data("menu");
        var proTran = $el.data("protrancode");

        if(menu === "manage") {
	       	$el.css("cursor", "pointer").on("click", function() {
       	      self.location = "/product/updateProduct?prodNo=" + prodNo;
       	    });
        } else {
          if (proTran && proTran.trim() === "재고없음") {
          	$el.css("cursor", "not-allowed");
          } else {
	       	  $el.css("cursor", "pointer").on("click", function() {
	   	        self.location = "/product/getProduct?prodNo=" + prodNo;
	   	      });
          }
        }
      });

      // 상태 처리
      $(".tran-status").each(function() {
        var $td = $(this);
        var proTran = $td.data("protran");
        var tranNo = $td.data("tranno");
        var menu = $td.data("menu");
        
        if(menu === "manage") {
          $td.empty();
          $td.append(proTran);
          if(proTran === "구매완료") {
            var $btn = $("<span>")
              .text(" 배송하기")
              .addClass("inline-block px-3 py-1 ml-2 text-sm font-medium text-white rounded-md cursor-pointer transition")
	          .css({
	          	"background-color": "#3d3d4e",
	          })
              .on("click", function() {
                self.location = "/product/updateTranCode?tranNo=" + tranNo + "&tranCode=2&menu=manage";
              });
            $td.append($btn);
          }
        } else {
          $td.text(proTran === "판매중" ? "판매중" : "재고없음");
        }
      });

    });
  </script>
</head>
<body class="bg-white text-gray-800">
	<div class="w-full min-h-screen px-14 py-12">
	  <div class="max-w-[1200px] mx-auto">
	  	
	  	<!-- 타이틀 -->
		<div class="w-full mb-6">
		  <h2 class="text-2xl font-semibold text-gray-800 border-b-2 pb-2"
		      style="border-color:#3d3d4e;">
		    ${param.menu eq 'manage' ? '상품 관리' : '상품목록 조회'}
		  </h2>
		</div>
	    
	    <!-- 검색 바 -->
	    <form name="detailForm" class="mb-8 flex flex-wrap items-center gap-3">
	      <select name="searchCondition" 
	              class="border border-gray-300 rounded-md px-3 py-2 text-sm focus:outline-none focus:border-[#3d3d4e]">
	        <option value="0" ${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품번호</option>
	        <option value="1" ${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>상품명</option>
	        <option value="2" ${ ! empty search.searchCondition && search.searchCondition==2 ? "selected" : "" }>상품가격</option>
	      </select>
	      
	      <input type="text" name="searchKeyword" 
	             onkeypress="if(event.keyCode==13) fncGetUserList(1);" 
	             value="${! empty search.searchKeyword ? search.searchKeyword : ""}"
	             placeholder="검색어 입력"
	             class="border border-gray-300 rounded-md px-3 py-2 w-64 text-sm focus:outline-none focus:border-[#3d3d4e]"/>
	      
	      <button type="button" id="btnSearch" 
	              class="px-5 py-2 rounded-md text-white text-sm font-medium transition" 
	              style="background-color:#3d3d4e;">
	        검색
	      </button>
	      
	      <input type="hidden" id="currentPage" name="currentPage" value=""/>
	      <input type="hidden" name="menu" value="${param.menu}">
	    </form>
	    
	    <!-- 상단 정보 -->
	    <div class="mb-6 text-sm text-gray-600">
	      전체 <span class="font-semibold">${resultPage.totalCount}</span> 건수, 
	      현재 <span class="font-semibold">${resultPage.currentPage}</span> 페이지
	    </div>
	    
	    <!-- 상품 리스트 그리드 -->
	    <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-8">
	      
	      <c:set var="i" value="0"/>
	      <c:forEach var="product" items="${list}">
	        <c:set var="i" value="${i+1}"/>
	        
	        <div class="product-card group">
	          <!-- 대표 이미지 -->
	          <div class="w-full aspect-[3/4] bg-gray-100 cursor-pointer prod-img rounded-lg overflow-hidden relative"
	          	   data-prodno="${product.prodNo}"
		           data-menu="${param.menu}"
		           data-protrancode="${product.proTranCode}">
	            <img src="/images/uploadFiles/${product.fileName}" 
		             alt="${product.prodName}" 
		             class="w-full h-full object-cover" />
	          </div>
	          
	          <!-- 상품 정보 -->
	          <div class="mt-3 space-y-1 text-sm">
	            <p class="text-gray-500">No. ${i}</p>
	            <h3 class="text-base font-medium text-gray-800">${product.prodName}</h3>
	            <p class="font-semibold text-gray-900">₩${product.price}</p>
	            <p class="text-gray-400 text-xs">등록일: ${product.regDate}</p>
	            <p class="tran-status text-gray-600"
	            	data-protran="${product.getProTranCodeName()}"
	       			data-tranno="${product.tranNo}"
	       			data-menu="${param.menu}">
	       			상태: ${product.getProTranCodeName()}</p>
	          </div>
	        </div>
	      </c:forEach>
	    </div>
	    
	    <!-- 페이지 네비게이션 -->
	    <div class="mt-10 flex justify-center">
	      <div class="inline-flex gap-2">
	        <jsp:include page="../common/pageNavigator.jsp"/>
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>
