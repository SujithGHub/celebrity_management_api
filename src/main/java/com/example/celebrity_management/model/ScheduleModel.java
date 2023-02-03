package com.example.celebrity_management.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


@Data
@Entity
@EqualsAndHashCode(callSuper=false)
public class ScheduleModel extends BaseModel {

  private Date date;
  private String status;
}
