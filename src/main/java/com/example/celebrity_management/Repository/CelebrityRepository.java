package com.example.celebrity_management.Repository;

import com.example.celebrity_management.model.CelebrityModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CelebrityRepository
  extends JpaRepository<CelebrityModel, String> {}
