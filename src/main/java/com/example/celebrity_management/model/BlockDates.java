package com.example.celebrity_management.model;

import java.util.Date;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class BlockDates extends BaseModel {

  private Date blockedDate;
  
  private String celebrityId;
}
