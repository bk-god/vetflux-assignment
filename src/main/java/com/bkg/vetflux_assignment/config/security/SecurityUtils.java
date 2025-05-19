package com.bkg.vetflux_assignment.config.security;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bkg.vetflux_assignment.config.exception.UnauthorizedTokenException;

public class SecurityUtils {
    
	public static Optional<Long> getTokenUserIdOptional() {
		return Optional.ofNullable(SecurityContextHolder.getContext())
			.map(SecurityContext::getAuthentication)
			.map(Authentication::getPrincipal)
			.filter(principal -> !principal.toString().equals("anonymousUser"))
			.map(principal -> Long.parseLong(String.valueOf(principal)));
	}

	public static long getTokenUserId() {
		return SecurityUtils.getTokenUserIdOptional()
			.orElseThrow(UnauthorizedTokenException::new);
	}
    
}
