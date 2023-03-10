package com.example.celebrity_management.service;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import com.example.celebrity_management.model.Celebrity;
import com.example.celebrity_management.repository.CelebrityRepository;
import com.example.celebrity_management.util.Types;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CelebrityService {

  @Autowired
  private CelebrityRepository celebrityRepository;

  public Celebrity create(Celebrity celebrity, MultipartFile file) throws IOException {
    celebrity.setName(StringUtils.capitalize(celebrity.getName()));

    celebrity.setImage(saveImageToPath(celebrity.getName(), file));

    // celebrityRepository.save(celebrity);
    return celebrityRepository.save(celebrity);
  }

  // FOR CHANGE THE STATUS
  public List<Celebrity> changeStatus(String id) throws IOException{
    Celebrity celebrity = celebrityRepository.findById(id).orElse(null);
    celebrity.setStatus(celebrity.getStatus().equals(Types.Status.ACTIVE) ? Types.Status.INACTIVE : Types.Status.ACTIVE);
    return getAll();
  }

  public List<Celebrity> getAll() throws IOException {
    List<Celebrity> celebrity = celebrityRepository.findAll();
    for (Celebrity celb : celebrity) {
      if (StringUtils.hasText(celb.getImage())) {
        setBase64Image(celb);
      }
    }
    return celebrity;
  }

  public Optional<Celebrity> get(String id) {
    return celebrityRepository.findById(id);
  }

  public List<Celebrity> getByAdminId(String Id) {
    return celebrityRepository.findByUsersId(Id);
  }

  // public List<Celebrity> delete(String id) throws IOException {
  //   scheduleService.deleteByCelebrityId(id);
  //   celebrityRepository.deleteById(id);
  //   return getAll();
  // }

  private String saveImageToPath(String name, MultipartFile file) throws IOException {
    String dir = System.getProperty("user.home").concat("/").concat("resources");
    File directory = new File(dir);
    if (!directory.exists()) {
      directory.mkdirs();
    }
    Path path = Paths.get(dir + "/" + name.concat(".jpeg"));
    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    return path.toString();
  }

  private void setBase64Image(Celebrity celebrity) throws IOException {
    Path imagePath = Path.of(celebrity.getImage());

    // Read the image file into a byte array
    byte[] imageBytes = Files.readAllBytes(imagePath);

    // Encode the byte array as a Base64 string
    celebrity.setBase64Image(Base64.getEncoder().encodeToString(imageBytes));
  }

}
