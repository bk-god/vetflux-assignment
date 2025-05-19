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
    <title>회원 목록</title>
  </head>
  <body>
    <h3>회원 목록</h3>
    <ul id="userList"></ul>
    <br/>
    <button onclick="logout()">로그아웃</button>
  </body>

  <script>
    const accessToken = sessionStorage.getItem("accessToken");
    if (!accessToken) location.href = "/page/user/login";

    function fetchUserList() {
      axios
        .get("/user", {
          headers: {
            Authorization: "Bearer " + accessToken,
          },
        })
        .then(function (response) {
          const userList = response.data.data;
          userList.forEach(function (element, index, array) {
            addUser(element);
          });
        })
        .catch(function (error) {
          if (error.response.data.code == "401" || error.response.data.code == "403") {
            location.href = "/page/user/login";
          } else {
            alert(error.response.data.message);
          }
        });
    }
    function addUser(user) {
      const userList = document.getElementById("userList");
      const li = document.createElement("li");
      li.innerHTML =
        "<b>" +
        user.nickname +
        "</b> <button onclick='location.href=\"/page/chat/room?userId=" +
        user.userId +
        "\"'>채팅하기</button>";
      userList.appendChild(li);
    }

    function logout() {
      sessionStorage.removeItem("accessToken");
      sessionStorage.removeItem("refreshToken");
      location.href = "/page/user/login";
    }

    window.onload = fetchUserList;
  </script>
</html>
