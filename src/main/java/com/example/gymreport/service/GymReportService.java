package com.example.gymreport.service;

import com.example.gymreport.dto.ActionType;
import com.example.gymreport.dto.TrainerWorkLoadRequest;
import com.example.gymreport.redis.model.TrainerSummary;
import com.example.gymreport.redis.service.TrainerSummaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.EnumMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class GymReportService {
    private final TrainerSummaryService trainerSummaryService;
    private final AuthenticationService authenticationService;

    public void processTrainerWorkload(TrainerWorkLoadRequest request) {
        log.info("Entry GymReportService processTrainerWorkload method, request = {}", request);

        Integer duration = request.getTrainingDuration();
        Month trainingMonth = request.getTrainingDate().getMonth();
        int trainingYear = request.getTrainingDate().getYear();

        TrainerSummary trainerSummary = trainerSummaryService.findTrainerSummaryByUsername(request.getUsername())
                .orElse(new TrainerSummary());

        trainerSummary.setUsername(request.getUsername());
        trainerSummary.setFirstName(request.getFirstName());
        trainerSummary.setLastName(request.getLastName());
        trainerSummary.setStatus(request.getIsActive());

        Map<Month, Integer> yearWorkLoad = trainerSummary.getYearlySummary().computeIfAbsent(trainingYear, k -> new EnumMap<>(Month.class));

        Integer currentWorkLoad = yearWorkLoad.getOrDefault(trainingMonth, 0);

        if (request.getActionType() == ActionType.ADD) {
            yearWorkLoad.put(trainingMonth, currentWorkLoad + duration);
        } else if (request.getActionType() == ActionType.DELETE) {
            int updatedWorkLoad = currentWorkLoad - duration;

            if (updatedWorkLoad < 0) {
                yearWorkLoad.put(trainingMonth, 0);
            } else {
                yearWorkLoad.put(trainingMonth, updatedWorkLoad);
            }
        }

        trainerSummaryService.saveTrainerSummary(request.getUsername(), trainerSummary);

        log.info("Exit GymReportService processTrainerWorkload method");
    }

    public Integer getWorkloadByUsernameAndMonth(String username, int year, int monthValue, String authHeader) {
        authenticationService.validateToken(authHeader);
        return trainerSummaryService.findTrainerWorkLoad(username, year, monthValue)
                .orElse(0);
    }
}
