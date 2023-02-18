package com.example.celebrity_management.util;

import com.example.celebrity_management.props.JwtProps;
import com.example.celebrity_management.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JwtProps jwtProps;

  @Autowired
  private TokenProvider jwtTokenUtil;

  private Set<String> skipUrls = new HashSet<>();

  private AntPathMatcher pathMatcher = new AntPathMatcher();

  @Autowired
  private UserService userService;

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request)
      throws ServletException {
    skipUrls.add("/user");
    skipUrls.add("/role/{id}");
    skipUrls.add("/user/login");
    skipUrls.add("/user/get-all");
    skipUrls.add("/celebrity");
    skipUrls.add("/enquiry");
    skipUrls.add("/enquiry/get");
    skipUrls.add("/celebrity/get-all-celebrity");
    skipUrls.add("/celebrity/{id}");
    skipUrls.add("/role");
    skipUrls.add("/celebrity/get-by-adminId/{id}");
    skipUrls.add("/enquiry/getByCelebrityId/{id}");
    skipUrls.add("/status");
    skipUrls.add("/block-date");

    return skipUrls
        .stream()
        .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String header = request.getHeader("Authorization");
    String mailId = null;
    String authToken = null;
    authToken = header.replace(jwtProps.getTokenPrefix(), "");
    try {
      mailId = jwtTokenUtil.getEmailFromToken(authToken);
    } catch (IllegalArgumentException e) {
      logger.error("An error occurred while fetching Username from Token", e);
      try {
        throw new Exception(
            "An error occurred while fetching Username from Token");
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    } catch (ExpiredJwtException e) {
      logger.warn("The token has expired", e);
      try {
        throw new Exception("The token has expired");
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    } catch (SignatureException e) {
      logger.error("Authentication Failed. Username or Password not valid.");
      try {
        throw new Exception(
            "Authentication Failed. Username or Password not valid.");
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    }

    UserDetails userDetails = userService.loadUserByUsername(mailId);

    if (jwtTokenUtil.validateToken(authToken, userDetails)) {
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          userDetails.getAuthorities());
      authentication.setDetails(
          new WebAuthenticationDetailsSource().buildDetails(request));
      logger.info(
          "authenticated user " + mailId + ", setting security context");
      SecurityContextHolder.getContext().setAuthentication(authentication);
      filterChain.doFilter(request, response);
    } else {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.getWriter().write("{\"message\":\"Unauthorized User\"}");
    }
  }
}
