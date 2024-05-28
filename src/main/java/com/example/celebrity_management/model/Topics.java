package com.example.celebrity_management.model;


import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Topics extends BaseModel {
    
    private String name;

}
