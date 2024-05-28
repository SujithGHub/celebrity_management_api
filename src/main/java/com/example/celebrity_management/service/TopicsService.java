package com.example.celebrity_management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.celebrity_management.Exception.InvalidDataException;
import com.example.celebrity_management.model.Topics;
import com.example.celebrity_management.repository.TopicsRepo;

@Service
public class TopicsService {
    @Autowired
    private TopicsRepo topicsRepo;

    public Topics create(Topics topics){
        if(topics.getName().isEmpty()){
            throw new InvalidDataException("Topic name must not be empty");
        }
        topics.setName(topics.getName().trim());
        Topics existTopic=topicsRepo.findByNameIgnoreCase(topics.getName());
        if ((existTopic !=null && topics.getId()== null) || (existTopic !=null && existTopic.getName().equalsIgnoreCase(topics.getName()))) {
            throw new InvalidDataException("Topic name already exists");
        }
        return topicsRepo.save(topics);
    }

    public List<Topics> getAllTopics(){
        return topicsRepo.findAll();
    }

    public void remove(String id){
        topicsRepo.deleteById(id);
     }

     public Optional<Topics> getById(String id){
        return topicsRepo.findById(id);
     }

     public Page<Topics> getAllTopicsPage(String name,int pageNo,int pageSize){
       Pageable pageable = PageRequest.of(pageNo, pageSize);
       if (name.isEmpty()) {
        return topicsRepo.findAll(pageable);
       }
      return topicsRepo.findByNameContainingIgnoreCase(name, pageable);
     }
}
