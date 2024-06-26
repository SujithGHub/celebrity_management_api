package com.example.celebrity_management.service;

import java.util.List;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.celebrity_management.Exception.InvalidDataException;
import com.example.celebrity_management.Exception.ResourceNotFoundException;
import com.example.celebrity_management.dto.LoginDto;
import com.example.celebrity_management.dto.UserDto;
import com.example.celebrity_management.model.Users;
import com.example.celebrity_management.repository.RoleRepository;
import com.example.celebrity_management.repository.UserRepository;
import com.example.celebrity_management.util.AuthenticationUtil;

@Service("userService")
public class UserService implements UserDetailsService {

  @Autowired
  @Lazy
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  @Lazy
  private AuthenticationUtil authenticationUtil;

  public Users create(Users user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    user.setRole(roleRepository.findById(user.getRoleId()).orElse(null));
    return userRepository.save(user);
  }

  public List<Users> getAll() {
    return userRepository.findAll();
  }

  public Optional<Users> get(String id) {
    return userRepository.findById(id);
  }

  public List<Users> delete(String id) {
    userRepository.deleteById(id);
    return getAll();
  }

  @Override
  public UserDetails loadUserByUsername(String mailId) {
    Users celebrityModel = userRepository
        .findByMailId(mailId)
        .orElse(null);
    return (UserDetails) celebrityModel;
  }

  public UserDto login(LoginDto loginDto) throws Exception {
    Users user = userRepository.findByMailId(loginDto.getMailId()).orElse(null);
    if (user == null) {
      throw new ResourceNotFoundException("User Not Found");
    } else if (!bCryptPasswordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
      throw new InvalidDataException("Invalid password");
    } else {
      String token = authenticationUtil.authentication(user.getMailId(), loginDto.getPassword(),user.getId()); 
      
      UserDto userDto = new UserDto();
      userDto.setId(user.getId());
      userDto.setMailId(user.getMailId());
      userDto.setName(user.getName());
      userDto.setPhoneNumber(user.getPhoneNumber());
      userDto.setToken(token);
      return userDto;
    }
  }
}
