package com.bkg.vetflux_assignment.config.security;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.bkg.vetflux_assignment.config.exception.ExpiredTokenException;
import com.bkg.vetflux_assignment.config.exception.UnauthorizedTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class SecurityTokenProvider {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.security.jwt.access-token.secret-key}")
    private String accessTokenSecretKey;

    @Value("${spring.security.jwt.access-token.expired-time}")
    private long accessTokenExpiredTime;

    @Value("${spring.security.jwt.refresh-token.secret-key}")
    private String refreshTokenSecretKey;

    @Value("${spring.security.jwt.refresh-token.expired-time}")
    private long refreshTokenExpiredTime;

    private final static String USER_ID_CLAIM_KEY = "user_id";
    private final static String USER_NAME_CLAIM_KEY = "user_name";
    private final static String ROLES_CLAIM_KEY = "roles";
    private final static String ROLES_CLAIM_DELIMITER = ",";

    private String issueToken(UUID id, Claims claims, String secretKey, Date expiredDate) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setClaims(claims)
                .setId(id.toString())
                .setIssuer(applicationName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiredDate)
                .compact();
    }

    public SecurityTokenDTO issueToken(long userId, String nickname,
            Collection<? extends GrantedAuthority> authorities) {
        Map<String, Object> claims = new HashMap<>() {
            {
                put(USER_ID_CLAIM_KEY, userId);
                put(USER_NAME_CLAIM_KEY, nickname);
                put(ROLES_CLAIM_KEY, authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(ROLES_CLAIM_DELIMITER)));
            }
        };

        UUID id = UUID.randomUUID();
        Date accessTokenExpiredDate = new Date(System.currentTimeMillis() + accessTokenExpiredTime);
        String accessToken = issueToken(id, Jwts.claims(claims), accessTokenSecretKey, accessTokenExpiredDate);
        Date refreshTokenExpiredDate = new Date(System.currentTimeMillis() + refreshTokenExpiredTime);
        String refreshToken = issueToken(id, Jwts.claims(claims), refreshTokenSecretKey, refreshTokenExpiredDate);

        LocalDateTime accessTokenExpiredDatetime = new Timestamp(accessTokenExpiredDate.getTime()).toLocalDateTime();
        LocalDateTime refreshTokenExpiredDatetime = new Timestamp(refreshTokenExpiredDate.getTime()).toLocalDateTime();
        return new SecurityTokenDTO(accessToken, accessTokenExpiredDatetime, refreshToken, refreshTokenExpiredDatetime);
    }

    private Boolean validateToken(String token, String secretKey)
            throws ExpiredTokenException, UnauthorizedTokenException {
        try {
            getClaim(token, secretKey);
            return true;
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (Exception e) {
            throw new UnauthorizedTokenException();
        }
    }

    public Boolean validateAccessToken(String token) throws ExpiredTokenException, UnauthorizedTokenException {
        return validateToken(token, accessTokenSecretKey);
    }

    public Boolean validateRefreshToken(String token) throws ExpiredTokenException, UnauthorizedTokenException {
        return validateToken(token, refreshTokenSecretKey);
    }

    private Claims getClaim(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public Long getAccessTokenUserId(String token) {
        return Optional.ofNullable(getClaim(token, accessTokenSecretKey).get(USER_ID_CLAIM_KEY))
                .map(userId -> Long.valueOf(String.valueOf(userId)))
                .orElse(null);
    }

    public Collection<? extends GrantedAuthority> getAccessTokenAuthorities(String token) {
        final String authorities = String.valueOf(getClaim(token, accessTokenSecretKey).get(ROLES_CLAIM_KEY));
        return authorities != null && !authorities.isBlank() ? Arrays.stream(authorities.split(ROLES_CLAIM_DELIMITER))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()) : List.of();
    }

}
