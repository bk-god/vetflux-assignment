package com.bkg.vetflux_assignment.user.presentation.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserRequest {
    private String loginId;
    private String password;
}
