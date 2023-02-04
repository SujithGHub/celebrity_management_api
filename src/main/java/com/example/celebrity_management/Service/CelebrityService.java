package com.example.celebrity_management.Service;

import com.example.celebrity_management.dto.LoginDto;
import com.example.celebrity_management.model.CelebrityModel;
import com.example.celebrity_management.model.ScheduleModel;
import com.example.celebrity_management.repository.CelebrityRepository;
import com.example.celebrity_management.repository.ScheduleRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("celebrityService")
public class CelebrityService implements UserDetailsService {

  @Autowired
  @Lazy
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private CelebrityRepository celebrityRepository;

  public Optional<CelebrityModel> create(CelebrityModel celebrityModel) {
    celebrityModel.setPassword(
      bCryptPasswordEncoder.encode(celebrityModel.getPassword())
    );
    CelebrityModel cModel= celebrityRepository.save(celebrityModel);
    return Optional.of(cModel);
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

  @Override

  public  UserDetails loadUserByUsername(String mailId){
       CelebrityModel celebrityModel = celebrityRepository
       .findByMailId(mailId)
       .orElse(null);
       return (UserDetails) celebrityModel;
  }

  public CelebrityModel login(LoginDto loginDto) throws Exception {
    CelebrityModel celebrityModel = celebrityRepository
      .findByMailId(loginDto.getMailId())
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
