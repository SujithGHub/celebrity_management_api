package com.example.celebrity_management.util;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TrimStringConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return attribute!= null? attribute.trim() : null;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData!= null? dbData.trim() : null;
    }
}