package com.example.celebrity_management.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.celebrity_management.model.EnquiryDetail;
import com.example.celebrity_management.model.Schedule;
import com.example.celebrity_management.repository.EnquiryRepository;
import com.example.celebrity_management.util.Types;

@Service
public class EnquiryService {

  @Autowired
  private EnquiryRepository enquiryRepository;

  @Autowired
  private ScheduleService scheduleService;

  // public static boolean timeChecker(List<Date> stime, List<Date> etime, Date
  // fromDate, Date endDate) {
  // int count = 0;
  // for (int i = 0; i < stime.size(); i++) {
  // if (stime.get(i).getDate() == fromDate.getDate()) {
  // if (endDate.after(stime.get(i)) && endDate.after(etime.get(i))
  // || fromDate.before(stime.get(i)) && fromDate.before(etime.get(i))) {
  // } else {
  // count++;
  // }
  // }
  // }
  // if (count == 0) {
  // return true;
  // } else {
  // return false;
  // }
  // }

  public EnquiryDetail create(EnquiryDetail enquiryDetail) {
    int count = 0;
    List<EnquiryDetail> enq = enquiryRepository.findByCelebrityId(enquiryDetail.getCelebrity().getId());
    for (EnquiryDetail e : enq) {
      if (!(enquiryDetail.getStartTime().before(e.getStartTime()) && enquiryDetail.getEndTime().before(e.getEndTime())
          ||
          enquiryDetail.getStartTime().after(e.getStartTime()) && enquiryDetail.getEndTime().after(e.getEndTime()))) {
        count++;
      }
    }
    return count == 0 ? enquiryRepository.save(enquiryDetail) : null;
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
