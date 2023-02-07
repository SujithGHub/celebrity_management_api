package com.example.celebrity_management.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.celebrity_management.dto.LoginDto;
import com.example.celebrity_management.model.Users;
import com.example.celebrity_management.props.SuccessResponse;
import com.example.celebrity_management.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.celebrity_management.util.AuthenticationUtil;
import com.example.celebrity_management.repository.UserRepository;
import org.springframework.context.annotation.Lazy;


@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AuthenticationUtil authenticationUtil;

  @Autowired
  @Lazy
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  
  @PostMapping
  public Users saveUser(@RequestBody Users user) {
    return userService.create(user);
  }

  @GetMapping(value = "/get-all")
  public List<Users> getAllCelebrityDetails() {
    return userService.getAll();
  }

  @GetMapping(value = "/{id}")
  public Optional<Users> getById(@PathVariable String id) {
    return userService.get(id);
  }

  @DeleteMapping(value = "/{id}")
  public List<Users> deleteById(@PathVariable String id) {
    return userService.delete(id);
  }

  @PostMapping(value = "/login")
  public SuccessResponse login(@RequestBody LoginDto loginDto) throws Exception {
    return new SuccessResponse("Logged In Successfully",userService.login(loginDto));
  }
}
