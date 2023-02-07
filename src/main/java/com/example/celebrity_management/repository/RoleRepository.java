package com.example.celebrity_management.repository;
import com.example.celebrity_management.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
  
  Role findByName(String name);
}
