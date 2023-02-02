package com.example.celebrity_management.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.util.Date;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


@Data
@MappedSuperclass
public class BaseModel {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String id;
  private Date updatedDate;
  private Date createdDate;
}
