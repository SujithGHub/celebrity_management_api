package com.example.celebrity_management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.celebrity_management.model.TypesOfProgram;
import com.example.celebrity_management.repository.TypesOfProgramRepo;

@Service
public class TypesOfProgramService {
    @Autowired
    private TypesOfProgramRepo typesOfProgramRepo;

    public TypesOfProgram create(TypesOfProgram typesOfProgram){
        return typesOfProgramRepo.save(typesOfProgram);
    }

    public List<TypesOfProgram> getAll() {
        return typesOfProgramRepo.findAll();
    }

     public void remove(String id){
        typesOfProgramRepo.deleteById(id);
     }
     
    public Optional<TypesOfProgram> getById(String id){
        return typesOfProgramRepo.findById(id);
    }
}
