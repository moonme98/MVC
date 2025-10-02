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
            $("form[name='detailForm']")
                .attr("method", "POST")
                .attr("enctype", "multipart/form-data")
                .attr("action", "/product/updateProduct")
                .submit();
        }

        // 취소/수정 버튼 이벤트
        $(function() {
            $("td.ct_btn01:contains('취소')").on("click", function() { history.go(-1); });
            $(".btn-update").on("click", fncUpdateProduct);
        });

        // 캘린더
        $(function() {
            $("input[name='manuDate']").datepicker({ dateFormat: "yy-mm-dd" });
        });

        // 기존 + 새로 추가 이미지 통합 삭제
        function deleteImage(btn){
	        const wrapper = btn.parentElement;
	        const isExisting = wrapper.dataset.existing === "true"; 
	        const fileName = wrapper.dataset.fname || "";
	        const prodNo = $("input[name='prodNo']").val();
	
	        if(isExisting){
	            if(confirm("이 이미지를 삭제하시겠습니까?")){
	                $.ajax({
	                    url: '/product/deleteProductImage',
	                    type: 'POST',
	                    data: { prodNo: prodNo, fileName: fileName },
	                    success: function(result){
	                        if(result === "SUCCESS"){
	                            wrapper.remove();
	                        } else {
	                            alert("삭제에 실패했습니다.");
	                        }
	                    },
	                    error: function(){
	                        alert("서버 오류로 삭제에 실패했습니다.");
	                    }
	                });
	            }
	        } else {
	            // 새로 추가한 파일 삭제
	            wrapper.remove();
	            // 삭제된 새 파일명을 hidden input에 추가
	            const deletedFilesInput = document.getElementById("deleteNewFiles");
	            deletedFilesInput.value += fileName + ",";
	        }
	    }
	
	    // 파일 미리보기 + data-fname 설정
	    function addFilePreview(file){
	        const reader = new FileReader();
	        reader.onload = e => {
	            const wrapper = document.createElement('div');
	            wrapper.className = 'relative w-24 h-24';
	            wrapper.dataset.existing = "false"; 
	            wrapper.dataset.fname = file.name; // 삭제 관리용
	
	            const img = document.createElement('img');
	            img.src = e.target.result;
	            img.className = 'w-full h-full rounded-md border object-cover';
	            wrapper.appendChild(img);
	
	            const btn = document.createElement('button');
	            btn.type = 'button';
	            btn.innerHTML = '&times;';
	            // 기존 이미지와 동일한 스타일 적용
	            btn.className = 'absolute top-1 right-1 w-6 h-6 flex items-center justify-center ' +
	                            'bg-gray-100 text-black rounded-full hover:bg-gray-200 transition';
	            btn.onclick = () => {
	                if(confirm("이 이미지를 삭제하시겠습니까?")) {
	                    wrapper.remove();
	                    const deletedFilesInput = document.getElementById("deleteNewFiles");
	                    deletedFilesInput.value += file.name + ",";
	                }
	            };
	            wrapper.appendChild(btn);
	
	            document.getElementById('preview').appendChild(wrapper);
	        };
	        reader.readAsDataURL(file);
	    }
	
	    document.addEventListener('DOMContentLoaded', () => {
	        const fileInput = document.getElementById('file-upload');
	        fileInput.addEventListener('change', () => {
	            [...fileInput.files].forEach(file => addFilePreview(file));
	        });
	
	        $(".btn-update").on("click", function() {
	            $("form[name='detailForm']")
	                .attr("method", "POST")
	                .attr("enctype", "multipart/form-data")
	                .attr("action", "/product/updateProduct")
	                .submit();
	        });
	
	        $("td.ct_btn01:contains('취소')").on("click", function() { history.go(-1); });
	    });
    </script>
</head>

<body class="bg-white text-black">

<form name="detailForm">

    <input type="hidden" name="prodNo" value="${product.prodNo }"/>
    <input type="hidden" name="deleteNewFiles" id="deleteNewFiles" value=""/>

    
    <div class="w-full min-h-screen px-14 py-12">
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
				<div id="preview" class="mt-3 flex gap-3 flex-wrap">
				    <c:if test="${not empty product.fileName}">
				        <c:forEach var="fname" items="${fn:split(product.fileName, ',')}">
				            <div class="relative w-24 h-24" data-existing="true" data-fname="${fname}">
				                <img src="/images/uploadFiles/${fname}" alt="상품 이미지" class="w-full h-full rounded-md border object-cover" />
				                <button type="button"
				                        class="absolute top-1 right-1 w-6 h-6 flex items-center justify-center
				                               bg-gray-100 text-black rounded-full hover:bg-gray-200 transition"
				                        onclick="deleteImage(this)">
				                    &times;
				                </button>
				            </div>
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

</form>

</body>
</html>
