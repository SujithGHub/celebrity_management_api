package com.example.celebrity_management.Service;

import com.example.celebrity_management.Repository.ScheduleRepository;
import com.example.celebrity_management.model.ScheduleModel;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

  @Autowired
  private ScheduleRepository scheduleRepository;

  public ScheduleModel create(ScheduleModel scheduleModel) {
    return scheduleRepository.save(scheduleModel);
  }

  public List<ScheduleModel> getAll() {
    return scheduleRepository.findAll();
  }

  public Optional<ScheduleModel> getById(String id) {
    return scheduleRepository.findById(id);
  }

  public List<ScheduleModel> delete(String id) {
    scheduleRepository.deleteById(id);
    return getAll();
  }
}
