package com.bkg.vetflux_assignment.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDTO {
    private final long userId;
    private final String nickname;
}
