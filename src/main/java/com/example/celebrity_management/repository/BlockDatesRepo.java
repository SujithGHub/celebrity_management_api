package com.example.celebrity_management.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.celebrity_management.model.BlockDates;

public interface BlockDatesRepo extends JpaRepository<BlockDates, String> {

    @Query(value = "SELECT * FROM block_dates WHERE blocked_date = TO_DATE(:outputDateStr, 'YYYY-MM-DD') AND celebrity_id = :id", nativeQuery = true)
    BlockDates findByCelebrityIdAndDate(@Param("id") String id, @Param("outputDateStr") String outputDateStr);

    BlockDates findByCelebrityIdAndBlockedDate(String celebrityId, Date date);

    List<BlockDates> findByCelebrityId(String id);

   
  @Query("SELECT COUNT(b) FROM BlockDates b WHERE b.celebrityId = :celebrityId AND " +
         " ((" + "b.blockedDate < :endTime AND b.blockedDate < :startTime) OR " +
                 "(b.blockedDate > :endTime AND b.blockedDate> :startTime))")
    Long findByBlockDateCount(@Param("celebrityId") String celebrityId,
                              @Param("startTime") Date startTime,
                              @Param("endTime") Date endTime);
                      
                             
                   
}
