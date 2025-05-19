package com.bkg.vetflux_assignment.chat.presentation.payload;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatRoomRequest {
    private Set<Long> userIds;
}
