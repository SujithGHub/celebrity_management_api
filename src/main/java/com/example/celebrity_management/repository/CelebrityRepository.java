package com.example.celebrity_management.repository;

import com.example.celebrity_management.model.Celebrity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CelebrityRepository
  extends JpaRepository<Celebrity, String> {

    public Optional<Celebrity> findByMailId(String email);
  }
