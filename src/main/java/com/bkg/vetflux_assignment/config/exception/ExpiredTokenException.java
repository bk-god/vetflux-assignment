package com.bkg.vetflux_assignment.config.exception;

import org.springframework.http.HttpStatus;

import com.bkg.vetflux_assignment.config.api.ApiResponse;

import lombok.Getter;

@Getter
public class ExpiredTokenException extends RuntimeException {

    public final static HttpStatus httpStatus = HttpStatus.FORBIDDEN;
    public final static String message = "만료된 토큰입니다.";


    public ExpiredTokenException() {
        super(message);
    }

    public static ApiResponse<?> getApiResponse() {
        return ApiResponse.exception(String.valueOf(httpStatus.value()), message);
    }

}
