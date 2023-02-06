package com.example.celebrity_management.controller;

import com.example.celebrity_management.service.ClientService;
import com.example.celebrity_management.model.Users;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {

  @Autowired
  private ClientService clientService;

  @PostMapping
  public Users createClient(@RequestBody Users clientModel) {
    return clientService.create(clientModel);
  }

  @GetMapping("/get-all-client")
  public List<Users> getAllClientDetails() {
    return clientService.getAll();
  }

  @GetMapping("{id}")
  public Optional<Users> getAllClientById(@PathVariable("id") String id) {
    return clientService.getById(id);
  }

  @DeleteMapping("{id}")
  public List<Users> deleteClient(@PathVariable("id") String id) {
    return clientService.delete(id);
  }
}
