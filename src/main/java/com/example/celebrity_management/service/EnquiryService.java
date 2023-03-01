package com.example.celebrity_management.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.celebrity_management.Exception.InvalidDataException;
import com.example.celebrity_management.model.BlockDates;
import com.example.celebrity_management.model.EnquiryDetail;
import com.example.celebrity_management.model.Schedule;
import com.example.celebrity_management.repository.BlockDatesRepo;
import com.example.celebrity_management.repository.EnquiryRepository;
import com.example.celebrity_management.util.Types;
import jakarta.transaction.Transactional;

import java.util.Locale;

@Service

@Transactional
public class EnquiryService {

  @Autowired
  private EnquiryRepository enquiryRepository;

  @Autowired
  private ScheduleService scheduleService;

  @Autowired
  private BlockDatesRepo blockDatesRepo;

  public EnquiryDetail create(EnquiryDetail enquiryDetail) throws InvalidDataException {


Calendar calendar = Calendar.getInstance();
calendar.setTime(enquiryDetail.getStartTime());
calendar.add(Calendar.HOUR_OF_DAY, 1);
Date newDate = calendar.getTime();

if(newDate.after(enquiryDetail.getEndTime())){
  throw new InvalidDataException("atleast select 1 hour");
}


    if (enquiryDetail.getCelebrity() != null) {
      String inputDateStr = String.valueOf(enquiryDetail.getEndTime());
      DateFormat inputDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

      try {
        Date inputDate = inputDateFormat.parse(inputDateStr);
        DateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String outputDateStr = outputDateFormat.format(inputDate);

        BlockDates blockedDate = blockDatesRepo.findByCelebrityIdAndDate(enquiryDetail.getCelebrity().getId(),outputDateStr);

        if (blockedDate == null) {
          return enquiryRepository.save(enquiryDetail);
        } else {
          throw new InvalidDataException("celebrity not available on this particular Date");
        }
      } catch (ParseException e) {
        System.out.println("Error parsing date: " + e.getMessage());
      }
    }
    return enquiryRepository.save(enquiryDetail);

  }

  public EnquiryDetail statusChange(Schedule schedule) {
    if(schedule.getStatus()==Types.EventStatus.ACCEPTED){
      scheduleService.create(schedule);
    }
  
    EnquiryDetail enquiryDetail = enquiryRepository.findById(schedule.getEnquiryId()).orElse(null);
    enquiryDetail.setStatus(schedule.getStatus());
    return enquiryRepository.save(enquiryDetail);
  }

  public List<EnquiryDetail> getAll() {
    return enquiryRepository.findAll();
  }

  public Optional<EnquiryDetail> getById(String id) {
    return enquiryRepository.findById(id);
  }

  public List<EnquiryDetail> delete(String id) {
    enquiryRepository.deleteById(id);
    return getAll();
  }

  public List<EnquiryDetail> getAllEvents(String id) {
    return enquiryRepository.findByCelebrityId(id);
  }
}
