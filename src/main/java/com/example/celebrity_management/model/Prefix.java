package com.example.celebrity_management.model;

import com.example.celebrity_management.util.Types;
import com.example.celebrity_management.util.Types.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Prefix extends BaseModel {

    private String prefix;

    @Column(name = "current_sequence")
    private long currentSequence = 1;

    @Column(name = "prefix_type")
    private Types.PrefixType prefixType;

    @Column(name = "status")
    private Status status = Status.ACTIVE;


    public void incrementSqeuenceNo() {
        this.currentSequence = currentSequence + 1;
    }
}
