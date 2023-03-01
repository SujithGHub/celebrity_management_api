package com.example.celebrity_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.celebrity_management.Exception.InvalidDataException;
import com.example.celebrity_management.model.BlockDates;
import com.example.celebrity_management.repository.BlockDatesRepo;
import jakarta.transaction.Transactional;
import java.util.*;

@Service

@Transactional
public class BlockDatesService {

  @Autowired
  private BlockDatesRepo blockDatesRepo;

  public BlockDates blockParticularDate(BlockDates blockDates) throws InvalidDataException {
    BlockDates block = blockDatesRepo.findByCelebrityIdAndBlockedDate(blockDates.getCelebrityId(),
        blockDates.getBlockedDate());

    if (block != null) {
      throw new InvalidDataException("you already blocked on this date");
    } else {
      return blockDatesRepo.save(blockDates);
    }
  }

  public List<BlockDates> getByCelebrityId(String id){
    return blockDatesRepo.findByCelebrityId(id);
  }

  public String deleteById(String id){
     blockDatesRepo.deleteById(id);
     return "Unblocked Sucessfully";
  }
}
