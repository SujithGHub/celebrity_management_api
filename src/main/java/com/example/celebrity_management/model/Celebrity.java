package com.example.celebrity_management.model;
import java.util.Date;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Celebrity extends BaseModel {

  private String name;
  private String mailId;
  private String phoneNumber;
  private String gender;
  private Date dateOfBirth;
  private String description;
  private String address;
}
