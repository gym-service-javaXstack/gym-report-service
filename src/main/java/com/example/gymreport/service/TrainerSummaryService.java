package com.example.gymreport.service;

import com.example.gymreport.model.TrainerSummary;

import java.util.Optional;

public interface TrainerSummaryService {

    Optional<TrainerSummary> findTrainerSummaryByUsername(String username);

    void saveTrainerSummary(TrainerSummary trainerSummary);
}
