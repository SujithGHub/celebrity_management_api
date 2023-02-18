package com.example.celebrity_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.celebrity_management.model.BlockDates;
import com.example.celebrity_management.service.BlockDatesService;

@RestController
@RequestMapping(value = "/block-date")
public class BlockDatesController {

  @Autowired
  private BlockDatesService availabilityService;

  @PostMapping
  public BlockDates saveUnAvailableDate(@RequestBody BlockDates blockDates) {
    return availabilityService.blockParticularDate(blockDates);
  }

}
