# 벳플럭스 과제
## 채팅 어플리케이션 구현 
### 요구사항
- Java로 서버-클라이언트로 구분해서 구현을 해주시고 나중에 시연을 해주시면 됩니다. 
- 화면 UI이 예쁠 필요는 없습니다. 
- 사용자 수가 많아졌을 때의 대응 전략과 향후 SDK로 구현시에 어떻게 할 지에 대한 말씀을 해주시면 좋겠습니다. (이 부분은 따로 구현 안하셔도 됩니다)

### 개발 Stack
- JDK 17
- Spring Framework 3.4.5
- gradle 8.13
- Spring Security + JWT 인증
- JPA, WebSocket, Redis(localhost:6379)
- mysql 8.0.40(localhost:3306 root/root)
- Html, Axios.js, SockJS + STOMP.js (프론트)


### 주요 기능
- 회원가입
- 로그인(JWT 기반 사용자 인증), 로그아웃
- 실시간 채팅방 생성 및 입장
- STOMP 기반 메시지 송수신
- Redis Pub/Sub을 통한 채팅 메시지 브로드캐스트 (분산 서버 대응)
- 10분 이상 채팅 미발송 시 세션 연결해제
