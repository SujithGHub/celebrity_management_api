package com.example.celebrity_management.controller;

import com.example.celebrity_management.Dto.LoginDto;
import com.example.celebrity_management.Service.CelebrityService;
import com.example.celebrity_management.Util.AuthenticationUtil;
import com.example.celebrity_management.model.CelebrityModel;
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
  private CelebrityService celebrityService;

  @PostMapping
  public CelebrityModel createCelebrity(
    @RequestBody CelebrityModel celebrityModel
  ) {
    return celebrityService.create(celebrityModel);
  }

  @GetMapping(value = "/get-all-celebrity")
  public List<CelebrityModel> getAllCelebrityDetails() {
    return celebrityService.getAll();
  }

  @GetMapping(value = "/{id}")
  public Optional<CelebrityModel> getById(@PathVariable String id) {
    return celebrityService.get(id);
  }

  @DeleteMapping(value = "/{id}")
  public List<CelebrityModel> deleteById(@PathVariable String id) {
    return celebrityService.delete(id);
  }

  @PostMapping(value = "/login")
  public ResponseEntity<String> login(@RequestBody LoginDto loginDto)
    throws Exception {
    CelebrityModel loginUser = celebrityService.login(loginDto);
    return ResponseEntity.ok(
      authenticationUtil.authentication(
        loginUser.getMailId(),
        loginDto.getPassword()
      )
    );
  }
}
