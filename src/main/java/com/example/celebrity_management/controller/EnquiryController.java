package com.example.celebrity_management.controller;

import com.example.celebrity_management.model.EnquiryDetail;
import com.example.celebrity_management.model.Schedule;
import com.example.celebrity_management.props.SuccessResponse;
import com.example.celebrity_management.service.EnquiryService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(value = "/enquiry")
public class EnquiryController {

  @Autowired
  private EnquiryService enquiryService;

  @PostMapping
  public SuccessResponse CreateEnquiry(@RequestBody EnquiryDetail enquiryDetail) {
    return new SuccessResponse("Enquiry Submitted", enquiryService.create(enquiryDetail));
  }

  @PostMapping(value = "/status")
  public EnquiryDetail Status(@RequestBody Schedule schedule) throws Exception {
    return enquiryService.statusChange(schedule);
  }

  @GetMapping("/get-date-enquiry")
  public SuccessResponse getAllWithinWeekEnquiryDetails() {
    return new SuccessResponse("", enquiryService.getEnquiriesWithinCurrentWeek());
  }

  @GetMapping("/get-all-enquiry")
  public SuccessResponse getAllEnquiryDetails() {
    return new SuccessResponse("", enquiryService.getAll());
  }
  @GetMapping("{id}")
  public Optional<EnquiryDetail> getAllEnquiryById(
      @PathVariable("id") String id) {
    return enquiryService.getById(id);
  }

  @DeleteMapping("{id}")
  public List<EnquiryDetail> deleteEnquiry(@PathVariable("id") String id) {
    return enquiryService.delete(id);
  }

  @GetMapping("/getByCelebrityId/{id}")
  public List<EnquiryDetail> getAllEventsById(@PathVariable("id") String id) {
    return enquiryService.getAllEvents(id);
  }

  @GetMapping("/get")
  public Page<EnquiryDetail> getAllEnq(@RequestParam(name = "page") int page,@RequestParam(name = "size") int size) {
      return enquiryService.getAllEnquiries(page, size);
  }
  
}
