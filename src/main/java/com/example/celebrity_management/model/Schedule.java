package com.example.celebrity_management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Schedule extends BaseModel {

  @OneToOne
  private EnquiryDetail enquiryDetails;
  private String scheduleNo;

}
