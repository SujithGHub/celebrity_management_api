package com.example.celebrity_management.configuration;

import com.example.celebrity_management.service.UserService;
import com.example.celebrity_management.util.JwtAuthenticationFilter;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Autowired
  @Qualifier("userService")
  private UserService userService;

  @Bean
  SecurityFilterChain configure(HttpSecurity http) throws Exception {
    http
      .cors()
      .and()
      .csrf()
      .disable()
      .authorizeHttpRequests()
      .requestMatchers(HttpMethod.GET, "/**")
      .permitAll()
      .requestMatchers(HttpMethod.POST, "/**")
      .permitAll()
      .requestMatchers(HttpMethod.DELETE, "/**")
      .permitAll()
      .anyRequest()
      .authenticated()
      .and()
      .exceptionHandling()
      .authenticationEntryPoint((request, response, authException) -> {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write("{\"message\":\"Unauthorized User\"}");
      })
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(
      authenticationJwtTokenFilter(),
      UsernamePasswordAuthenticationFilter.class
    );
    return http.build();
  }

  @Bean
  AuthenticationManager authenticationManagerBean(HttpSecurity http)
    throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(
      AuthenticationManagerBuilder.class
    );
    authenticationManagerBuilder
      .userDetailsService(userService)
      .passwordEncoder(getBcryptPasswordEncoder());
    return authenticationManagerBuilder.build();
  }

  @Bean
  PasswordEncoder getBcryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  Filter authenticationJwtTokenFilter() {
    return new JwtAuthenticationFilter();
  }
}
