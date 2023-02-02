package com.example.celebrity_management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name = "")
public class SheduleModel extends BaseModel {

  private Date date;
  private String status;
}
