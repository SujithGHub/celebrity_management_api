package com.example.celebrity_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.example.celebrity_management.props.SuccessResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.celebrity_management.model.BlockDates;
import com.example.celebrity_management.service.BlockDatesService;

@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "/block-date")
public class BlockDatesController {

  @Autowired
  private BlockDatesService blockDatesService;

  @PostMapping
  public SuccessResponse saveUnAvailableDate(@RequestBody BlockDates blockDates) {
    return new SuccessResponse("Date Blocked Successfully", blockDatesService.blockParticularDate(blockDates));
  }

  @GetMapping("/getByCelebrityId/{id}")
  public List<BlockDates> getByCelebrityId(@PathVariable("id") String id){
return blockDatesService.getByCelebrityId(id);
  }

  @DeleteMapping("/delete-by-id")
  public SuccessResponse deleteById(@PathVariable("id") String id){
    return new SuccessResponse(blockDatesService.deleteById(id));
  } 


}
