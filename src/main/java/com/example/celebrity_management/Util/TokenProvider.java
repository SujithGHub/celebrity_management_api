package com.example.celebrity_management.Util;

import com.example.celebrity_management.Props.JwtProps;
import com.example.celebrity_management.model.CelebrityModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class TokenProvider implements Serializable {

  @Autowired
  private JwtProps jwtProps;

  public String getEmailFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(
    String token,
    Function<Claims, T> claimsResolver
  ) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts
      .parser()
      .setSigningKey(jwtProps.getSigningKey().getBytes())
      .parseClaimsJws(token)
      .getBody();
  }

  public String generateToken(
    Authentication authentication,
    String mailId,
    String id
  ) {
    String authorities = authentication
      .getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(","));
    return Jwts
      .builder()
      .setSubject(mailId)
      .setId(id)
      .claim("role", authorities)
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(
        new Date(
          System.currentTimeMillis() + jwtProps.getTokenValidity() * 1000
        )
      )
      .signWith(SignatureAlgorithm.HS256, jwtProps.getSigningKey().getBytes())
      .compact();
  }

  public Boolean validateToken(String token, UserDetails user) {
    final String mailId = getEmailFromToken(token);
    return (
      mailId.equals(((CelebrityModel) user).getMailId()) &&
      !isTokenExpired(token)
    );
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }
}
