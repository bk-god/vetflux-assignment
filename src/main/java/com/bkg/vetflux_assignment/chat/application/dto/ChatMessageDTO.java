package com.bkg.vetflux_assignment.chat.application.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatMessageDTO {
    private final long chatMessageId;
    private final long chatRoomId;
    private final long senderId;
    private final String content;
    private final LocalDateTime sendDatetime;
}
