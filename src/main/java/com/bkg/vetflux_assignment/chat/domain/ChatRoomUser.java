package com.bkg.vetflux_assignment.chat.domain;

import org.hibernate.annotations.Comment;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Comment("채팅방 유저")
@Entity
@Table(name = "chat_room_user")
public class ChatRoomUser {

    @EmbeddedId
    private ChatRoomUserId chatRoomUserId;

    @MapsId("chatRoomId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false, updatable = false)
    private ChatRoom chatRoom;

    public static ChatRoomUser create(ChatRoom chatRoom, long userId) {
        return ChatRoomUser.builder()
            .chatRoomUserId(new ChatRoomUserId(chatRoom.getChatRoomId(), userId))
            .chatRoom(chatRoom)
            .build();
    }

    public long getChatRoomId() {
        return this.chatRoomUserId.getChatRoomId();
    }

    public long getUserId() {
        return this.chatRoomUserId.getUserId();
    }

}
