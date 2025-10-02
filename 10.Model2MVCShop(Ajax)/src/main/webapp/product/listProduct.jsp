<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>상품 목록조회</title>

  <script src="https://cdn.tailwindcss.com"></script>
  <script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>

  <style>
    .sold-tag {
      background: rgba(255,255,255,0.88);
      border-radius: 999px;
      padding: 6px 10px;
      color: #ff8a8a;
      font-weight: 600;
      box-shadow: 0 1px 6px rgba(0,0,0,0.08);
      font-size: 0.9rem;
    }

    .img-blur {
      filter: blur(3px);
      opacity: 0.75;
    }

    .hidden-overlay { display: none; }
  </style>

  <script type="text/javascript">
  $(function() {

	    // --- 카드 상태 처리 함수 ---
	    // $card : .product-card 요소
	    // itemData : AJAX에서 가져온 데이터 객체 (없으면 DOM data-* 사용)
	    function applyCardState($card, itemData) {
	        const $imgWrap = $card.find(".prod-img");  // 카드 내 이미지 래퍼
	        const $img = $imgWrap.find("img").first(); // 이미지 요소
	        const menu = $("input[name='menu']").val() || $imgWrap.data("menu"); // 메뉴 정보
	        const proTranName = itemData 
	                            ? (itemData.proTranCodeName || itemData.proTranCode || "") 
	                            : ($imgWrap.data("protran") || $imgWrap.data("protrancode") || ""); // 상태명
	        const tranNo = itemData 
	                       ? itemData.tranNo 
	                       : ($imgWrap.data("tranno") || $card.find(".tran-status").data("tranno")); // 거래번호

	        const status = (proTranName || "").toString().trim(); // 상태 문자열

	        // 회원일 경우: SOLD OUT 처리
	        if (menu !== "manage") {
	            $card.find(".tran-status").hide(); // 상태 텍스트 숨기기

	            if (status === "구매완료" || status === "재고없음") {
	                $img.addClass("img-blur"); // 이미지 블러 처리

	                let $overlay = $imgWrap.find(".sold-overlay"); // SOLD OUT 오버레이
	                if ($overlay.length === 0) {
	                    $overlay = $('<div class="sold-overlay absolute inset-0 flex items-center justify-center"></div>');
	                    $imgWrap.append($overlay);
	                }
	                $overlay.removeClass("hidden-overlay").html('<span class="sold-tag">SOLD OUT</span>');

	                $imgWrap.off("click"); // 클릭 비활성화
	            } else {
	                $img.removeClass("img-blur"); // 이미지 정상
	                $imgWrap.find(".sold-overlay").addClass("hidden-overlay"); // 오버레이 숨김
	            }

	        } else { // 관리자인 경우
	            $card.find(".tran-status").show(); // 상태 텍스트 보이기
	            $img.removeClass("img-blur"); 
	            $imgWrap.find(".sold-overlay").addClass("hidden-overlay"); 

	            if (itemData && status) {
	                $card.find(".tran-status").text("상태: " + status); // 상태 텍스트 설정
	            }

	            // 기존 배송 버튼 제거 후 추가 (중복 방지)
	            $card.find(".manage-ship-btn").remove();
	            if (status === "구매완료") {
	                const $btn = $('<span class="manage-ship-btn inline-block px-3 py-1 mt-2 text-sm font-medium text-white rounded-md cursor-pointer transition">배송하기</span>');
	                $btn.css("background-color", "#3d3d4e");
	                $btn.on("click", function(e){
	                    e.stopPropagation(); // 이미지 클릭 이벤트 방지
	                    window.location.href = "/product/updateTranCode?tranNo=" + (tranNo || '') + "&tranCode=2&menu=manage";
	                });
	                $card.find(".prod-info").append($btn);
	            }
	        }
	    } // applyCardState

	    // --- 이미지 클릭 바인딩 함수 ---
	    // $container : 카드 컨테이너
	    function bindImageClick($container) {
	        $container.find(".prod-img").each(function() {
	            var $el = $(this);
	            var prodNo = $el.data("prodno");
	            var menu = $el.data("menu");
	            var proTran = $el.data("protran") || $el.data("protrancode") || "";
	            proTran = proTran ? proTran.toString().trim() : "";

	            $el.off("click"); // 기존 클릭 해제

	            if(menu === "manage") {
	                $el.css("cursor", "pointer").on("click", function() {
	                    window.location.href = "/product/updateProduct?prodNo=" + prodNo;
	                });
	            } else {
	                if (proTran === "재고없음" || proTran === "구매완료") {
	                    $el.css("cursor", "default"); // 클릭 불가
	                } else {
	                    $el.css("cursor", "pointer").on("click", function() {
	                        window.location.href = "/product/getProduct?prodNo=" + prodNo;
	                    });
	                }
	            }
	        });
	    }

	    // --- 초기 페이지 로드 시 기존 카드 상태 적용 ---
	    $(".product-card").not(".template").each(function() {
	        const $card = $(this);
	        applyCardState($card, null); // DOM data-* 사용
	    });
	    bindImageClick($(".grid")); // 클릭 이벤트 바인딩
		
	    // --- 자동완성 기능 ---
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

	        $.ajax({
	            url: "/product/json/getProductList",
	            method: "POST",
	            contentType: "application/json",
	            data: JSON.stringify(searchData),
	            success: function(response) {
	                let list = $("#autoList").empty();
	                let items = response.list || [];

	                if (items.length > 0) {
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
	                console.error("자동완성 AJAX 오류:", status, error);
	            }
	        });
	    });

	    // --- 자동완성 아이템 클릭 ---
	    $(document).on("click", "#autoList li", function () {
	        let prodNo = $(this).data("id");
	        window.location.href = "/product/getProduct?prodNo=" + prodNo;
	    });

	    // --- 자동완성 외부 클릭 시 닫기 ---
	    $(document).on("click", function(e) {
	        if(!$(e.target).closest("#searchBox, #autoList").length) {
	            $("#autoList").hide();
	        }
	    });
	    
	    // --- 무한 스크롤 + 카드 클론 ---
	    let loading = false;
	    let currentPage = 1;
	    const pageSize = 10;

	    $(window).on("scroll", function() {
	        if ($(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
	            if (loading) return;
	            loading = true;
	            currentPage++;

	            const searchData = {
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
	                    const items = response.list || [];
	                    if (items.length === 0) {
	                    	if ($("#endMessage").length === 0) {
	                    	    const $msg = $(`
	                    	        <div id="endMessage" class="w-full flex justify-center mt-20 mb-8">
	                    	            <div class="bg-white border border-gray-200 shadow-sm rounded-lg px-6 py-3 flex items-center gap-3">
	                    	                <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
	                    	                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M12 2a10 10 0 100 20 10 10 0 000-20z" />
	                    	                </svg>
	                    	                <span class="text-gray-500 text-sm font-medium">마지막 상품입니다.</span>
	                    	            </div>
	                    	        </div>
	                    	    `);
	                    	    $(".grid").after($msg);
	                    	}


	                        loading = false; 
	                        return; 
	                    }

	                    const menu = $("input[name='menu']").val();

	                    items.forEach(function(item) {
	                        // 템플릿 카드 클론
	                        const $clone = $(".product-card.template").clone().removeClass("template hidden");

	                        // data-* 설정
	                        $clone.find(".prod-img").attr({
	                            "data-prodno": item.prodNo,
	                            "data-menu": menu,
	                            "data-protrancode": item.proTranCode || "",
	                            "data-protran": item.proTranCodeName || "",
	                            "data-tranno": item.tranNo || ""
	                        });

	                        // 이미지 src, alt 설정
	                        $clone.find(".prod-img img").attr("src", "/images/uploadFiles/" + (item.fileName || "") )
	                                                    .attr("alt", item.prodName || "");

	                        // 텍스트 필드
	                        $clone.find(".product-no").text("No. " + ($(".product-card").not(".template").length + 1));
	                        $clone.find(".prod-name").text(item.prodName || "");
	                        $clone.find(".prod-price").text("₩" + (item.price || ""));

	                        // 등록일 형식 변환
	                        let regText = "";
	                        if (item.regDate) {
	                            const d = new Date(item.regDate);
	                            if (!isNaN(d)) {
	                                regText = d.getFullYear() + "-" + String(d.getMonth()+1).padStart(2,"0") + "-" + String(d.getDate()).padStart(2,"0");
	                            }
	                        }
	                        $clone.find(".prod-date").text("등록일: " + regText);

	                        // tran-status 설정
	                        $clone.find(".tran-status").attr({
	                          "data-protran": item.proTranCodeName || "",
	                          "data-tranno": item.tranNo || "",
	                          "data-menu": menu
	                        }).text("상태: " + (item.proTranCodeName || ""));

	                        // 카드 UI 적용
	                        applyCardState($clone, item);

	                        // DOM에 추가 후 클릭 이벤트 바인딩
	                        $(".grid").append($clone);
	                        bindImageClick($clone);
	                    });

	                },
	                error: function(xhr, status, error) {
	                    console.error("무한 스크롤 AJAX 오류:", status, error);
	                },
	                complete: function() {
	                    loading = false;
	                }
	            });
	        }
	    });

	    // --- 검색 버튼 클릭 처리 ---
	    $("#btnSearch").on("click", function() {
	        $("#currentPage").val(1);
	        $("form").attr("method" , "POST").attr("action" , "/product/listProduct").submit();
	    });

	}); // end $(function)

  </script>
</head>

<body class="bg-white text-gray-800">
  <div class="w-full min-h-screen px-14 py-12">
    <div class="max-w-[1200px] mx-auto">

      <!-- 타이틀 -->
      <div class="w-full mb-6">
        <h2 class="text-2xl font-semibold text-gray-800 border-b-2 pb-2" style="border-color:#3d3d4e;">
          ${param.menu eq 'manage' ? '상품 관리' : '상품목록 조회'}
        </h2>
      </div>

      <!-- 검색 바 -->
      <form name="detailForm" class="mb-8 flex flex-wrap items-center gap-3">
        <select name="searchCondition" class="border border-gray-300 rounded-md px-3 py-2 text-sm focus:outline-none focus:border-[#3d3d4e]">
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
          <ul id="autoList" class="border mt-1 bg-white absolute w-64 z-10 rounded-md shadow" style="display:none;"></ul>
        </div>

        <button type="button" id="btnSearch" class="px-5 py-2 rounded-md text-white text-sm font-medium transition" style="background-color:#3d3d4e;">
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
      <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-8 grid-flow-row auto-rows-max grid" id="productGrid">
        
        <!-- 템플릿 카드 (숨김) -->
        <div class="product-card template hidden">
          <div class="w-full aspect-[3/4] bg-gray-100 cursor-pointer prod-img rounded-lg overflow-hidden relative"
               data-prodno="" data-menu="" data-protrancode="" data-protran="" data-tranno="">
            <img src="" alt="" class="w-full h-full object-cover" />
            <!-- overlay placeholder (hidden initially) -->
            <div class="sold-overlay hidden-overlay absolute inset-0 flex items-center justify-center"></div>
          </div>
          <div class="mt-3 space-y-1 text-sm prod-info">
            <p class="text-gray-500 product-no">No.</p>
            <h3 class="text-base font-medium text-gray-800 prod-name"></h3>
            <p class="font-semibold text-gray-900 prod-price"></p>
            <p class="text-gray-400 text-xs prod-date"></p>
            <p class="tran-status text-gray-600" data-protran="" data-tranno="" data-menu="">상태:</p>
          </div>
        </div>

        <!-- 기존(초기) 렌더링된 카드들 -->
        <c:set var="i" value="0"/>
        <c:forEach var="product" items="${list}">
          <c:set var="i" value="${i+1}"/>
          
          <div class="product-card group">
            <!-- 대표 이미지 -->
            <div class="w-full aspect-[3/4] bg-gray-100 cursor-pointer prod-img rounded-lg overflow-hidden relative"
                 data-prodno="${product.prodNo}"
                 data-menu="${param.menu}"
                 data-protrancode="${product.proTranCode}"
                 data-protran="${product.getProTranCodeName()}"
                 data-tranno="${product.tranNo}">
              <img src="/images/uploadFiles/${product.fileName}" 
                   alt="${product.prodName}" 
                   class="w-full h-full object-cover" />
              <!-- overlay element (initially hidden; JS will show for members) -->
              <div class="sold-overlay hidden-overlay absolute inset-0 flex items-center justify-center"></div>
            </div>
            
            <!-- 상품 정보 -->
            <div class="mt-3 space-y-1 text-sm prod-info">
              <p class="text-gray-500 product-no">No. ${i}</p>
              <h3 class="text-base font-medium text-gray-800 prod-name">${product.prodName}</h3>
              <p class="font-semibold text-gray-900 prod-price">₩${product.price}</p>
              <p class="text-gray-400 text-xs prod-date">
                등록일: <fmt:formatDate value="${product.regDate}" pattern="yyyy-MM-dd"/>
              </p>

              <!-- 상태: only shown for manage view (admin) -->
              <c:if test="${param.menu == 'manage'}">
                <p class="tran-status text-gray-600"
                   data-protran="${product.getProTranCodeName()}"
                   data-tranno="${product.tranNo}"
                   data-menu="${param.menu}">
                   상태: ${product.getProTranCodeName()}
                </p>
              </c:if>

            </div>
          </div>
        </c:forEach>

      </div>

      <!-- (기존 페이지 네비, 숨김 형태 유지) -->
      <%-- 페이지 내비게이션 생략 --%>

    </div>
  </div>
</body>
</html>
