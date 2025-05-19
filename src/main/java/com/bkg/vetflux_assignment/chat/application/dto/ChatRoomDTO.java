package com.bkg.vetflux_assignment.chat.application.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ChatRoomDTO {
    private final Long chatRoomId;
    private final String roomName;
    private final LocalDateTime createdDatetime;
}
