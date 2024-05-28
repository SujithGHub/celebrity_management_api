package com.example.celebrity_management.model;

import lombok.Data;
import java.util.Date;
import java.util.List;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.example.celebrity_management.util.Types;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
  private int duration;
  private int budget;
  private Types.EventStatus status = Types.EventStatus.PENDING;
  private String enquiryNo;

  @OneToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  private TypesOfProgram programType;

  @OneToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Celebrity celebrity;

  @ManyToMany
  @OnDelete(action = OnDeleteAction.CASCADE)
  private List<Topics> topics;

  @ManyToMany
  @OnDelete(action = OnDeleteAction.CASCADE)
  private List<Celebrity> celebrityIds;
}
