package com.example.celebrity_management.model;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Entity;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class ClientModel extends BaseModel {

  private Date date;
  private String duration;
  private String event;
  private String mailId;
  private String phoneNumber;
}
