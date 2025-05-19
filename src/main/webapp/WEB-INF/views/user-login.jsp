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
    <title>로그인</title>
  </head>
  <body>
    <h2>로그인</h2>
    <div>
      <p></p>
      아이디 : <input id="loginId" /> <br /><br />
      비밀번호 : <input id="password" type="password" />
    </div>
    <br />
    <button onclick="loginUser()">로그인</button>
    <button onclick="window.location.href='/page/user/join'">회원가입</button>
  </body>
  <script>
    function loginUser() {
      const loginId = document.getElementById("loginId").value;
      const password = document.getElementById("password").value;
      if (!loginId) {
        alert("아이디를 입력해주세요.");
        return;
      }
      if (!password) {
        alert("비밀번호를 입력해주세요.");
        return;
      }
      axios
        .post("/user/login", {
          loginId: loginId,
          password: password,
        })
        .then(function (response) {
          sessionStorage.setItem('accessToken', response.data.data.accessToken);
          sessionStorage.setItem('refreshToken', response.data.data.refreshToken);
          location.href = "/page/chat/user";
        })
        .catch(function (error) {
          console.error("error:", error.response.data);
          alert(error.response.data.message);
        });
    }
  </script>
</html>
