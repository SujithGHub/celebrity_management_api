package com.example.celebrity_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.celebrity_management.model.Topics;
import com.example.celebrity_management.props.SuccessResponse;
import com.example.celebrity_management.service.TopicsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "/topics")
public class TopicsController {
    @Autowired
    private TopicsService topicsService;

    @PostMapping("/add")
    public SuccessResponse saveTopics(@RequestBody Topics topics) {
        return new SuccessResponse("Topics Submitted", topicsService.create(topics));
    }

    @GetMapping("/get-all-topic")
    public List<Topics> getAllTopics() {
        return topicsService.getAllTopics();
    }
    
    @DeleteMapping("remove/{id}")
    public void removeTopic(@PathVariable String id){
        topicsService.remove(id);
    }
    
    @GetMapping("/get")
    public Page<Topics> getTopics(@RequestParam String name,@RequestParam int page,@RequestParam int size) {
        return topicsService.getAllTopicsPage(name,page, size);
    }
    

    

}
