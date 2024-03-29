package com.example.celebrity_management.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.celebrity_management.model.EnquiryDetail;

public interface EnquiryRepository extends JpaRepository<EnquiryDetail, String> {

  List<EnquiryDetail> findByCelebrityId(String id);

}
