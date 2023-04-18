package com.example.celebrity_management.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.celebrity_management.Exception.InvalidDataException;
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

    if (file == null && !StringUtils.hasText(celebrity.getImage())){
       throw new InvalidDataException("Please upload image.");
    } else if(file != null) {
      celebrityRepository.save(celebrity);
      celebrity.setImage(saveImageToPath(celebrity.getId(), file));
    }
    return celebrityRepository.save(celebrity);
  }

  // FOR CHANGE THE STATUS
  public List<Celebrity> changeStatus(String id) throws IOException {
    Celebrity celebrity = celebrityRepository.findById(id).orElse(null);
    celebrity
        .setStatus(celebrity.getStatus().equals(Types.Status.ACTIVE) ? Types.Status.INACTIVE : Types.Status.ACTIVE);
    return getAll();
  }

  public List<Celebrity> getAll() throws IOException {
    List<Celebrity> celebrity = celebrityRepository.findAll();
    for (Celebrity celb : celebrity) {
      if(celb.getImage()==null){
        continue;
      }
      if (StringUtils.hasText(celb.getImage())) {
        setBase64Image(celb);
      }
    }
    return celebrity;
    // return celebrityRepository.findAll();
  }

  public Optional<Celebrity> get(String id) {
    return celebrityRepository.findById(id);
  }

  public List<Celebrity> getByAdminId(String Id) {
    return celebrityRepository.findByUsersId(Id);
  }

  private String saveImageToPath(String name, MultipartFile file) throws IOException {
    if(file.getSize()>=100*1024){
      throw new FileSizeLimitExceededException("upload file below 100kb", file.getSize(), 100*1024);
    }
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
