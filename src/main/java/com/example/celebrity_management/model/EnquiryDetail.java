package com.example.celebrity_management.model;
import lombok.Data;
import java.util.Date;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class EnquiryDetail extends BaseModel {

  private String name;
  private String email;
  private String mobile;
  private Date startTime;
  private Date endTime;

  @OneToOne
  private Celebrity celebrity;
  
}
