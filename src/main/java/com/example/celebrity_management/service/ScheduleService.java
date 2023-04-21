package com.example.celebrity_management.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.example.celebrity_management.Exception.InvalidDataException;
import com.example.celebrity_management.model.Schedule;
import com.example.celebrity_management.repository.ScheduleRepository;
import com.example.celebrity_management.util.Types.EventStatus;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ScheduleService {

  @Autowired
  private ScheduleRepository scheduleRepository;
  @Autowired
  JavaMailSender sender;

  @Autowired
  private Configuration freemarkerConfig;

  
  public Schedule create(Schedule schedule) throws Exception {

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
      //to Client
      CompletableFuture.runAsync(() -> {

        try {
          confirmationMail(schedule ,"Client.html",schedule.getEnquiryDetails().getCelebrity().getMailId());
          confirmationMail(schedule ,"Celebrity.html",schedule.getEnquiryDetails().getMailId());
        } catch(Exception e) {
           e.printStackTrace();
           Thread.currentThread().interrupt();
        }
      });

      return scheduleRepository.save(schedule);
    } else {
      throw new InvalidDataException("Another schedule available on this Date/Time");
    }
  }

  public void confirmationMail(Schedule schedule,String fileName,String mailId) throws Exception {
    
    String subject = "CONFIRMATION MAIL";
    MimeMessage message = sender.createMimeMessage();

    MimeMessageHelper helper = new MimeMessageHelper(message);
    // Using a subfolder such as /templates here
    freemarkerConfig.setClassForTemplateLoading(ScheduleService.class, "/template");

    Template t = freemarkerConfig.getTemplate(fileName);
    String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, schedule.getEnquiryDetails());

    helper.setTo(mailId);
    helper.setText(text, true);
    helper.setSubject(subject);

    sender.send(message);

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