package com.example.celebrity_management.controller;

import com.example.celebrity_management.model.EnquiryDetail;
import com.example.celebrity_management.service.EnquiryService;
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
@RequestMapping(value = "/enquiry")
public class EnquiryController {

  @Autowired
  private EnquiryService enquiryService;

  @PostMapping
  public EnquiryDetail CreateEnquiry(@RequestBody EnquiryDetail enquiryDetail) {
    return enquiryService.create(enquiryDetail);
  }

  @GetMapping("/get-all-enquiry")
  public List<EnquiryDetail> getAllEnquiryDetails() {
    return enquiryService.getAll();
  }

  @GetMapping("{id}")
  public Optional<EnquiryDetail> getAllEnquiryById(
    @PathVariable("id") String id
  ) {
    return enquiryService.getById(id);
  }

  @DeleteMapping("{id}")
  public List<EnquiryDetail> deleteEnquiry(@PathVariable("id") String id) {
    return enquiryService.delete(id);
  }
}