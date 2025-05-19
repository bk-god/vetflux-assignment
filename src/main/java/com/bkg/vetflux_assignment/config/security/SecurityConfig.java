package com.bkg.vetflux_assignment.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityTokenProvider securityTokenProvider;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring().requestMatchers(
                        "classpath:/static/**",
                        "classpath:/resources/**",
                        "classpath:/META-INF/**",
                        "/static/**",
                        "/resources/**",
                        "/WEB-INF/**",
                        "/BOOT-INF/**",
                        "/favicon.ico",
                        "/api/docs",
                        "/api/docs/**",
                        "/api/swagger-ui/**");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(c -> c.configurationSource(corsConfigurationSource()))
                .headers(hc -> hc.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .formLogin(form -> form
                        .loginPage("/page/user/login")
                        .permitAll())
                .logout(logout -> logout.permitAll())

                .authorizeHttpRequests(ar -> ar
                        .requestMatchers(HttpMethod.GET, SecurityWhiteList.getWhitelistByMethod(HttpMethod.GET))
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, SecurityWhiteList.getWhitelistByMethod(HttpMethod.POST))
                        .permitAll()
                        .requestMatchers(HttpMethod.PUT, SecurityWhiteList.getWhitelistByMethod(HttpMethod.PUT))
                        .permitAll()
                        .requestMatchers(HttpMethod.DELETE, SecurityWhiteList.getWhitelistByMethod(HttpMethod.DELETE))
                        .permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(new SecurityTokenUnauthorizedHandler())
                        .accessDeniedHandler(new SecurityTokenAccessDeniedHandler()))
                .addFilterBefore(
                        new SecurityTokenAuthenticationFilter(this.securityTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
