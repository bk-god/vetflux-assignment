package com.bkg.vetflux_assignment.config.security;

import java.io.IOException;
import java.util.Collection;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bkg.vetflux_assignment.config.exception.ExpiredTokenException;
import com.bkg.vetflux_assignment.config.exception.UnauthorizedTokenException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SecurityTokenAuthenticationFilter extends OncePerRequestFilter {

    public final static String TOKEN_HEADER_NAME = "Authorization";
    public final static String TOKEN_PREFIX = "Bearer ";

    private final SecurityTokenProvider securityTokenProvider;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();
        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        return SecurityWhiteList.isWhitelist(contextPath, requestURI, method);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            final String accessToken = request.getHeader(TOKEN_HEADER_NAME);
            if (accessToken == null || accessToken.isBlank())
                throw new UnauthorizedTokenException();

            String prefix = accessToken.trim().substring(0, TOKEN_PREFIX.length()).toUpperCase();
            if (!TOKEN_PREFIX.equalsIgnoreCase(prefix))
                throw new UnauthorizedTokenException();

            final String token = accessToken.trim().substring(TOKEN_PREFIX.length());
            if (securityTokenProvider.validateAccessToken(token)) {
                long userId = securityTokenProvider.getAccessTokenUserId(token);
                Collection<? extends GrantedAuthority> authority = securityTokenProvider
                        .getAccessTokenAuthorities(token);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userId, null, authority);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                SecurityContextHolder.getContext().setAuthentication(null);
            }
            filterChain.doFilter(request, response);
        } catch (UnauthorizedTokenException | ExpiredTokenException e) {
            SecurityContextHolder.getContext().setAuthentication(null);
            request.setAttribute("ExceptionClass", e.getClass().getSimpleName());
            throw e;
        }
    }

}
