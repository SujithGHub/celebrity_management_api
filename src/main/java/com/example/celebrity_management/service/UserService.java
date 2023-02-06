package com.example.celebrity_management.service;

import com.example.celebrity_management.dto.LoginDto;
import com.example.celebrity_management.model.Celebrity;
import com.example.celebrity_management.repository.CelebrityRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService implements UserDetailsService {

  @Autowired
  @Lazy
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private CelebrityRepository celebrityRepository;

  public Optional<Celebrity> create(Celebrity celebrityModel) {
    // celebrityModel.setPassword(
    //   // bCryptPasswordEncoder.encode(celebrityModel.getPassword())
    // );
    Celebrity cModel= celebrityRepository.save(celebrityModel);
    return Optional.of(cModel);
  }

  public List<Celebrity> getAll() {
    return celebrityRepository.findAll();
  }

  public Optional<Celebrity> get(String id) {
    return celebrityRepository.findById(id);
  }

  public List<Celebrity> delete(String id){
    celebrityRepository.deleteById(id);
     return getAll();
  }

  @Override

  public UserDetails loadUserByUsername(String mailId){
       Celebrity celebrityModel = celebrityRepository
       .findByMailId(mailId)
       .orElse(null);
       return (UserDetails) celebrityModel;
  }

  public Celebrity login(LoginDto loginDto) throws Exception {
    Celebrity celebrityModel = celebrityRepository
      .findByMailId(loginDto.getMailId())
      .orElseThrow(() -> new Exception("Invalid user"));
    if (
      !bCryptPasswordEncoder.matches(
        loginDto.getPassword(),
       " celebrityModel.getPassword()"
      )
    ) {
      throw new Exception("Invalid password");
    }
    return celebrityModel;
  }

}
