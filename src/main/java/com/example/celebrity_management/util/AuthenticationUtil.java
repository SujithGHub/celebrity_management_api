package com.example.celebrity_management.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtil {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenProvider tokenProvider;

  public String authentication(String mailId, String password) {
    try {
      final Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(mailId, password)
      );
      SecurityContextHolder.getContext().setAuthentication(authentication);
      return tokenProvider.generateToken(authentication, mailId, password);
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }
}
