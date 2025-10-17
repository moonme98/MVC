<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>상품등록</title>

  <!-- TailwindCSS -->
  <script src="https://cdn.tailwindcss.com"></script>

  <!-- jQuery & jQuery UI -->
  <script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

  <script type="text/javascript">
    // 👉 기존 자바스크립트 그대로 둠
    function fncAddProduct(){
      var name=$("input[name='prodName']").val();
      var detail=$("input[name='prodDetail']").val();
      var manuDate=$("input[name='manuDate']").val();
      var price=$("input[name='price']").val();

      if(name == null || name.length<1){ alert("상품명은 반드시 입력하여야 합니다."); return; }
      if(detail == null || detail.length<1){ alert("상품상세정보는 반드시 입력하여야 합니다."); return; }
      if(manuDate == null || manuDate.length<1){ alert("제조일자는 반드시 입력하셔야 합니다."); return; }
      if(price == null || price.length<1){ alert("가격은 반드시 입력하셔야 합니다."); return; }

      $("form").attr("method" , "POST").attr("enctype" , "multipart/form-data").attr("action" , "/product/addProduct").submit();
    }

    $(function() {
      $( "td.ct_btn01:contains('취소')" ).on("click" , function() {
        history.go(-1);
      });
      $( "td.ct_btn01:contains('등록')" ).on("click" , function() {
        fncAddProduct();
      });
      $("input[name='manuDate']").datepicker({ dateFormat: "yy-mm-dd" });
    });
  </script>
</head>

<body class="bg-gray-50">

<div class="w-full min-h-screen bg-white px-8 py-10">
  <form class="w-full max-w-2xl space-y-6">
    <!-- 제목 -->
    <h2 class="text-2xl font-semibold text-gray-800 mb-8">상품등록</h2>

    <!-- 상품명 -->
    <div>
      <input type="text" placeholder="상품명 *"
             class="w-full border border-gray-300 rounded-md px-4 py-3 text-sm text-gray-600 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-[#3d3d4e]" />
    </div>

    <!-- 상품상세정보 -->
    <div>
      <textarea placeholder="상품상세정보 *"
                rows="4"
                class="w-full border border-gray-300 rounded-md px-4 py-3 text-sm text-gray-600 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-[#3d3d4e]"></textarea>
    </div>

    <!-- 가격 -->
    <div>
      <input type="number" placeholder="가격 (원) *"
             class="w-full border border-gray-300 rounded-md px-4 py-3 text-sm text-gray-600 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-[#3d3d4e]" />
    </div>

    <!-- 제조일자 -->
    <div>
      <input type="date" placeholder="yy-mm-dd *"
             class="w-full border border-gray-300 rounded-md px-4 py-3 text-sm text-gray-600 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-[#3d3d4e]" />
    </div>

    <!-- 상품이미지 -->
    <div>
      <label for="file-upload" 
             class="flex flex-col items-center justify-center w-full h-32 border-2 border-dashed border-gray-300 rounded-md cursor-pointer hover:bg-gray-50">
        <span class="text-sm text-gray-400">클릭하거나 파일을 드래그하세요</span>
        <span class="text-xs text-gray-400">이미지 파일만 업로드 가능</span>
      </label>
      <input type="file" id="file-upload" class="hidden" accept="image/*" />
      <!-- 미리보기 -->
      <div id="preview" class="mt-3 flex gap-3"></div>
    </div>

    <!-- 버튼 -->
    <div class="flex justify-end gap-3 pt-6 border-t">
      <button type="button" 
              class="px-6 py-2 rounded-md border border-gray-300 text-gray-500 text-sm hover:bg-gray-50">
        취소
      </button>
      <button type="submit" 
              class="px-6 py-2 rounded-md text-white text-sm font-medium hover:opacity-90" 
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
