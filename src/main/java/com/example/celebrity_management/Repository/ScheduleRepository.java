package com.example.celebrity_management.Repository;

import com.example.celebrity_management.model.ScheduleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository
  extends JpaRepository<ScheduleModel, String> {}
