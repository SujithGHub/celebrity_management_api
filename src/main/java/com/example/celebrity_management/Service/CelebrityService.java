package com.example.celebrity_management.Service;

import com.example.celebrity_management.Dto.LoginDto;
import com.example.celebrity_management.Repository.CelebrityRepository;
import com.example.celebrity_management.model.CelebrityModel;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CelebrityService {

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private CelebrityRepository celebrityRepository;

  public CelebrityModel create(CelebrityModel celebrityModel) {
    return celebrityRepository.save(celebrityModel);
  }

  public List<CelebrityModel> getAll() {
    return celebrityRepository.findAll();
  }

  public Optional<CelebrityModel> get(String id) {
    return celebrityRepository.findById(id);
  }

  public List<CelebrityModel> delete(String id){
    celebrityRepository.deleteById(id);
     return getAll();
  }

  public CelebrityModel login(LoginDto loginDto) throws Exception {
    CelebrityModel celebrityModel = celebrityRepository
      .findById(loginDto.getMailId())
      .orElseThrow(() -> new Exception("Invalid user"));
    if (
      !bCryptPasswordEncoder.matches(
        loginDto.getPassword(),
        celebrityModel.getPassword()
      )
    ) {
      throw new Exception("Invalid password");
    }
    return celebrityModel;
  }

}
