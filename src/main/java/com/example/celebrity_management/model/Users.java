package com.example.celebrity_management.model;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.OneToOne;


@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Users extends BaseModel implements UserDetails {
  private String name;
  private String mailId;
  private String password;
  private String phoneNumber;
  private boolean locked;
  private boolean credentialsExpired;
  private Boolean isActive;
  @OneToOne
  private Role role;

  
  @Override
  public List<GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("USER_ROLE"));
    return authorities;
  }

  @Override
  public String getUsername() {
    return name;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !locked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return !credentialsExpired;
  }

  @Override
  public boolean isEnabled() {
    return isActive;
  } 
}
