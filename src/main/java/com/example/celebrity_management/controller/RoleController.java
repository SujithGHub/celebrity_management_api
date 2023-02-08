package com.example.celebrity_management.controller;

import com.example.celebrity_management.model.Role;
import com.example.celebrity_management.service.RoleService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;

@RestController
@RequestMapping(value = "role")
public class RoleController {

  @Autowired
  private RoleService RoleService;

  @PostMapping
  private Role createRole(@RequestBody Role role) {
    return RoleService. create(role);
  }

  @GetMapping(value="/get-name")
  public List<Role> getRoleName(@RequestParam ("name")String name) {
      return RoleService.name(name);
  }

  @DeleteMapping(value="/{delete-name}")
  public Optional<Role> deleteRole(@PathVariable("name") String name){
    return RoleService.delete(name);
  }
  
}
