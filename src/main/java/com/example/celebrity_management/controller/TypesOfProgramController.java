package com.example.celebrity_management.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.celebrity_management.model.TypesOfProgram;
import com.example.celebrity_management.service.TypesOfProgramService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;





@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "/programs")
public class TypesOfProgramController {
    @Autowired
    private TypesOfProgramService typesOfProgramService;

    @PostMapping("/add")
    public TypesOfProgram createProgram(@RequestBody TypesOfProgram typesOfProgram) {
        
        return typesOfProgramService.create(typesOfProgram);
    }

    @GetMapping("/get-all-programs")
    public List<TypesOfProgram> getAllPrograms() {
        return typesOfProgramService.getAll();
    }
    
    @GetMapping("/{id}")
    public Optional<TypesOfProgram> getByProgramId(@PathVariable String id) {
        return typesOfProgramService.getById(id);
    }
    
   
  
}
