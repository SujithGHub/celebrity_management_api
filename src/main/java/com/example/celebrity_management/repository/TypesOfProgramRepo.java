package com.example.celebrity_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.celebrity_management.model.TypesOfProgram;

public interface TypesOfProgramRepo  extends JpaRepository<TypesOfProgram, String> {
    
}
