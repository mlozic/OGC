package com.example.secret_store.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.example.secret_store.security.filter.AuthenticationFilter;
import com.example.secret_store.security.filter.ExceptionHandlerFilter;
import com.example.secret_store.security.filter.JWTAuthorizationFilter;
import com.example.secret_store.security.manager.CustomAuthenticationManager;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class WebSecurityConfiguration {

    private CustomAuthenticationManager customAuthenticationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager);
        authenticationFilter.setFilterProcessesUrl("/authenticate");
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((auth) -> auth
                .requestMatchers(HttpMethod.POST, Constants.REGISTER_PATH).permitAll()
                .anyRequest().authenticated())
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
                .httpBasic(withDefaults()).sessionManagement(sessionManagementCustomizer -> sessionManagementCustomizer
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}