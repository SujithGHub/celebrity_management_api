package com.example.celebrity_management.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.celebrity_management.Exception.InvalidDataException;
import com.example.celebrity_management.model.BlockDates;
import com.example.celebrity_management.model.Celebrity;
import com.example.celebrity_management.model.EnquiryDetail;
import com.example.celebrity_management.model.Schedule;
import com.example.celebrity_management.repository.BlockDatesRepo;
import com.example.celebrity_management.repository.EnquiryRepository;
import com.example.celebrity_management.util.Types;

@Service
public class EnquiryService {

  @Autowired
  private EnquiryRepository enquiryRepository;

  @Autowired
  private ScheduleService scheduleService;

  @Autowired
  private BlockDatesRepo blockDatesRepo;

  @Autowired
  private PrefixService prefixService;

  @Transactional
  public EnquiryDetail create(EnquiryDetail enquiryDetail) throws InvalidDataException {
    
    String prefix=prefixService.createNumberSequence(Types.PrefixType.ENQUIRY);
    enquiryDetail.setEnquiryNo(prefix);

if (enquiryDetail.getEndTime() != null && enquiryDetail.getStartTime() != null) {
      Calendar calendar = Calendar.getInstance();
        calendar.setTime(enquiryDetail.getStartTime());
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        Date newDate = calendar.getTime();
    if (newDate.after(enquiryDetail.getEndTime())) {
      throw new InvalidDataException("atleast select 1 hour");
    }
    if (enquiryDetail.getCelebrityIds() != null ) {
      String inputDateStr = String.valueOf(enquiryDetail.getEndTime());
      DateFormat inputDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
      try {
        Date inputDate = inputDateFormat.parse(inputDateStr);
        DateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String outputDateStr = outputDateFormat.format(inputDate);

        // BlockDates blockedDate = blockDatesRepo.findByCelebrityIdAndDate(enquiryDetail.getCelebrity().getId(),
        //     outputDateStr);

        // if (blockedDate == null) {
        //   return enquiryRepository.save(enquiryDetail);
        // } else {
        //   throw new InvalidDataException("Celebrity Not Available on this Date");
        // }
//Alter for List of  Celebrities
        List<Celebrity> cl = enquiryDetail.getCelebrityIds();
        for (Celebrity celebrity : cl) {
            BlockDates blockDate = blockDatesRepo.findByCelebrityIdAndDate(celebrity.getId(), outputDateStr);
            if (blockDate != null) {
                throw new InvalidDataException("Celebrity "+celebrity.getName()+" Not Available on this Date");
            }
        }
        
        // If none of the celebrities are blocked on the specified date, save the enquiry
        return enquiryRepository.save(enquiryDetail);
        
      } catch (ParseException e) {
        System.out.println("Error parsing date: " + e.getMessage());
      }
    }
  }
    return enquiryRepository.save(enquiryDetail);
  }

  public EnquiryDetail statusChange(Schedule schedule) throws Exception {
    if (schedule.getEnquiryDetails().getStatus() == Types.EventStatus.ACCEPTED) {
      scheduleService.create(schedule);
    }
    return enquiryRepository.save(schedule.getEnquiryDetails());
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

  public Page<EnquiryDetail> getAllEnquiries(int pageNo,int pageSize){
     Pageable pageable = PageRequest.of(pageNo, pageSize);
    return enquiryRepository.findAll(pageable);
  }
}
