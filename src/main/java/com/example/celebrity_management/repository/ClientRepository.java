package com.example.celebrity_management.repository;

import com.example.celebrity_management.model.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientModel, String> {}
