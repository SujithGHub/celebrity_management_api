package com.example.celebrity_management.model;

import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Users extends BaseModel implements UserDetails {
  private String name;
  private String mailId;
  private String password;
  private String phoneNumber;

  @Transient
  private String roleId;

  @OneToOne
  private Role role;

  @Override
  public List<GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.getName()));
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
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
