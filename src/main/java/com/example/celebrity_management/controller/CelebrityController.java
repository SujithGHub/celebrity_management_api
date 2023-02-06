package com.example.celebrity_management.controller;

import com.example.celebrity_management.service.UserService;
import com.example.celebrity_management.dto.LoginDto;
import com.example.celebrity_management.model.Celebrity;
import com.example.celebrity_management.util.AuthenticationUtil;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("/celebrity")
public class CelebrityController {

  @Autowired
  private AuthenticationUtil authenticationUtil;

  @Autowired
  private UserService celebrityService;

  @PostMapping
  public Optional<Celebrity> createCelebrity(@RequestBody Celebrity celebrityModel) {
    return celebrityService.create(celebrityModel);
  }

  @GetMapping(value = "/get-all-celebrity")
  public List<Celebrity> getAllCelebrityDetails() {
    return celebrityService.getAll();
  }

  @GetMapping(value = "/{id}")
  public Optional<Celebrity> getById(@PathVariable String id) {
    return celebrityService.get(id);
  }

  @DeleteMapping(value = "/{id}")
  public List<Celebrity> deleteById(@PathVariable String id) {
    return celebrityService.delete(id);
  }

  @PostMapping(value = "/login")
  public ResponseEntity<String> login(@RequestBody LoginDto loginDto)
      throws Exception {
    Celebrity loginUser = celebrityService.login(loginDto);
    return ResponseEntity.ok(
        authenticationUtil.authentication(
            loginUser.getMailId(),
            loginDto.getPassword()));
  }
}
