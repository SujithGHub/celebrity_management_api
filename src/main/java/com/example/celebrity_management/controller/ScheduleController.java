package com.example.celebrity_management.controller;

import com.example.celebrity_management.Service.ScheduleService;
import com.example.celebrity_management.model.ScheduleModel;
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
  public ScheduleModel createShedule(@RequestBody ScheduleModel ScheduleModel) {
    return scheduleService.create(ScheduleModel);
  }

  @GetMapping("/get-all-schedule")
  private List<ScheduleModel> getAllSchedule() {
    return scheduleService.getAll();
  }

  @GetMapping("{id}")
  private Optional<ScheduleModel> getScheduleById(@PathVariable String id) {
    return scheduleService.getById(id);
  }
  @DeleteMapping("{id}")
  public List<ScheduleModel> deleteScheduleById(@PathVariable String id){
   return scheduleService.delete(id);
  }
}
