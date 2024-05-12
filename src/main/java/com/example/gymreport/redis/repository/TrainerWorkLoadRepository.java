package com.example.gymreport.redis.repository;

import com.example.gymreport.redis.model.TrainerSummary;

import java.time.Month;
import java.util.Optional;

public interface TrainerWorkLoadRepository {
    void saveTrainerSummary(String username, TrainerSummary trainerSummary);

    Optional<Integer> findTrainerWorkLoadByMonth(String username, int year, Month month);

    Optional<TrainerSummary> findTrainerSummaryByUsername(String username);
}
