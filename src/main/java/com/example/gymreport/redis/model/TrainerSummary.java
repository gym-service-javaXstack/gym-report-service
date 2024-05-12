package com.example.gymreport.redis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@RedisHash("TrainerSummary")
public class TrainerSummary implements Serializable {
    private String username;
    private String firstName;
    private String lastName;
    private Boolean status;
    private Map<Integer, Map<Month, Integer>> yearlySummary;

    public TrainerSummary() {
        this.yearlySummary = new HashMap<>();
    }
}