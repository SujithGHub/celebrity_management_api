package com.example.celebrity_management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.celebrity_management.Exception.InvalidDataException;
import com.example.celebrity_management.model.Schedule;
import com.example.celebrity_management.repository.ScheduleRepository;
import com.example.celebrity_management.util.Types.EventStatus;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ScheduleService {

  @Autowired
  private ScheduleRepository scheduleRepository;

  public Schedule create(Schedule schedule) throws InvalidDataException {

    List<Schedule> enq = scheduleRepository
        .findByEnquiryDetails_Celebrity_Id(schedule.getEnquiryDetails().getCelebrity().getId());
    int count = 0;
    for (Schedule e : enq) {
      if (!(schedule.getEnquiryDetails().getStartTime().before(e.getEnquiryDetails().getStartTime())
          && schedule.getEnquiryDetails().getEndTime().before(e.getEnquiryDetails().getEndTime())
          ||
          schedule.getEnquiryDetails().getStartTime().after(e.getEnquiryDetails().getStartTime())
              && schedule.getEnquiryDetails().getEndTime().after(e.getEnquiryDetails().getEndTime()))) {
        count++;
      }
    }
    if (count == 0) {
      return scheduleRepository.save(schedule);
    } else {
      throw new InvalidDataException("Another schedule available on this Date/Time");
    }
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
    return scheduleRepository.findByEnquiryDetails_Celebrity_Id(id);
  }

  public Schedule changeEventStatus(String id) {

    Schedule schedule = scheduleRepository.findById(id).orElse(null);

    EventStatus status = schedule.getEnquiryDetails().getStatus();
    if (status == EventStatus.ACCEPTED) {
      schedule.getEnquiryDetails().setStatus(EventStatus.REJECTED);
    } else {
      schedule.getEnquiryDetails().setStatus(EventStatus.ACCEPTED);
    }
    return scheduleRepository.save(schedule);
  }
}