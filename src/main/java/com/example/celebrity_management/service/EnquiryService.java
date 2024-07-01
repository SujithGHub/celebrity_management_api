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
import com.example.celebrity_management.repository.ScheduleRepository;
import com.example.celebrity_management.util.Types;

@Service
public class EnquiryService {

  @Autowired
  private EnquiryRepository enquiryRepository;

  @Autowired
  private ScheduleService scheduleService;

  @Autowired
  private ScheduleRepository scheduleRepository;

  @Autowired
  private BlockDatesRepo blockDatesRepo;

  @Autowired
  private PrefixService prefixService;

  @Transactional(rollbackFor = Exception.class)
  public EnquiryDetail create(EnquiryDetail enquiryDetail) throws InvalidDataException {
      if (enquiryDetail.getEnquiryNo() == null) {
          String prefix = prefixService.createNumberSequence(Types.PrefixType.ENQUIRY);
          enquiryDetail.setEnquiryNo(prefix);
      }

      validateEnquiryTimes(enquiryDetail);
      checkCelebrityAvailability(enquiryDetail);

      return enquiryRepository.save(enquiryDetail);
  }

    public EnquiryDetail statusChange(Schedule schedule) throws Exception {
        Schedule existingSchedule = scheduleRepository.findByEnquiryDetails_Id(schedule.getEnquiryDetails().getId());
        if (existingSchedule != null) {
            scheduleRepository.deleteById(existingSchedule.getId());
        }

        EnquiryDetail enquiryDetail = schedule.getEnquiryDetails();

        validateEnquiryTimes(enquiryDetail);
        checkCelebrityAvailability(enquiryDetail);

        if (schedule.getEnquiryDetails().getStatus() == Types.EventStatus.ACCEPTED) {
            scheduleService.create(schedule);
        }

        return enquiryRepository.save(schedule.getEnquiryDetails());
    }

    private void validateEnquiryTimes(EnquiryDetail enquiryDetail) throws InvalidDataException {
        if (enquiryDetail.getEndTime() != null && enquiryDetail.getStartTime() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(enquiryDetail.getStartTime());
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            Date newDate = calendar.getTime();
            if (newDate.after(enquiryDetail.getEndTime())) {
                throw new InvalidDataException("At least select 1 hour");
            }
        }
    }

    private void checkCelebrityAvailability(EnquiryDetail enquiryDetail) throws InvalidDataException {
        if (enquiryDetail.getCelebrityIds() != null) {
            String endDateString = formatDate(enquiryDetail.getEndTime());

            List<Celebrity> celebrities = enquiryDetail.getCelebrityIds();
            for (Celebrity celebrity : celebrities) {
                BlockDates blockDate = blockDatesRepo.findByCelebrityIdAndDate(celebrity.getId(), endDateString);
                if (blockDate != null) {
                    throw new InvalidDataException("Celebrity " + celebrity.getName() + " is not available on this date");
                }
            }
        }
    }

    private String formatDate(Date date) throws InvalidDataException {
        if (date == null) {
            return null;
        }
        
        DateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return outputDateFormat.format(date);
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


  public List<EnquiryDetail> getEnquiriesWithinCurrentWeek() {
    Date startOfWeek = getStartOfWeek();
    Date endOfWeek = getEndOfWeek();

    return enquiryRepository.findByStartTimeBetween(startOfWeek, endOfWeek);
}

private Date getStartOfWeek() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
    return calendar.getTime();
}

private Date getEndOfWeek() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6);
    return calendar.getTime();
}
}
