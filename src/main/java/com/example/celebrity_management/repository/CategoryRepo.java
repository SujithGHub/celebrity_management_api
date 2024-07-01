package com.example.celebrity_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.celebrity_management.model.Category;

public interface CategoryRepo extends JpaRepository<Category, String>{

    Category findByNameIgnoreCase(String name);
    
}
