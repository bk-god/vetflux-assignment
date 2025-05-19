package com.bkg.vetflux_assignment.chat.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.annotations.Comment;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Comment("채팅방")
@Entity
@Table(name="chat_room")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("채팅방 고유번호")
    @Column(name = "chat_room_id", nullable = false)
    private Long chatRoomId;

    @Comment("채팅방 이름")
    @Column(name = "room_name", nullable = false)
    private String roomName;

    @Comment("생성일자")
    @Column(name = "created_datetime", nullable = false)
    private LocalDateTime createdDatetime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chatRoom", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private Set<ChatRoomUser> users;

    public static ChatRoom create(String roomName, Set<Long> userIds) {
        ChatRoom chatRoom = ChatRoom.builder()
        .roomName(roomName)
        .createdDatetime(LocalDateTime.now())
        .build();
        userIds.forEach((userId) -> chatRoom.enterUser(userId));
        return chatRoom;
    }

    private void enterUser(long userId) {
        if (this.users == null) this.users = new HashSet<>();
		if (this.users.stream().noneMatch(r -> r.getUserId() == userId))
			this.users.add(ChatRoomUser.create(this, userId));
    }

    public boolean isEnteredUser(long userId) {
        return Optional.ofNullable(this.users)
            .map((us) -> us.stream().map((u) -> u.getUserId()).collect(Collectors.toSet()))
            .map((ids) -> ids.contains(userId))
            .orElse(false);
    }

}
