package com.example.celebrity_management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.celebrity_management.Exception.InvalidDataException;
import com.example.celebrity_management.model.Prefix;
import com.example.celebrity_management.repository.PrefixRepo;
import com.example.celebrity_management.util.Types;
import com.example.celebrity_management.util.Types.PrefixType;

@Service
public class PrefixService {
    @Autowired
    
    private PrefixRepo prefixRepo;
    
    public String createNumberSequence(Types.PrefixType prefixType) {
        Prefix prefix = getPrefixWithRightLock(prefixType);
        String value = formatPrefix(prefix.getPrefix(),Long.toString(prefix.getCurrentSequence()));
        prefix.incrementSqeuenceNo();
        prefixRepo.save(prefix);
        return value;
    }

    private Prefix getPrefixWithRightLock(PrefixType prefixType) {
        Prefix prefix = prefixRepo.getByPrefixType(prefixType);
        if (prefix == null) {
            throw new InvalidDataException("Invalid PrefixType");
        }
        
        Optional<Prefix> optionalPrefix = prefixRepo.findById(prefix.getId());
        if (optionalPrefix.isPresent()) {
                prefix = optionalPrefix.get();
            }else{
                throw new InvalidDataException("Invaid Prefix");
            }

        return prefix;
    }

    private String formatPrefix(String prefix, String numberSequence) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prefix);
        stringBuilder.append("-");
        stringBuilder.append(numberSequence);
        return stringBuilder.toString();
    }

    public Prefix createNew(Prefix prefix) {
        return prefixRepo.save(prefix);
    }

    public List<Prefix> getAll() {
       
       return prefixRepo.findAll();
    }



}
