package com.example.celebrity_management.model;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Celebrity extends BaseModel {

  private String name;

  @Column(unique = true)
  private String mailId;

  @Column(unique = true)
  private String phoneNumber;
  private String gender;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dateOfBirth;
  private String profession;
  private String description;
  private String address;

  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Users users;

}
