package com.example.gymreport.redis.service;

import com.example.gymreport.model.TrainerSummary;
import com.example.gymreport.redis.repository.RedisTrainerWorkLoadRepository;
import com.example.gymreport.service.TrainerSummaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "NOSQL_TYPE", havingValue = "redis")
public class RedisTrainerSummaryService implements TrainerSummaryService {
    private final RedisTrainerWorkLoadRepository redisTrainerWorkLoadRepository;

    @Override
    public Optional<TrainerSummary> findTrainerSummaryByUsername(String username) {
        return redisTrainerWorkLoadRepository.findTrainerSummaryByUsername(username);
    }

    @Override
    public void saveTrainerSummary(TrainerSummary trainerSummary) {
        redisTrainerWorkLoadRepository.saveTrainerSummary(trainerSummary.getUsername(), trainerSummary);
    }
}
