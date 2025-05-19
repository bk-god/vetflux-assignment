package com.bkg.vetflux_assignment.chat.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(access=AccessLevel.PRIVATE)
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Comment("채팅 메세지")
@Entity
@Table(name="chat_message")
public class ChatMessage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("채팅 메세지 고유번호")
    @Column(name = "chat_message_id", nullable = false)
    private long chatMessageId;

    @Comment("채팅방 고유번호")
    @Column(name = "chat_room_id", nullable = false)
    private long chatRoomId;

    @Comment("발신자 고유번호")
    @Column(name = "sender_id", nullable = false)
    private long senderId;

    @Comment("내용")
    @Column(name = "content", nullable = false)
    private String content;

    @Comment("발신일자")
    @Column(name = "send_datetime", nullable = false)
    private LocalDateTime sendDatetime;

    public static ChatMessage create(long chatRoomId, long senderId, String content) {
        return ChatMessage.builder()
        .chatRoomId(chatRoomId)
        .senderId(senderId)
        .content(content)
        .sendDatetime(LocalDateTime.now())
        .build();
    }

}
