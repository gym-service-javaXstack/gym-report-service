package com.example.gymreport.redis.repository.impl;

import com.example.gymreport.redis.model.TrainerSummary;
import com.example.gymreport.redis.repository.TrainerMonthlyWorkLoadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.Optional;

@Repository
@Slf4j
public class TrainerMonthlyWorkLoadRepositoryImpl implements TrainerMonthlyWorkLoadRepository {
    private static final String TRAINER_SUMMARY_KEY = "TrainerSummary";

    private RedisTemplate<String, TrainerSummary> redisTemplate;
    private HashOperations<String, String, TrainerSummary> hashOperations;

    public TrainerMonthlyWorkLoadRepositoryImpl(RedisTemplate<String, TrainerSummary> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void saveTrainerWorkLoad(String username, Month month, Long workLoad) {
        log.info("Entry TrainerMonthlyWorkLoadRepositoryImpl saveTrainerWorkLoad method username {}, month {}, workload {}", username, month, workLoad);
        TrainerSummary trainerSummary = findTrainerSummaryByUsername(username).orElse(new TrainerSummary());
        trainerSummary.getMonthlyWorkLoad().put(month, workLoad);
        hashOperations.put(TRAINER_SUMMARY_KEY, username, trainerSummary);
    }

    @Override
    public Optional<Long> findTrainerWorkLoad(String username, Month month) {
        TrainerSummary trainerSummary = hashOperations.get(TRAINER_SUMMARY_KEY, username);
        if (trainerSummary != null) {
            Long workLoad = trainerSummary.getMonthlyWorkLoad().get(month);
            return Optional.ofNullable(workLoad);
        }
        return Optional.empty();
    }

    @Override
    public void updateTrainerWorkLoad(String username, Month month, Long updatedWorkLoad) {
        TrainerSummary trainerSummary = findTrainerSummaryByUsername(username).orElse(new TrainerSummary());
        trainerSummary.getMonthlyWorkLoad().put(month, updatedWorkLoad);
        hashOperations.put(TRAINER_SUMMARY_KEY, username, trainerSummary);
    }

    @Override
    public void deleteTrainerWorkLoad(String username, Month month) {
        TrainerSummary trainerSummary = findTrainerSummaryByUsername(username).orElse(new TrainerSummary());
        trainerSummary.getMonthlyWorkLoad().remove(month);
        hashOperations.put(TRAINER_SUMMARY_KEY, username, trainerSummary);
    }

    @Override
    public Optional<TrainerSummary> findTrainerSummaryByUsername(String username) {
        TrainerSummary trainerSummary = hashOperations.get(TRAINER_SUMMARY_KEY, username);
        return Optional.ofNullable(trainerSummary);
    }
}
