package com.example.celebrity_management.util;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.example.celebrity_management.model.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenProvider implements Serializable {

  final String securityKey = "926D96C90030DD58429D2751AC1BDBBC";

  public String getEmailFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(
      String token,
      Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(securityKey.getBytes()))
        .build().parseClaimsJws(token).getBody();
  }

  public String generateToken(
      Authentication authentication,
      String mailId,
      String id) {
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
                System.currentTimeMillis() + (604800 * 1000)))
        .signWith(Keys.hmacShaKeyFor(securityKey.getBytes()))
        .compact();
  }

  public Boolean validateToken(String token, UserDetails user) {
    final String mailId = getEmailFromToken(token);
    return (mailId.equals(((Users) user).getMailId()) &&
        !isTokenExpired(token));
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }
}
