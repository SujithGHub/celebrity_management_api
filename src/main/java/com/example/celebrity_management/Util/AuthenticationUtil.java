package com.example.celebrity_management.Util;

import com.example.celebrity_management.Service.CelebrityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtil {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenProvider tokenProvider;

  @Autowired
  private CelebrityService celebrityService;

  public void AuthenticationManager(
    AuthenticationManager authenticationManager,
    TokenProvider tokenProvider
  ) {
    this.tokenProvider = tokenProvider;
    this.authenticationManager = authenticationManager;
  }

  public String authentication(String mailId, String password) {
    try {
      final Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(mailId, password)
      );
      SecurityContextHolder.getContext().setAuthentication(authentication);
      // String id = employeeService.loadUserByUsername(empName).getUsername();
      return tokenProvider.generateToken(authentication, mailId, password);
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }
}
  