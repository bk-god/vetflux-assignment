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
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client/dist/sockjs.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/stompjs/lib/stomp.min.js"></script>
    <title>회원 ${userId}번 채팅방</title>
  </head>
  <body>
    <h3>회원 ${userId}번 채팅방</h3>
    <div>
      <ul id="messageList"></ul>
    </div>
    <input id="messageInput" />
    <button onclick="sendMessage()">전송</button>
    <br />
    <br />
    <button onclick="leaveChatRoom()">채팅방 나가기</button>
  </body>

  <script>
    const accessToken = sessionStorage.getItem("accessToken");
    if (!accessToken) location.href = "/page/user/login";

    let stompClient = null;
    let chatRoomId = null;
    function createChatRoom() {
      const userId = "${userId}";
      axios
        .post(
          "/chat/room",
          {
            userIds: [userId],
          },
          {
            headers: {
              Authorization: "Bearer " + accessToken,
            },
          }
        )
        .then(function (response) {
          chatRoomId = response.data.data;
          enterChatRoom(chatRoomId);
          fetchMessageList(chatRoomId);
        })
        .catch(function (error) {
          if (
            error.response.data.code == "401" ||
            error.response.data.code == "403"
          ) {
            location.href = "/page/user/login";
          } else {
            alert(error.response.data.message);
          }
        });
    }

    function enterChatRoom(chatRoomId) {
      const socket = new SockJS("/ws");
      stompClient = Stomp.over(socket);
      stompClient.connect(
        {
          Authorization: "Bearer " + accessToken,
        },
        function (frame) {
          stompClient.subscribe(
            "/sub/chat/room/" + chatRoomId,
            function (message) {
              const chatMessage = JSON.parse(message.body);
              addMessage(chatMessage);
            },
            {
              Authorization: "Bearer " + accessToken,
            }
          );
        }
      );
    }

    function fetchMessageList(chatRoomId) {
      axios
        .get("/chat/room/" + chatRoomId + "/message", {
          headers: {
            Authorization: "Bearer " + accessToken,
          },
        })
        .then(function (response) {
          const chatMessageList = response.data.data;
          chatMessageList.forEach(function (element, index, array) {
            addMessage(element);
          });
        })
        .catch(function (error) {
          if (
            error.response.data.code == "401" ||
            error.response.data.code == "403"
          ) {
            location.href = "/page/user/login";
          } else {
            alert(error.response.data.message);
          }
        });
    }

    function formatDateTime(isoString) {
      const date = new Date(isoString);
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, "0");
      const day = String(date.getDate()).padStart(2, "0");
      const hours = String(date.getHours()).padStart(2, "0");
      const minutes = String(date.getMinutes()).padStart(2, "0");
      const seconds = String(date.getSeconds()).padStart(2, "0");
      return year + "." + month + "." + day + " " + hours + ":" + minutes;
    }

    function addMessage(message) {
      const messageList = document.getElementById("messageList");
      const li = document.createElement("li");
      li.innerHTML =
        "<b>회원" +
        message.senderId +
        ":</b> " +
        message.content +
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;전송일자: " +
        formatDateTime(message.sendDatetime);
      messageList.appendChild(li);
    }

    function sendMessage() {
      if (!stompClient || !chatRoomId) {
        alert("채팅방에 연결되어 있지 않습니다.");
        return;
      }

      const content = document.getElementById("messageInput").value;
      if (!content) {
        alert("메시지를 입력해주세요.");
        return;
      }

      // 서버에 메시지 전송
      stompClient.send(
        "/pub/chat/message",
        {
          Authorization: "Bearer " + accessToken,
        },
        JSON.stringify({
          chatRoomId: chatRoomId,
          content: content,
        })
      );

      // 입력창 초기화
      document.getElementById("messageInput").value = "";
    }

    function leaveChatRoom() {
      if (stompClient && stompClient.connected) {
        stompClient.disconnect(() => {
          location.href = "/page/chat/user";
        });
      }
    }

    window.onload = createChatRoom;
  </script>
</html>
