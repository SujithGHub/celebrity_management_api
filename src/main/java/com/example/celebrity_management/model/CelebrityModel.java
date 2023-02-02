package com.example.celebrity_management.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class CelebrityModel extends BaseModel {

  private String name;
  private String mailId;
  private String phoneNumber;
  private String gender;
  private int Age;
  private String role;
}
