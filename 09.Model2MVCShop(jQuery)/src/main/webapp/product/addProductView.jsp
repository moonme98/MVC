<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>상품등록</title>

  <!-- TailwindCSS -->
  <script src="https://cdn.tailwindcss.com"></script>

  <!-- jQuery & jQuery UI -->
  <link rel="stylesheet" href="/css/admin.css" type="text/css">
	
  <script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
  <script type="text/javascript">
	
		function fncAddProduct(){
			//Form 유효성 검증
			var name=$("input[name='prodName']").val();
			var detail=$("textarea[name='prodDetail']").val();
			var manuDate=$("input[name='manuDate']").val();
			var price=$("input[name='price']").val();
		
			if(name == null || name.length<1){
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
			}
		
			$("form").attr("method" , "POST").attr("enctype" , "multipart/form-data").attr("action" , "/product/addProduct").submit();
		}
		
		//==> 추가된부분 : "취소" "등록" Event 처리 및  연결
		$(function() {
			 $( "td.ct_btn01:contains('취소')" ).on("click" , function() {
					//alert(  $( "td.ct_btn01:contains('취소')" ).html() );
					history.go(-1);
			});
			 
			 $( ".btn-add" ).on("click" , function() {
					//alert(  $( "td.ct_btn01:contains('등록')" ).html() );
					fncAddProduct();			
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

<body class="bg-gray-50">

	<div class="w-full min-h-screen bg-white px-14 py-12">
	  <form class="w-full max-w-[800px] space-y-6">
	    
	    <!-- 제목 -->
	    <h2 class="text-2xl font-semibold text-gray-800 mb-6 border-b-2 pb-2" style="border-color:#3d3d4e;">
	      상품등록
	    </h2>
	
	    <!-- 상품명 -->
	    <input type="text" name="prodName" placeholder="상품명 *"
	           class="w-full border-b border-gray-300 px-2 py-3 text-sm text-gray-600 placeholder-gray-400 focus:outline-none focus:border-[#3d3d4e]" />
	
	    <!-- 상품상세정보 -->
	    <textarea name="prodDetail" placeholder="상품상세정보 *" rows="4"
	              class="w-full border-b border-gray-300 px-2 py-3 text-sm text-gray-600 placeholder-gray-400 focus:outline-none focus:border-[#3d3d4e]"></textarea>
	
	    <!-- 가격 -->
	    <input type="number" name="price" placeholder="가격 (원) *"
	           class="w-full border-b border-gray-300 px-2 py-3 text-sm text-gray-600 placeholder-gray-400 focus:outline-none focus:border-[#3d3d4e]" />
	
	    <!-- 제조일자 -->
	    <input type="date" name="manuDate" placeholder="yy-mm-dd *"
	           class="w-full border-b border-gray-300 px-2 py-3 text-sm text-gray-600 placeholder-gray-400 focus:outline-none focus:border-[#3d3d4e]" />
	
	    <!-- 상품이미지 -->
	    <div>
	      <label for="file-upload" 
	             class="flex flex-col items-center justify-center w-full h-28 border border-gray-300 rounded-md cursor-pointer hover:border-[#3d3d4e] hover:bg-gray-50 transition">
	        <span class="text-sm text-gray-400">이미지 업로드</span>
	      </label>
	      <input type="file" id="file-upload" name="uploadFiles" class="hidden" accept="image/*" multiple />
	      <!-- 미리보기 -->
	      <div id="preview" class="mt-3 flex gap-3"></div>
	    </div>
	
	    <!-- 버튼 -->
	    <div class="flex justify-end gap-3 pt-6">
	      <button type="button" 
	              class="px-5 py-2 rounded-md border border-gray-300 text-sm text-gray-500 hover:bg-gray-50 transition">
	        취소
	      </button>
	      <button type="button" 
	              class="btn-add px-5 py-2 rounded-md text-white text-sm font-medium transition" 
	              style="background-color:#3d3d4e;">
	        등록
	      </button>
	    </div>
	  </form>
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

</body>
</html>
