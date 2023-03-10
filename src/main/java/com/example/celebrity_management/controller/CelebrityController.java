package com.example.celebrity_management.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.celebrity_management.model.Celebrity;
import com.example.celebrity_management.service.CelebrityService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("/celebrity")
public class CelebrityController {

  @Autowired
  private CelebrityService celebrityService;
  @Autowired 
  private ObjectMapper objectMapper;

  @PostMapping
  public Celebrity createCelebrity(@RequestPart("celebrity") String celebrity,MultipartFile file ) throws IOException{
    return celebrityService.create(objectMapper.readValue(celebrity, Celebrity.class),file);
  }
  @PostMapping(value = "/change-status/{id}")
  public List<Celebrity> changeStatus(@PathVariable("id") String id) throws IOException{
    return celebrityService.changeStatus(id);
  }


  @GetMapping(value = "/get-all-celebrity")
  public List<Celebrity> getAllCelebrityDetails() throws IOException{
    return celebrityService.getAll();
  }

  @GetMapping(value = "/{id}")
  public Optional<Celebrity> getById(@PathVariable String id) {
    return celebrityService.get(id);
  }

  @GetMapping(value = "/get-by-adminId/{id}")
  public List<Celebrity> getByAdminId(@PathVariable String id) {
    return celebrityService.getByAdminId(id);
  }

  @DeleteMapping(value = "/{id}")
  public List<Celebrity> deleteById(@PathVariable String id) throws IOException {
    return celebrityService.delete(id);
  }
}
