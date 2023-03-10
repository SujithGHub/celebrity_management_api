package com.example.celebrity_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.celebrity_management.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {
 

  //@Query("select e from Schedule s inner join EnquiryDetail e on e.id=s.enquiryDetails inner join Celebrity c on c.id=e.celebrity where c.id=:id")
  List<Schedule> findByEnquiryDetails_Celebrity_Id(String id);

  // String deleteAllByCelebrityId(String id);

}
