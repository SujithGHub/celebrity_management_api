package com.example.celebrity_management.service;
import com.example.celebrity_management.model.EnquiryDetail;
import com.example.celebrity_management.repository.EnquiryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnquiryService {

  @Autowired
  private EnquiryRepository enquiryRepository;

  public EnquiryDetail create(EnquiryDetail enquiryDetail) {
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

  public List<EnquiryDetail> getAllEvents(String id){
    return enquiryRepository.findByCelebrityId(id);
  }
}
