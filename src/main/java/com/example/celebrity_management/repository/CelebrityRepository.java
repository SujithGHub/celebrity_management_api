package com.example.celebrity_management.repository;

import com.example.celebrity_management.model.CelebrityModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CelebrityRepository
  extends JpaRepository<CelebrityModel, String> {

    public Optional<CelebrityModel> findByMailId(String email);
  }
