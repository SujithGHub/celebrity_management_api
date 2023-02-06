package com.example.celebrity_management.service;

import com.example.celebrity_management.model.Users;
import com.example.celebrity_management.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

  @Autowired
  private UserRepository userRepository;

  public Users create(Users clientModel) {
    return userRepository.save(clientModel);
  }

  public List<Users> getAll() {
    return userRepository.findAll();
  }

  public Optional<Users> getById(String id) {
    return userRepository.findById(id);
  }

  public List<Users> delete(String id) {
    userRepository.deleteById(id);
    return getAll();
  }
}
