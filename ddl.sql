-- `vetflux-assignment`.chat_room definition
CREATE TABLE `chat_room` (
  `chat_room_id` bigint NOT NULL AUTO_INCREMENT COMMENT '채팅방 고유번호',
  `room_name` varchar(255) NOT NULL COMMENT '채팅방 이름',
  `created_datetime` datetime(6) NOT NULL COMMENT '생성일자',
  PRIMARY KEY (`chat_room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='채팅방';

-- `vetflux-assignment`.chat_message definition
CREATE TABLE `chat_message` (
  `chat_message_id` bigint NOT NULL AUTO_INCREMENT COMMENT '채팅 메세지 고유번호',
  `chat_room_id` bigint NOT NULL COMMENT '채팅방 고유번호',
  `sender_id` bigint NOT NULL COMMENT '발신자 고유번호',
  `content` varchar(255) NOT NULL COMMENT '내용',
  `send_datetime` datetime(6) NOT NULL COMMENT '발신일자',
  PRIMARY KEY (`chat_message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='채팅 메세지';

-- `vetflux-assignment`.`user` definition
CREATE TABLE `user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '회원 고유번호',
  `login_id` varchar(50) NOT NULL COMMENT '아이디',
  `password` varchar(255) NOT NULL COMMENT '비밀번호',
  `nickname` varchar(50) NOT NULL COMMENT '닉네임',
  `create_datetime` datetime(6) NOT NULL COMMENT '가입일자',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='회원';

-- `vetflux-assignment`.chat_room_user definition
CREATE TABLE `chat_room_user` (
  `chat_room_id` bigint NOT NULL COMMENT '채팅방 고유번호',
  `user_id` bigint NOT NULL COMMENT '회원 고유번호',
  PRIMARY KEY (`chat_room_id`,`user_id`),
  CONSTRAINT `FKn7wfsq1ii61la6vi9gigw4pk1` FOREIGN KEY (`chat_room_id`) REFERENCES `chat_room` (`chat_room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='채팅방 유저';