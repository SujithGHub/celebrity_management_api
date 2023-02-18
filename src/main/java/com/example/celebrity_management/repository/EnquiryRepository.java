package com.example.celebrity_management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.celebrity_management.model.EnquiryDetail;
import java.util.Date;

public interface EnquiryRepository extends JpaRepository<EnquiryDetail, String> {

  List<EnquiryDetail> findByCelebrityId(String id);

  EnquiryDetail save(Optional<EnquiryDetail> enquiryDetail);

}
