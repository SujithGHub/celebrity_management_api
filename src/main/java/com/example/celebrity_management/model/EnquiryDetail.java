package com.example.celebrity_management.model;

import lombok.Data;
import java.util.Date;
import java.util.List;
import com.example.celebrity_management.util.Types;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class EnquiryDetail extends BaseModel {

  private String organizationName;
  private String name;
  private String mailId;
  private String venue;
  private String phoneNumber;
  private String eventName;
  private Date startTime;
  private Date endTime;
  private String duration;
  private int budget;
  private Types.EventStatus status = Types.EventStatus.PENDING;
  private String enquiryNo;

  @OneToOne
  private TypesOfProgram programType;

  @OneToOne
  private Celebrity celebrity;

  @ManyToMany(cascade = CascadeType.REMOVE)
  private List<Celebrity> celebrityIds;
}
