package com.example.celebrity_management.controller;

import com.example.celebrity_management.service.ScheduleService;
import com.example.celebrity_management.model.Schedule;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

  @Autowired
  private ScheduleService scheduleService;

  @PostMapping
  public Schedule createSchedule(@RequestBody Schedule ScheduleModel) throws Exception {
    return scheduleService.create(ScheduleModel);
  }

  @GetMapping("/get-all-schedule")
  private List<Schedule> getAllSchedule() {
    return scheduleService.getAll();
  }

  @GetMapping("/celebrity-id/{id}")
  public List<Schedule> getByCelebrityId(@PathVariable String id) {
    return scheduleService.getByCelebrityId(id);
  }

  @GetMapping("{id}")
  private Optional<Schedule> getScheduleById(@PathVariable String id) {
    return scheduleService.getById(id);
  }

  @PostMapping("/status/{id}")
  public Schedule changeStatus(@PathVariable("id") String id)  {
    return scheduleService.changeEventStatus(id);
  }

  @DeleteMapping("{id}")
  public List<Schedule> deleteScheduleById(@PathVariable String id) {
    return scheduleService.delete(id);
  }
}
