package com.example.celebrity_management.props;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JwtProps {

  @Value("${jwt.token.validity}")
  private int tokenValidity;

  @Value("${jwt.signing.key}")
  private String signingKey;

  @Value("${jwt.authorities.key}")
  private String authoritiesKey;

  @Value("${jwt.token.prefix}")
  private String tokenPrefix;

  @Value("${jwt.header.string}")
  private String headerString;
}

