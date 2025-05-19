package com.bkg.vetflux_assignment.chat.domain;

import java.io.Serializable;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ChatRoomUserId implements Serializable {

    @Comment("채팅방 고유번호")
    @Column(name = "chat_room_id", nullable = false, updatable = false)
    private Long chatRoomId;

	@Comment("회원 고유번호")
	@Column(name = "user_id", nullable = false, updatable = false)
	private Long userId;
    
}
