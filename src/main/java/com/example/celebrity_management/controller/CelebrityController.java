package com.example.celebrity_management.controller;

import com.example.celebrity_management.Service.CelebrityService;
import com.example.celebrity_management.model.CelebrityModel;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
  private CelebrityService celebrityService;

  @PostMapping
  public CelebrityModel createCelebrity(
    @RequestBody CelebrityModel celebritymodel
  ) {
    return celebrityService.create(celebritymodel);
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
}
