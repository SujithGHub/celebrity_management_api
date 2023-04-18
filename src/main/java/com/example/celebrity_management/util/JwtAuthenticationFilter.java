package com.example.celebrity_management.util;

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

import com.example.celebrity_management.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

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
    skipUrls.add("/schedule");
    skipUrls.add("/enquiry");
    skipUrls.add("/celebrity/get-all-celebrity");
    skipUrls.add("/client");

    

    return skipUrls
        .stream()
        .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String header = request.getHeader("Authorization");
      String mailId = null;
      String authToken = null;

      authToken = header.substring(7);

      mailId = jwtTokenUtil.getEmailFromToken(authToken);
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
    } catch (Exception e) {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.getWriter().write("{\"message\":\"Unauthorized User\"}");
    }
  }
}
