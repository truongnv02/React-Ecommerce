package com.poly.truongnvph29176.config;

import com.poly.truongnvph29176.enums.RoleEnums;
import com.poly.truongnvph29176.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth // ủy quyền
                        .requestMatchers(
                                String.format("%s/accounts/register", apiPrefix),
                                String.format("%s/accounts/login", apiPrefix)
                        )
                        .permitAll()

                        .requestMatchers(HttpMethod.GET,
                                String.format("%s/product-details/**", apiPrefix)).permitAll()
                        .requestMatchers(HttpMethod.GET,
                                String.format("%s/product-details**", apiPrefix)).permitAll()
                        .requestMatchers(HttpMethod.POST,
                                String.format("%s/product-details/**", apiPrefix))
                        .hasAnyAuthority(RoleEnums.ADMIN.name(), RoleEnums.MANAGER.name())

                        .anyRequest()
                        .authenticated())
                .sessionManagement(
                        sessionManager -> sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // quản lý các phiên
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
