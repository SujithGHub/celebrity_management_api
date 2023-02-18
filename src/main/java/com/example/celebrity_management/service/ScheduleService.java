package com.example.celebrity_management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.celebrity_management.Exception.ResourceNotFoundException;
import com.example.celebrity_management.model.Schedule;
import com.example.celebrity_management.repository.EnquiryRepository;
import com.example.celebrity_management.repository.ScheduleRepository;
import com.example.celebrity_management.util.Types;

@Service
public class ScheduleService {

  @Autowired
  private ScheduleRepository scheduleRepository;

  @Autowired
  private EnquiryRepository enquiryRepository;

  public Schedule create(Schedule scheduleModel) {
    // enquiryRepository.deleteById(scheduleModel.getEnquiryId());
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

  public List<Schedule> getByCelebrityId(String id) {
    return scheduleRepository.findByCelebrityId(id);
  }

  public Schedule changeEventStatus(String id, String status) {
    Schedule schedule = scheduleRepository.findById(id).orElse(null);

    if (schedule == null) {
      throw new ResourceNotFoundException("Schedule is Null!!!");
    }
    schedule.setStatus(Types.EventStatus.valueOf(status));

    return scheduleRepository.save(schedule);
  }
}
