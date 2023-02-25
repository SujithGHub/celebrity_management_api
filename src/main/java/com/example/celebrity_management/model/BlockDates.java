package com.example.celebrity_management.model;

import java.util.Date;
import java.util.regex.Pattern;

import com.example.celebrity_management.util.Types;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class BlockDates extends BaseModel {

  // private Types.Availability availability = Types.Availability.NOT_AVAILABLE;

  private Date blockedDate;
  
  private String celebrityId;
}
