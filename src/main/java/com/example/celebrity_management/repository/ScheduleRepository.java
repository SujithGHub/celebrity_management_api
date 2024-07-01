package com.example.celebrity_management.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.celebrity_management.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {

  // @Query("select e from Schedule s inner join EnquiryDetail e on
  // e.id=s.enquiryDetails inner join Celebrity c on c.id=e.celebrity where
  // c.id=:id")
  List<Schedule> findByEnquiryDetails_Celebrity_Id(String id);


@Query("SELECT COUNT(s) FROM Schedule s WHERE s.enquiryDetails.celebrity.id = :celebrityId AND " +
       "NOT ((" +
       "s.enquiryDetails.startTime < :endTime AND s.enquiryDetails.endTime < :startTime) OR " +
       "(s.enquiryDetails.startTime > :endTime AND s.enquiryDetails.endTime > :startTime))")
Long countNonOverlappingSchedules(@Param("celebrityId") String celebrityId, 
                                  @Param("startTime") Date startTime, 
                                  @Param("endTime") Date endTime);


  Schedule findByEnquiryDetails_Id(String id);
}
