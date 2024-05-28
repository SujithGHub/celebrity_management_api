package com.example.celebrity_management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.celebrity_management.Exception.InvalidDataException;
import com.example.celebrity_management.model.Category;
import com.example.celebrity_management.model.Celebrity;
import com.example.celebrity_management.repository.CategoryRepo;
import com.example.celebrity_management.repository.CelebrityRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private CelebrityRepository celebrityRepository;

    public Category create(Category category){
        if(category.getName().isEmpty()){
            throw new InvalidDataException("Category name must not be empty");
        }
        category.setName(category.getName().trim());
        Category existingCategory=categoryRepo.findByNameIgnoreCase(category.getName());
        if ((existingCategory !=null && category.getId()== null) || (existingCategory !=null && existingCategory.getName().equalsIgnoreCase(category.getName()))) {
            throw new InvalidDataException("Category name aleady exists");
        }
        return categoryRepo.save(category);
    }

    public List<Category> getAll(){
        return categoryRepo.findAll();
    }

    public void deleteById(String id){
        List<Celebrity> cele=celebrityRepository.findByCategoryId(id);
        if (!cele.isEmpty()) {
            throw new InvalidDataException("celebrities are available in this category");
        }
         categoryRepo.deleteById(id);
    }
}
