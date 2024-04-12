package com.example.gymreport.redis.service;

import com.example.gymreport.redis.model.TrainerSummary;
import com.example.gymreport.redis.repository.TrainerMonthlyWorkLoadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainerSummaryService {
    private final TrainerMonthlyWorkLoadRepository trainerMonthlyWorkLoadRepository;

    public void saveTrainerWorkLoad(String username, Month month, Long workLoad) {
        log.info("Entry TrainerSummaryService saveTrainerWorkLoad method");
        trainerMonthlyWorkLoadRepository.saveTrainerWorkLoad(username, month, workLoad);
        log.info("Exit TrainerSummaryService saveTrainerWorkLoad method");
    }

    public Optional<Long> findTrainerWorkLoad(String username, Month month) {
        return trainerMonthlyWorkLoadRepository.findTrainerWorkLoad(username, month);
    }

    public void updateTrainerWorkLoad(String username, Month month, Long updatedWorkLoad) {
        trainerMonthlyWorkLoadRepository.updateTrainerWorkLoad(username, month, updatedWorkLoad);
    }

    public void deleteTrainerWorkLoad(String username, Month month) {
        trainerMonthlyWorkLoadRepository.deleteTrainerWorkLoad(username, month);
    }

    public Optional<TrainerSummary> findTrainerSummaryByUsername(String username) {
        return trainerMonthlyWorkLoadRepository.findTrainerSummaryByUsername(username);
    }
}
