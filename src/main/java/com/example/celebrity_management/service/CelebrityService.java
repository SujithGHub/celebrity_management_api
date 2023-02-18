package com.example.celebrity_management.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.celebrity_management.model.Celebrity;
import com.example.celebrity_management.repository.CelebrityRepository;

@Service
public class CelebrityService {

  @Autowired
  private CelebrityRepository celebrityRepository;

  public Celebrity create(Celebrity celebrity) {

    String CelebrityFullName = celebrity.getName().substring(1, celebrity.getName().length());

    String CelebrityFirstName = celebrity.getName().substring(0, 1).toUpperCase();
    String result = CelebrityFirstName.concat(CelebrityFullName);
    celebrity.setName(result);
    return celebrityRepository.save(celebrity);
  }

  public List<Celebrity> getAll() {
    return celebrityRepository.findAll();
  }

  public Optional<Celebrity> get(String id) {
    return celebrityRepository.findById(id);
  }

  public List<Celebrity> getByAdminId(String Id) {
    return celebrityRepository.findByUsersId(Id);
  }

  public List<Celebrity> delete(String id) {
    celebrityRepository.deleteById(id);
    ;
    return getAll();
  }
}
