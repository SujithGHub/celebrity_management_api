package com.example.celebrity_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.example.celebrity_management.model.Prefix;
import com.example.celebrity_management.util.Types.PrefixType;

import jakarta.persistence.LockModeType;

public interface PrefixRepo extends JpaRepository<Prefix, String>  {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Prefix getByPrefixType(PrefixType prefixType);

    
}
