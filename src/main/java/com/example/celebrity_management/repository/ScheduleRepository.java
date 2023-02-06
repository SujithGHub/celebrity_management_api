package com.example.celebrity_management.repository;

import com.example.celebrity_management.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {
  
}
