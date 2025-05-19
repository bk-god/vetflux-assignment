package com.bkg.vetflux_assignment.config.security;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SecurityTokenDTO {
    private final String accessToken;
	private final LocalDateTime accessTokenExpiredDatetime;
	private final String refreshToken;
	private final LocalDateTime refreshTokenExpiredDatetime;
}
