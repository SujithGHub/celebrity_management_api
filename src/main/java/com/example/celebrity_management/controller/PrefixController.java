package com.example.celebrity_management.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.celebrity_management.model.Prefix;
import com.example.celebrity_management.props.SuccessResponse;
import com.example.celebrity_management.service.PrefixService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "/prefix")
public class PrefixController {

    @Autowired
    private PrefixService prefixService;
    
    @PostMapping("/add")
    public SuccessResponse createPrefix(@RequestBody Prefix prefix) {
        return new SuccessResponse("Prefix Submitted", prefixService.createNew(prefix));
    }


    @GetMapping("/get-all-prefix")
    public List<Prefix> getAllPrefixs() {
        return prefixService.getAll();
    }
    
    
}
