package com.example.celebrity_management.model;

import java.util.Date;

import com.example.celebrity_management.util.Types;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class BlockDates extends BaseModel {

  private Types.Availability availability = Types.Availability.NOT_AVAILABLE;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date blockedDate;

  @Transient
  private String celebrityId;
}
