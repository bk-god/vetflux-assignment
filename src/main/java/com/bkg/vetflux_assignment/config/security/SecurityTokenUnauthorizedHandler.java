package com.bkg.vetflux_assignment.config.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.bkg.vetflux_assignment.config.api.ApiResponse;
import com.bkg.vetflux_assignment.config.exception.ExpiredTokenException;
import com.bkg.vetflux_assignment.config.exception.UnauthorizedTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityTokenUnauthorizedHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        final String exceptionClass = Optional.ofNullable(request.getAttribute("ExceptionClass"))
                .map(ec -> String.valueOf(ec))
                .orElse("OtherException");
        if (exceptionClass.equals("UnauthorizedTokenException")) {
            response.setStatus(UnauthorizedTokenException.httpStatus.value());
            response.getWriter()
                    .write(objectMapper.writeValueAsString(
                            UnauthorizedTokenException.getApiResponse()));
        } else if (exceptionClass.equals("ExpiredTokenException")) {
            response.setStatus(ExpiredTokenException.httpStatus.value());
            response.getWriter()
                    .write(objectMapper.writeValueAsString(
                            ExpiredTokenException.getApiResponse()));
        } else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            response.setStatus(status.value());
            response.getWriter()
                    .write(objectMapper.writeValueAsString(
                            ApiResponse.exception(String.valueOf(status.value()),
                                    "알 수 없는 오류가 발생하였습니다.")));
        }

    }

}
