package com.example.celebrity_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.celebrity_management.model.Category;
import com.example.celebrity_management.props.SuccessResponse;
import com.example.celebrity_management.service.CategoryService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@CrossOrigin("*")
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public SuccessResponse create(@RequestBody Category category) {
          return new SuccessResponse("Category Created", categoryService.create(category));
    }

    @GetMapping("/get-all-category")
    public List<Category> getAllCategories() {
        return categoryService.getAll();
    }
    
    @DeleteMapping("/remove/{id}")
    public void removeCategory(@PathVariable String id){
        categoryService.deleteById(id);
    }
    
}
