<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <title>상품 목록조회</title>
  
  <script src="https://cdn.tailwindcss.com"></script>
  
  <script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
$(function() {

    // === 1. 검색 ===
    function fncGetUserList(currentPage) {
        $("#currentPage").val(currentPage);
        $("form").attr("method", "POST").attr("action", "/product/listProduct").submit();
    }

    $("#btnSearch").on("click", function() {
        fncGetUserList(1);
    });

    // === 2. 자동완성 ===
    $("#searchBox").on("keyup", function () {
        let keyword = $(this).val().trim();
        if (keyword.length < 1) {
            $("#autoList").empty().hide();
            return;
        }

        let searchData = {
            searchCondition: $("select[name='searchCondition']").val() || "0",
            searchKeyword: keyword,
            currentPage: 1,
            pageSize: 10
        };

        console.log("검색 요청 데이터:", searchData);

        $.ajax({
            url: "/product/json/getProductList",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(searchData),
            success: function(response) {
                console.log("서버 응답:", response);
                let list = $("#autoList").empty();
                let items = response.list;

                if (items && items.length > 0) {
                    items.forEach(function(item) {
                        let liHtml = "<li data-id='" + item.prodNo + "' " +
                                     "class='px-3 py-2 text-gray-500 text-sm hover:bg-gray-100 hover:text-gray-900 cursor-pointer transition-colors'>" +
                                     item.prodName +
                                     "</li>";
                        list.append(liHtml);
                    });
                    list.show();
                } else {
                    list.hide();
                }
            },
            error: function(xhr, status, error) {
                console.error("자동완성 오류:", status, error);
            }
        });
    });

    $(document).on("click", "#autoList li", function () {
        let prodNo = $(this).data("id");
        window.location.href = "/product/getProduct?prodNo=" + prodNo;
    });

    $(document).on("click", function(e) {
        if(!$(e.target).closest("#searchBox, #autoList").length) {
            $("#autoList").hide();
        }
    });

    // === 3. 대표 이미지 클릭 ===
    function bindImageClick($container) {
        $container.find(".prod-img").each(function() {
            var $el = $(this);
            var prodNo = $el.data("prodno");
            var menu = $el.data("menu");
            var proTran = $el.data("protrancode");

            if(menu === "manage") {
                $el.css("cursor", "pointer").off("click").on("click", function() {
                    self.location = "/product/updateProduct?prodNo=" + prodNo;
                });
            } else {
                if (proTran && proTran.trim() === "재고없음") {
                    $el.css("cursor", "not-allowed").off("click");
                } else {
                    $el.css("cursor", "pointer").off("click").on("click", function() {
                        self.location = "/product/getProduct?prodNo=" + prodNo;
                    });
                }
            }
        });
    }

    bindImageClick($(".grid"));

    // === 4. 무한스크롤 + 클론 노드 + 상태/배송 버튼/등록일 ===
    let loading = false;
    let currentPage = 1;
    const pageSize = 10;

    $(window).on("scroll", function() {
        if ($(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
            if (loading) return;
            loading = true;
            currentPage++;

            console.log("무한스크롤 요청 페이지:", currentPage);

            let searchData = {
                searchCondition: $("select[name='searchCondition']").val() || "0",
                searchKeyword: $("#searchBox").val().trim(),
                currentPage: currentPage,
                pageSize: pageSize
            };

            $.ajax({
                url: "/product/json/getProductList",
                method: "POST",
                contentType: "application/json",
                data: JSON.stringify(searchData),
                success: function(response) {
                    console.log("서버 응답(무한스크롤):", response);
                    let items = response.list;
                    if (!items || items.length === 0) {
                        console.log("더 이상 로드할 데이터 없음");
                        return;
                    }

                    let menu = $("input[name='menu']").val();
                    items.forEach(function(item) {
                        let $clone = $(".product-card.template").clone().removeClass("template hidden");

                        // 이미지
                        $clone.find(".prod-img").attr({
                            "data-prodno": item.prodNo,
                            "data-menu": menu,
                            "data-protrancode": item.proTranCode
                        });
                        $clone.find(".prod-img img").attr("src", "/images/uploadFiles/" + item.fileName)
                                                     .attr("alt", item.prodName);

                        // 기본 정보
                        $clone.find(".product-no").text("No. " + ($(".product-card").not(".template").length + 1));
                        $clone.find(".prod-name").text(item.prodName);
                        $clone.find(".prod-price").text("₩" + item.price);

                        // 등록일 변환
                        let regDate = new Date(item.regDate);
                        let formattedDate = regDate.getFullYear() + "-" +
                                            String(regDate.getMonth() + 1).padStart(2, '0') + "-" +
                                            String(regDate.getDate()).padStart(2, '0');
                        $clone.find(".prod-date").text("등록일: " + formattedDate);

                        // 상태 + 배송 버튼
                        let $status = $clone.find(".tran-status").attr({
                            "data-protran": item.proTranCodeName,
                            "data-tranno": item.tranNo,
                            "data-menu": menu
                        }).text("상태: " + item.proTranCodeName);

                        if(menu === "manage" && item.proTranCodeName === "구매완료") {
                            let $btn = $("<span>")
                                .text(" 배송하기")
                                .addClass("inline-block px-3 py-1 ml-2 text-sm font-medium text-white rounded-md cursor-pointer transition")
                                .css("background-color", "#3d3d4e")
                                .on("click", function() {
                                    self.location = "/product/updateTranCode?tranNo=" + item.tranNo + "&tranCode=2&menu=manage";
                                });
                            $status.append($btn);
                        }

                        $(".grid").append($clone);
                        bindImageClick($clone); // 새로 추가된 카드에도 이미지 클릭 바인딩
                    });
                },
                error: function(xhr, status, error) {
                    console.error("무한스크롤 AJAX 오류:", status, error);
                },
                complete: function() {
                    loading = false;
                }
            });
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
	      
	      <div class="relative w-64">
			  <input type="text" name="searchKeyword" id="searchBox"
			         onkeypress="if(event.keyCode==13) fncGetUserList(1);" 
			         value="${! empty search.searchKeyword ? search.searchKeyword : ''}"
			         placeholder="검색어 입력"
			         class="border border-gray-300 rounded-md px-3 py-2 w-full text-sm focus:outline-none focus:border-[#3d3d4e]"/>
			  <ul id="autoList" 
			  	  class="border mt-1 bg-white absolute w-64 z-10 rounded-md shadow"
			  	  style="display:none;"></ul>
		  </div>
	      
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
	      
	      <!-- 템플릿 카드 (숨김) -->
		  <div class="product-card template hidden">
		    <div class="w-full aspect-[3/4] bg-gray-100 cursor-pointer prod-img rounded-lg overflow-hidden relative">
		      <img src="" alt="" class="w-full h-full object-cover" />
		    </div>
		    <div class="mt-3 space-y-1 text-sm">
		      <p class="text-gray-500 product-no">No.</p>
		      <h3 class="text-base font-medium text-gray-800 prod-name"></h3>
		      <p class="font-semibold text-gray-900 prod-price"></p>
		      <p class="text-gray-400 text-xs prod-date"></p>
		      <p class="tran-status text-gray-600"
		         data-protran=""
		         data-tranno=""
		         data-menu="">
		         상태:
		      </p>
		    </div>
		  </div>
  
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
	   <%--  <div class="mt-10 flex justify-center">
	      <div class="inline-flex gap-2">
	        <jsp:include page="../common/pageNavigator.jsp"/>
	      </div>
	    </div> --%>
	  </div>
	</div>
</body>
</html>
