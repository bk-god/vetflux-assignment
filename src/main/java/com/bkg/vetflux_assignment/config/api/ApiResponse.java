package com.bkg.vetflux_assignment.config.api;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private String code;
    private String message;
    private T data;

    public static ApiResponse<?> success() {
        return ApiResponse.builder()
        .code("200")
        .message("API Call Successed")
        .build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
        .code("200")
        .message("API Call Successed")
        .data(data)
        .build();
    }

    public static ApiResponse<?> exception(String code, String message) {
        return ApiResponse.builder()
        .code(code)
        .message(message)
        .build();
    }

}
