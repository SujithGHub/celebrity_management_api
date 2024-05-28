package com.example.celebrity_management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.celebrity_management.model.Topics;

public interface TopicsRepo extends JpaRepository<Topics, String>  {

    Page<Topics> findAll(Pageable pageable);

    Page<Topics> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Topics findByNameIgnoreCase(String name);

}
