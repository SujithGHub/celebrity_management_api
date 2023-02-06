package com.example.celebrity_management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


@Data
@Entity
@EqualsAndHashCode(callSuper=false)
public class Schedule extends BaseModel {

  private Date date;
  private String status;
  private String duration;
  private String event;
  
  @ManyToOne
  public Celebrity celebrity;
}
