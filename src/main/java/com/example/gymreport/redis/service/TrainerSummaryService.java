package com.example.gymreport.redis.service;

import com.example.gymreport.redis.model.TrainerSummary;
import com.example.gymreport.redis.repository.TrainerWorkLoadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainerSummaryService {
    private final TrainerWorkLoadRepository trainerWorkLoadRepository;

    public Optional<Integer> findTrainerWorkLoad(String username, int year, int monthValue) {
        Month month = Month.of(monthValue);
        return trainerWorkLoadRepository.findTrainerWorkLoadByMonth(username, year, month);
    }

    public Optional<TrainerSummary> findTrainerSummaryByUsername(String username) {
        return trainerWorkLoadRepository.findTrainerSummaryByUsername(username);
    }

    public void saveTrainerSummary(String username, TrainerSummary trainerSummary) {
        trainerWorkLoadRepository.saveTrainerSummary(username, trainerSummary);
    }
}
