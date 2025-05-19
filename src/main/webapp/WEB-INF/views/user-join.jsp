<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
language="java" %>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />
    <meta name="author" content="Untree.co" />
    <meta name="description" content="" />
    <meta name="keywords" content="" />
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <title>회원가입</title>
  </head>
  <body>
    <h2>회원가입</h2>
    <div>
      아이디 : <input id="loginId" /> <br /><br />
      닉네임 : <input id="nickname" /> <br /><br />
      비밀번호 : <input id="password" type="password" /> <br /><br />
      비밀번호 확인 : <input id="passwordCheck" type="password" />
    </div>
    <br />
    <button onclick="joinUser()">회원가입</button>
    <button onclick="window.location.href='/page/user/login'">뒤로가기</button>
  </body>
  <script>
    function joinUser() {
      const loginId = document.getElementById("loginId").value;
      const nickname = document.getElementById("nickname").value;
      const password = document.getElementById("password").value;
      const passwordCheck = document.getElementById("passwordCheck").value;
      if (!loginId) {
        alert("아이디를 입력해주세요.");
        return;
      }
      if (!nickname) {
        alert("닉네임을 입력해주세요.");
        return;
      }
      if (!password) {
        alert("비밀번호를 입력해주세요.");
        return;
      }
      if (!passwordCheck) {
        alert("비밀번호 확인을 입력해주세요.");
        return;
      }
      axios
        .post("/user", {
          loginId: loginId,
          nickname: nickname,
          password: password,
          passwordCheck: passwordCheck,
        })
        .then(function (response) {
          alert("회원가입에 성공하였습니다.");
          window.location.href = "/page/user/login";
        })
        .catch(function (error) {
          console.error("error:", error.response.data);
          alert(error.response.data.message);
        });
    }
  </script>
</html>
