package com.example.celebrity_management.service;

import com.example.celebrity_management.model.Schedule;
import com.example.celebrity_management.repository.ScheduleRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

  @Autowired
  private ScheduleRepository scheduleRepository;

  public Schedule create(Schedule scheduleModel) {
    return scheduleRepository.save(scheduleModel);
  }

  public List<Schedule> getAll() {
    return scheduleRepository.findAll();
  }

  public Optional<Schedule> getById(String id) {
    return scheduleRepository.findById(id);
  }

  public List<Schedule> delete(String id) {
    scheduleRepository.deleteById(id);
    return getAll();
  }
}
