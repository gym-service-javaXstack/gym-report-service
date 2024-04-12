package com.example.gymreport.redis.repository;

import com.example.gymreport.redis.model.TrainerSummary;

import java.time.Month;
import java.util.Optional;

public interface TrainerMonthlyWorkLoadRepository {
    void saveTrainerWorkLoad(String username, Month month, Long workLoad);

    Optional<Long> findTrainerWorkLoad(String username, Month month);

    void updateTrainerWorkLoad(String username, Month month, Long updatedWorkLoad);

    void deleteTrainerWorkLoad(String username, Month month);

    Optional<TrainerSummary> findTrainerSummaryByUsername(String username);
}
