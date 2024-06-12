package com.example.gymreport.service;

import com.example.gymreport.dto.TrainerWorkLoadRequest;
import com.example.gymreport.model.TrainerSummary;

import java.util.Optional;

public interface TrainerSummaryService {

    Optional<TrainerSummary> findTrainerSummaryByTrainerWorkLoadRequest(TrainerWorkLoadRequest request);

    Optional<TrainerSummary> findTrainerSummaryByUsername(String username);

    void saveTrainerSummary(TrainerSummary trainerSummary);
}
