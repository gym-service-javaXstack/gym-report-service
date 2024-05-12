package com.example.gymreport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

@Data
@Document
@AllArgsConstructor
@RedisHash("TrainerSummary")
@CompoundIndex(name = "first_last_name", def = "{'firstName': 1, 'lastName': 1}")
public class TrainerSummary implements Serializable {

    @Id
    private String username;
    private String firstName;
    private String lastName;
    private Boolean status;
    private Map<Integer, Map<Month, Integer>> yearlySummary;

    public TrainerSummary() {
        this.yearlySummary = new HashMap<>();
    }
}