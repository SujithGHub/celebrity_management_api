package com.example.celebrity_management.controller;

import com.example.celebrity_management.model.Role;
import com.example.celebrity_management.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/role")
public class RoleController {

  @Autowired
  private RoleService roleService;

  @PostMapping
  private Role createRole(@RequestBody Role role) {
    return roleService.create(role);
  }

  @GetMapping(value = "/{id}")
  public Optional<Role> getRoleName(@PathVariable("id") String id) {
    return roleService.name(id);
  }

  @GetMapping("/get-all")
  public List<Role> getAllRoles() {
      return roleService.getAllRoles();
  }
  
  @DeleteMapping(value = "/{delete-name}")
  public Optional<Role> deleteRole(@PathVariable("name") String name) {
    return roleService.delete(name);
  }

}
