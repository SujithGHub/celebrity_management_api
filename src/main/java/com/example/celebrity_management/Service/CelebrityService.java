package com.example.celebrity_management.Service;

import com.example.celebrity_management.Repository.CelebrityRepositroy;
import com.example.celebrity_management.model.CelebrityModel;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CelebrityService {

  @Autowired
  private CelebrityRepositroy celebrityRepositroy;

  public CelebrityModel create(CelebrityModel celebritymodel) {
    return celebrityRepositroy.save(celebritymodel);
  }

  public List<CelebrityModel> getAll() {
    return celebrityRepositroy.findAll();
  }

  public Optional<CelebrityModel> get(String id) {
    return celebrityRepositroy.findById(id);
  }

  public List<CelebrityModel> delete(String id){
     celebrityRepositroy.deleteById(id);
     return getAll();
  }
}
