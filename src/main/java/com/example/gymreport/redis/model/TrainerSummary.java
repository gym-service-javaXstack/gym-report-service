package com.example.gymreport.redis.model;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;
import java.io.Serializable;
import java.time.Month;
import java.util.EnumMap;
import java.util.Map;

@Data
@RedisHash("TrainerSummary")
public class TrainerSummary implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    private Map<Month, Long> monthlyWorkLoad;

    public TrainerSummary() {
        this.monthlyWorkLoad = new EnumMap<>(Month.class);
    }
}