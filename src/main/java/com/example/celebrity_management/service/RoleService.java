package com.example.celebrity_management.service;

import com.example.celebrity_management.model.Role;
import com.example.celebrity_management.repository.RoleRepository;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service

@Transactional
public class RoleService {

  @Autowired
  private RoleRepository roleRepository;

  public Role create(Role role) {
    return roleRepository.save(role);
  }

  public Optional<Role> name(String id) {
    return roleRepository.findById(id);
  }

  public Optional<Role> delete(String name) {
    return roleRepository.deleteByName(name);

  }
}