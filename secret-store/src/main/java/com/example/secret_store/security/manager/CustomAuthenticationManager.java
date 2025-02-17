package com.example.secret_store.security.manager;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.secret_store.entity.User;
import com.example.secret_store.service.UserService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

  private UserService userService;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    User user = userService.getUser(authentication.getPrincipal().toString());
    if (!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), user.getAppPassword())) {
      throw new BadCredentialsException("Bad password");
    }
    List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRoleType().name()));
    return new UsernamePasswordAuthenticationToken(authentication.getPrincipal().toString(), user.getAppPassword(),
        authorities);
  }

}