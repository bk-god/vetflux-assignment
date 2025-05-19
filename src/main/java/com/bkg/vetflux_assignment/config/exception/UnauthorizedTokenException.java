package com.bkg.vetflux_assignment.config.exception;

import org.springframework.http.HttpStatus;

import com.bkg.vetflux_assignment.config.api.ApiResponse;

public class UnauthorizedTokenException extends RuntimeException {
    
    public final static HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
    public final static String message = "유효하지 않은 토근입니다.";

    public UnauthorizedTokenException() {
        super(message);
    }

    public static ApiResponse<?> getApiResponse() {
        return ApiResponse.exception(String.valueOf(httpStatus.value()), message);
    }

}
