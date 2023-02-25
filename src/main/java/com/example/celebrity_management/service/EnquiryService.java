package com.example.celebrity_management.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import java.util.Locale;

@Service
public class EnquiryService {

  @Autowired
  private EnquiryRepository enquiryRepository;

  @Autowired
  private ScheduleService scheduleService;

  @Autowired
  private BlockDatesRepo blockDatesRepo;

  public EnquiryDetail create(EnquiryDetail enquiryDetail) throws InvalidDataException {
    int count = 0;
    if (enquiryDetail.getCelebrity() != null) {
      String inputDateStr = String.valueOf(enquiryDetail.getEndTime());
      DateFormat inputDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
      try {
        Date inputDate = inputDateFormat.parse(inputDateStr);

        DateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String outputDateStr = outputDateFormat.format(inputDate);

        BlockDates blockedDate = blockDatesRepo.findByCelebrityIdAndDate(enquiryDetail.getCelebrity().getId(),
            outputDateStr);

        if (blockedDate == null) {
          List<EnquiryDetail> enq = enquiryRepository.findByCelebrityId(enquiryDetail.getCelebrity().getId() );
          for (EnquiryDetail e : enq) {
            if (!(enquiryDetail.getStartTime().before(e.getStartTime())
                && enquiryDetail.getEndTime().before(e.getEndTime())
                ||
                enquiryDetail.getStartTime().after(e.getStartTime())
                    && enquiryDetail.getEndTime().after(e.getEndTime()))) {
              count++;
            }
          }
        } else {
          count++;
          throw new InvalidDataException("Booking is blocked on this Date");
        }
      } catch (ParseException e) {
        System.out.println("Error parsing date: " + e.getMessage());
      }
      if (count == 0) {
        return enquiryRepository.save(enquiryDetail);
      } else {
        throw new InvalidDataException("choose another date");
      }
    } else {
      return enquiryRepository.save(enquiryDetail);
    }
  }

  public EnquiryDetail statusChange(Schedule schedule) {
    EnquiryDetail enquiryDetail = enquiryRepository.findById(schedule.getEnquiryId()).orElse(null);

    enquiryDetail.setStatus(schedule.getStatus());

    scheduleService.create(schedule);
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
