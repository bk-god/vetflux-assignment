# 벳플럭스 과제: 실시간 채팅 어플리케이션
## 1. 과제 개요
Java 기반 서버-클라이언트 채팅 어플리케이션 구현
(UI 미관은 중요하지 않음, 시연 필수)

## 2. 개발 스택
- Java 17
- Spring Framework 6.1.4
- Gradle 8.13
- Spring Security + JWT
- Spring WebSocket + STOMP + SockJS
- JPA / MySQL 8.0.40
- Redis (localhost:6379)
- Frontend: HTML + Axios.js + STOMP.js

## 3. 주요 기능
- 회원가입
- 로그인 / 로그아웃
- JWT 기반 사용자 인증
- 채팅방 생성 및 입장
- STOMP 기반 실시간 채팅 메시지 송수신
- Redis Pub/Sub을 통한 메시지 브로드캐스트 (분산 서버 대응)
- 10분 이상 미응답 시 세션 자동 종료

## 4. 사용자 수 증가 대응 전략
- WebSocket 서버 다중 인스턴스 배포 (Scale-out)
- Redis 기반 세션 클러스터링
- Kafka / RabbitMQ로 메시지 브로커 전환 고려
- Spring WebFlux: 논블로킹 아키텍처로 대규모 동시 처리 지원
- NoSQL 저장소 도입: MongoDB, Cassandra 등으로 대용량 메시지 저장 최적화

## 5. SDK로 개발 시 고려 사항
- 버전 및 호환성: 필드나 메서드 제거보다는 deprecated 처리, 인터페이스 변경 최소화, 명확한 버전 관리(SemVer) 및 마이그레이션 가이드 제공
- 모듈화 구조: 모든 기능을 하나의 패키지에 몰아넣지 않고, 핵심(Core)과 확장(Extension) 모듈로 분리
- 환경 독립성: 다양한 플랫폼(웹/모바일/서버), 프레임워크(Spring, Express 등) 또는 클라우드 환경에서 동작 가능
- 사용자 정의 설정 지원
  - WebSocket 연결 URL, 토큰 처리 방식, 타임아웃, 리트라이 횟수 등 설정 가능하게 기본값 제공 + 명시적 오버라이드 허용
  - 빌더 패턴을 활용한 설정 객체 제공
- 토큰 기반 인증: JWT 등 AccessToken 기반 인증 방식 지원 (WebSocket 연결 시에도 token 전달 방식 고려)
- 메시지 암호화: 클라이언트-서버 간 메세지 암호화
- 테스트: 메시지 발송 속도, 전송 지연, 성공률 추적, 로드 테스트(JMeter, k6 등), WebSocket connection 수 제한 테스트, 에러 발생 시 재연결/재시도 로직 내장 등 진행

## 6. 시연 프로세스
1. 회원가입(2개 이상의 계정을 생성, 아이디 중복확인)
2. 로그인
3. 로그인한 회원을 제외한 회원 채팅방 목록 확인
4. 채팅방 입장
5. 메세지 전송
6. 채팅방 나가기
7. 채팅방 재접속(과거 메세지 확인을 위해)
8. 로그아웃
