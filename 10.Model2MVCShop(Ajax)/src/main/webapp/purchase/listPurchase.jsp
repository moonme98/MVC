<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>구매 목록조회</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
    <script type="text/javascript">
        var currentPage = 1;
        var isLoading = false;
        var hasMoreData = true;

        $(function() {
            // 무한 스크롤 구현
            $(window).on('scroll', function() {
                if (isLoading || !hasMoreData) return;
                
                var scrollHeight = $(document).height();
                var scrollPosition = $(window).height() + $(window).scrollTop();
                
                if ((scrollHeight - scrollPosition) / scrollHeight < 0.1) {
                    loadMorePurchases();
                }
            });

            // 주문상세 버튼 클릭
            $(document).on("click", ".order-detail-btn", function() {
                var tranNo = $(this).data("tranno");
                if(tranNo) {
                    location.href = "/purchase/getPurchase?tranNo=" + tranNo;
                }
            });

            // 물건도착 버튼 클릭
            $(document).on("click", ".arrival-btn", function() {
                var tranNo = $(this).data("tranno");
                if(tranNo) {
                    location.href = "/purchase/updateTranCode?tranNo=" + tranNo + "&tranCode=3";
                }
            });
        });

        function loadMorePurchases() {
            if (isLoading || !hasMoreData) return;
            
            isLoading = true;
            currentPage++;
            
            $.ajax({
                url: '/purchase/listPurchase',
                type: 'POST',
                data: { currentPage: currentPage },
                success: function(response) {
                    var newItems = $(response).find('.purchase-item');
                    
                    if (newItems.length === 0) {
                        hasMoreData = false;
                        $('#loading-indicator').hide();
                    } else {
                        $('#purchase-list').append(newItems);
                    }
                    
                    isLoading = false;
                },
                error: function() {
                    isLoading = false;
                    alert('데이터를 불러오는데 실패했습니다.');
                }
            });
        }
    </script>
</head>
<body class="bg-white">
    <div class="max-w-5xl mx-auto px-4 py-8">
        <h1 class="text-2xl font-bold mb-8">주문 내역</h1>
        
        <div id="purchase-list">
            <c:forEach var="purchase" items="${list}">
                <div class="purchase-item mb-8 border-t border-gray-200 pt-6">
                    <!-- 날짜 및 주문상세 버튼 -->
                    <div class="flex justify-between items-center mb-4">
                        <div class="text-sm text-gray-600">
                            ${purchase.orderDate}
                        </div>
                        <button class="order-detail-btn text-sm border border-gray-300 px-4 py-2 hover:bg-gray-50" 
                                data-tranno="${purchase.tranNo}">
                            주문상세
                        </button>
                    </div>

                    <!-- 상품 정보 -->
                    <div class="flex gap-4">
                        <!-- 썸네일 -->
                        <div class="flex-shrink-0">
                            <img src="/images/uploadFiles/${purchase.purchaseProd.fileName}" 
                                 alt="${purchase.purchaseProd.fileName}"  
                                 class="w-24 h-24 object-cover">
                        </div>

                        <!-- 상품 상세 -->
                        <div class="flex-1">
                            <h3 class="font-medium mb-1">${purchase.purchaseProd.prodName}</h3>
                            <p class="text-sm text-gray-600 mb-2 line-clamp-2">${purchase.purchaseProd.prodDetail}</p>
                            <p class="font-semibold">${purchase.purchaseProd.price}원</p>
                        </div>

                    </div>

                    <!-- 배송현황 & 정보수정 -->
                    <div class="mt-4 pt-4 border-t border-gray-100 flex gap-3">
                        <div class="flex-1 text-sm text-gray-700">
                            배송현황: <span class="font-medium">${purchase.tranCodeName}</span>
                        </div>
                        <div>
                            <c:if test="${purchase.tranCode.trim() eq '2'}">
                                <button class="arrival-btn text-sm bg-black text-white px-6 py-2 hover:bg-gray-800"
                                        data-tranno="${purchase.tranNo}">
                                    구매확정
                                </button>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <!-- 로딩 인디케이터 -->
        <div id="loading-indicator" class="text-center py-8">
            <div class="inline-block w-8 h-8 border-4 border-gray-300 border-t-black rounded-full animate-spin"></div>
        </div>
    </div>
</body>
</html>