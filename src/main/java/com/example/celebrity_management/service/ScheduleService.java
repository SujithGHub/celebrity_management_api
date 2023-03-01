package com.example.celebrity_management.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.celebrity_management.Exception.InvalidDataException;
import com.example.celebrity_management.Exception.ResourceNotFoundException;
import com.example.celebrity_management.model.Schedule;
import com.example.celebrity_management.repository.ScheduleRepository;
import com.example.celebrity_management.util.Types;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ScheduleService {

  @Autowired
  private ScheduleRepository scheduleRepository;

  public Schedule create(Schedule schedule) throws InvalidDataException {

    List<Schedule> enq = scheduleRepository.findByCelebrityId(schedule.getCelebrity().getId());
    int count = 0;
    for (Schedule e : enq) {
      if (!(schedule.getStartTime().before(e.getStartTime())
          && schedule.getEndTime().before(e.getEndTime())
          ||
          schedule.getStartTime().after(e.getStartTime())
              && schedule.getEndTime().after(e.getEndTime()))) {
        count++;
      }
    }
    if (count == 0) {
      return schedule;
    } else {
      throw new InvalidDataException("Another schedule available on this particular Date/Time");
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

  public String deleteByCelebrityId(String id) {
    return scheduleRepository.deleteAllByCelebrityId(id);
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