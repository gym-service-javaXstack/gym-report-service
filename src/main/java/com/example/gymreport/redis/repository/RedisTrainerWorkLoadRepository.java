package com.example.gymreport.redis.repository;

import com.example.gymreport.model.TrainerSummary;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.Optional;

@ConditionalOnProperty(name = "NOSQL_TYPE", havingValue = "redis")
public interface RedisTrainerWorkLoadRepository {
    void saveTrainerSummary(String username, TrainerSummary trainerSummary);

    Optional<TrainerSummary> findTrainerSummaryByUsername(String username);
}
