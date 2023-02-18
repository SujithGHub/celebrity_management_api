package com.example.celebrity_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.celebrity_management.model.BlockDates;
import com.example.celebrity_management.repository.BlockDatesRepo;

@Service
public class BlockDatesService {

  @Autowired
  private BlockDatesRepo blockDatesRepo;

  public BlockDates blockParticularDate(BlockDates blockDates) {
    return blockDatesRepo.save(blockDates);
  }

}
