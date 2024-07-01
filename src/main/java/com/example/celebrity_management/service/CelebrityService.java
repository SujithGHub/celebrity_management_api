  package com.example.celebrity_management.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    return celebrityRepository.findAll();
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
    String homePath = System.getProperty("user.home");
    String dir = buildString(homePath,"/resources");
    File directory = new File(dir);
    if (!directory.exists()) {
      directory.mkdirs();
    }
    Path path = Paths.get(buildString(dir,"/",buildString(name,".jpeg")));
    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    return path.toString().replace(homePath, "/api");
  }

   public List<Celebrity> getCelebrityByCategoryId(String id){
    return celebrityRepository.findByCategories_Id(id);
   }

    public List<Celebrity> getCelebrityByCategoryIdandTopicId(String categoryId,String topicId){
        if (StringUtils.hasText(categoryId) && StringUtils.hasText(topicId)) {
            return celebrityRepository.findByCategories_IdAndTopics_Id(categoryId, topicId);
        }
        
        if (StringUtils.hasText(categoryId)) {
            return celebrityRepository.findByCategories_Id(categoryId);
        }
        
        if (StringUtils.hasText(topicId)) {
            return celebrityRepository.findByTopics_Id(topicId);
        }
        
        return celebrityRepository.findAll();
    }

    public List<Celebrity> getCelebritiesBySearch(String search) throws IOException{
      if (! StringUtils.hasText(search)) {
         return celebrityRepository.findAll();
      }
      return celebrityRepository.findBySearchTerm(search.toLowerCase());
     
    }


    public Page<Celebrity> getCelebritiesByStatusAndSearch(String name,Types.Status status,int pageNo, int pageSize) throws IOException{
      Pageable pageable = PageRequest.of(pageNo, pageSize);

      if (StringUtils.hasText(name)) {
          name = name.toLowerCase(); 
          return celebrityRepository.findBySearchTermAndStatus(name, status, pageable);
      } 
      return celebrityRepository.findAllByStatus(status, pageable);
    }

  public String buildString(String ...args) {
    StringBuilder sb = new StringBuilder();
    for (String arg : args) {
      sb.append(arg);
    }
    return sb.toString();
  }

  public List<Celebrity> getCelebrityByCategoryAndTopicList(String categoryId, List<String> topicId) {
    if (StringUtils.hasText(categoryId) && topicId != null && !topicId.isEmpty()) {
      return celebrityRepository.findByCategories_IdAndTopics_IdIn(categoryId, topicId);
    } else {
      return new ArrayList<>();
    }
  }
}
