package com.example.celebrity_management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

import com.example.celebrity_management.util.Types;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Schedule extends BaseModel {

  private Date startTime;
  private Date endTime;
  private String eventName;

  private Types.EventStatus status;
  private String availability;

  @Transient
  private String enquiryId;

  @ManyToOne
  public Celebrity celebrity;
}
