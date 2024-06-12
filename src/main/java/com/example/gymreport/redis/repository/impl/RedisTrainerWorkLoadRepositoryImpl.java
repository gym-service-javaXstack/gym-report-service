package com.example.gymreport.redis.repository.impl;

import com.example.gymreport.model.TrainerSummary;
import com.example.gymreport.redis.repository.RedisTrainerWorkLoadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "NOSQL_TYPE", havingValue = "redis")
public class RedisTrainerWorkLoadRepositoryImpl implements RedisTrainerWorkLoadRepository {
    private static final String TRAINER_SUMMARY_KEY = "TrainerSummary";

    private final HashOperations<String, String, TrainerSummary> hashOperations;

    @Override
    public void saveTrainerSummary(String username, TrainerSummary trainerSummary) {
        hashOperations.put(TRAINER_SUMMARY_KEY, username, trainerSummary);
    }

    @Override
    public Optional<TrainerSummary> findTrainerSummaryByUsername(String username) {
        TrainerSummary trainerSummary = hashOperations.get(TRAINER_SUMMARY_KEY, username);
        return Optional.ofNullable(trainerSummary);
    }
}
