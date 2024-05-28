package com.example.celebrity_management.model;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.celebrity_management.util.Types;
import com.example.celebrity_management.util.Types.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
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

  private Date dateOfBirth;
  private String profession;
  @Column(length = 1000)
  private String description;
  private String address;
  private Types.Status status = Status.ACTIVE;
  private String image;

  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Users users;

  @ManyToMany
  @OnDelete(action = OnDeleteAction.CASCADE)
  private List<Category> categories;


}
