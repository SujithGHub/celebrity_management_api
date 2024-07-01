package com.example.celebrity_management.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class TypesOfProgram extends BaseModel{
    
    private String ProgramName;
}
