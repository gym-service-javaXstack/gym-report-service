package com.example.gymreport.redis.repository.impl;

import com.example.gymreport.redis.model.TrainerSummary;
import com.example.gymreport.redis.repository.TrainerWorkLoadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class TrainerWorkLoadRepositoryImpl implements TrainerWorkLoadRepository {
    private static final String TRAINER_SUMMARY_KEY = "TrainerSummary";

    private final HashOperations<String, String, TrainerSummary> hashOperations;

    @Override
    public void saveTrainerSummary(String username, TrainerSummary trainerSummary) {
        hashOperations.put(TRAINER_SUMMARY_KEY, username, trainerSummary);
    }

    @Override
    public Optional<Long> findTrainerWorkLoadByMonth(String username, int year, Month month) {
        TrainerSummary trainerSummary = hashOperations.get(TRAINER_SUMMARY_KEY, username);
        if (trainerSummary != null && trainerSummary.getYearlySummary().containsKey(year)) {
            Long workLoad = trainerSummary.getYearlySummary().get(year).get(month);
            return Optional.ofNullable(workLoad);
        }
        return Optional.empty();
    }

    @Override
    public Optional<TrainerSummary> findTrainerSummaryByUsername(String username) {
        TrainerSummary trainerSummary = hashOperations.get(TRAINER_SUMMARY_KEY, username);
        return Optional.ofNullable(trainerSummary);
    }
}
