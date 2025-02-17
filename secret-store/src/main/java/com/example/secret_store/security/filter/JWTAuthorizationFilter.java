package com.example.secret_store.security.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.secret_store.security.Constants;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String header = request.getHeader(Constants.AUTHORIZATION);
    if (header == null || !header.startsWith(Constants.BEARER)) {
      filterChain.doFilter(request, response);
      return;
    }
    String token = header.replace(Constants.BEARER, "");
    DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(Constants.SECRET_KEY))
        .build()
        .verify(token);
    String username = decodedJWT.getSubject().toString();
    String role = decodedJWT.getClaim("role").asString();
    Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
        List.of(new SimpleGrantedAuthority("ROLE_" + role)));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(request, response);
  }

}
