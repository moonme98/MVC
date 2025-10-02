<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 화면</title>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://t1.kakaocdn.net/kakao_js_sdk/2.7.6/kakao.min.js"></script>

<script>
$(function() {
    // ----------------------
    // 카카오 SDK 초기화
    // ----------------------
    Kakao.init('YOUR_JAVASCRIPT_KEY'); // <-- JavaScript Key 입력
    console.log(Kakao.isInitialized() ? "Kakao SDK 초기화 완료" : "SDK 초기화 실패");

    // ----------------------
    // 일반 로그인
    // ----------------------
    $("#loginBtn").click(function() {
        var id = $("#userId").val();
        var pw = $("#password").val();
        if(!id || !pw){ alert("ID와 비밀번호 입력"); return; }

        $.ajax({
            url: "/user/logrksrin",   // 기존 POST 로그인 컨트롤러
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify({ userId: id, password: pw }),
            success: function(){ window.location.href="/index.jsp"; },
            error: function(){ alert("로그인 실패"); }
        });
    });

    $("input:text, input:password").on("keyup", function(e){
        if(e.keyCode === 13) $("#loginBtn").click();
    });

    // ----------------------
    // 카카오 로그인 버튼
    // ----------------------
    $("#kakao-login-btn").click(function() {
        Kakao.Auth.authorize({
            redirectUri: 'http://localhost:8080/user/kakaoLogin'
        });
    });

    // ----------------------
    // 회원가입
    // ----------------------
    $("#signupBtn").click(function(){
        window.location.href="/user/addUser";
    });
});
</script>

</head>
<body>
<form>
    <div>
        <h2>일반 로그인</h2>
        <input type="text" id="userId" placeholder="ID"><br>
        <input type="password" id="password" placeholder="Password"><br>
        <button type="button" id="loginBtn">로그인</button>
        <button type="button" id="signupBtn">회원가입</button>
    </div>

    <hr>

    <div>
        <h2>카카오 로그인</h2>
        <a href="javascript:void(0)" id="kakao-login-btn">
            <img src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg" width="222">
        </a>
    </div>
</form>
</body>
</html>
