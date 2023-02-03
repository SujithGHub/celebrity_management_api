package com.example.celebrity_management.Props;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class JwtProps {

  @Value("${jwt.token.validity}")
  private int tokenValidity;

  @Value("${jwt.token.signing.Key}")
  private String signingKey;

  @Value("${jwt.token.authorities.key}")
  private String authoritiesKey;
}
