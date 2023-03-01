package com.example.celebrity_management.model;

import lombok.Data;
import java.util.Date;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.example.celebrity_management.util.Types;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class EnquiryDetail extends BaseModel {

  private String organizationName;
  private String name;
  private String mailId;
  private String location;
  private String phoneNumber;
  private String eventName;
  private Date startTime;
  private Date endTime;
  private Types.EventStatus status = Types.EventStatus.PENDING;

  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Celebrity celebrity;

}
