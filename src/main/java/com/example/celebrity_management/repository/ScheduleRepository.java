package com.example.celebrity_management.repository;

import com.example.celebrity_management.model.Schedule;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {

  List<Schedule> findByCelebrityId(String id);

}
