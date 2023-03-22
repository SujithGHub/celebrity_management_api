package com.example.celebrity_management.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

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
  @Autowired
  JavaMailSender mailSender;
  @Autowired
  TemplateEngine engine;

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
      confirmationMail(schedule);
      return scheduleRepository.save(schedule);
    } else {
      throw new InvalidDataException("Another schedule available on this Date/Time");
    }
  }

  public void confirmationMail(Schedule schedule) {
    String[] to = { schedule.getEnquiryDetails().getMailId(), schedule.getEnquiryDetails().getCelebrity().getMailId() };
    String subject = "CONFIRMATION MAIL";
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(schedule.getEnquiryDetails().getStartTime());
    Date newDate = calendar.getTime();
    String body = " On this day "+ newDate +" your event "+schedule.getEnquiryDetails().getEventName() + " Has been scheduled  " ;
    SimpleMailMessage message = new SimpleMailMessage();
    message.setCc(to);
    message.setSubject(subject);
    message.setText(body);
    mailSender.send(message);

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