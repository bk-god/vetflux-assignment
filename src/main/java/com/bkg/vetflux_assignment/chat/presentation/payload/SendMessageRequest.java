package com.bkg.vetflux_assignment.chat.presentation.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageRequest {
    private long chatRoomId;
    private String content;
}
