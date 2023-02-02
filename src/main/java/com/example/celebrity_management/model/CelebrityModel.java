package com.example.celebrity_management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "celebrity")
public class CelebrityModel extends BaseModel {

  private String name;
  private String mailId;
  private String phoneNumber;
  private String gender;
  private int Age;
  private String role;
}
