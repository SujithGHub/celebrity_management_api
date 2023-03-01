package com.example.celebrity_management.model;

import java.util.Date;

import com.example.celebrity_management.util.Types;
import com.example.celebrity_management.util.Types.Availability;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Schedule extends BaseModel {

  private Date startTime;
  private Date endTime;
  private String eventName;

  private Types.EventStatus status;
  private Types.Availability availability=Availability.AVAILABLE;

  @Transient
  private String enquiryId;

  @ManyToOne
  public Celebrity celebrity;
}
