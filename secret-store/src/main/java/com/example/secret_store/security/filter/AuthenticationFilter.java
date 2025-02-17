package com.example.secret_store.security.filter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.secret_store.entity.User;
import com.example.secret_store.security.Constants;
import com.example.secret_store.security.manager.CustomAuthenticationManager;
import com.example.secret_store.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final CustomAuthenticationManager customAuthenticationManager;
    private final UserService userService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            List<GrantedAuthority> authorities = List.of(
                    new SimpleGrantedAuthority("ROLE_" + user.getRoleType().name()));
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(),
                    user.getAppPassword(), authorities);
            return customAuthenticationManager.authenticate(authentication);
        } catch (final IOException exception) {
            throw new RuntimeException("Invalid data input");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        User authenticatedUser = userService.getUser(authResult.getPrincipal().toString());

        String token = JWT.create()
                .withSubject(authenticatedUser.getUsername())
                .withClaim("role", authenticatedUser.getRoleType().name())
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(Constants.SECRET_KEY));

        response.addHeader(Constants.AUTHORIZATION, Constants.BEARER + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(failed.getMessage());
        response.getWriter().flush();
    }

}
