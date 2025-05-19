package com.bkg.vetflux_assignment.config.security;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.bkg.vetflux_assignment.config.exception.ExpiredTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityTokenAccessDeniedHandler implements AccessDeniedHandler {

        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response,
                        AccessDeniedException accessDeniedException) throws IOException {
                response.setCharacterEncoding("UTF-8");
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(ExpiredTokenException.httpStatus.value());
                response.getWriter()
                                .write(objectMapper.writeValueAsString(
                                                ExpiredTokenException.getApiResponse()));
        }

}
