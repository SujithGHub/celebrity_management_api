package com.example.celebrity_management.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.celebrity_management.model.EnquiryDetail;

public interface EnquiryRepository extends JpaRepository<EnquiryDetail, String> {

  List<EnquiryDetail> findByCelebrityId(String id);

  Page<EnquiryDetail> findAll(Pageable pageable);

  List<EnquiryDetail> findByStartTimeBetween(Date startOfWeek, Date endOfWeek);
}
