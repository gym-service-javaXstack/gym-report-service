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

        Long duration = request.getTrainingDuration();
        Month trainingMonth = request.getTrainingDate().getMonth();
        int trainingYear = request.getTrainingDate().getYear();

        TrainerSummary trainerSummary = trainerSummaryService.findTrainerSummaryByUsername(request.getUsername())
                .orElse(new TrainerSummary());

        trainerSummary.setUsername(request.getUsername());
        trainerSummary.setFirstName(request.getFirstName());
        trainerSummary.setLastName(request.getLastName());
        trainerSummary.setStatus(request.getIsActive());

        Map<Month, Long> yearWorkLoad = trainerSummary.getYearlySummary().computeIfAbsent(trainingYear, k -> new EnumMap<>(Month.class));

        Long currentWorkLoad = yearWorkLoad.getOrDefault(trainingMonth, 0L);

        if (request.getActionType() == ActionType.ADD) {
            yearWorkLoad.put(trainingMonth, currentWorkLoad + duration);
        } else if (request.getActionType() == ActionType.DELETE) {
            long updatedWorkLoad = currentWorkLoad - duration;

            if (updatedWorkLoad < 0) {
                yearWorkLoad.put(trainingMonth, 0L);
            } else {
                yearWorkLoad.put(trainingMonth, updatedWorkLoad);
            }
        }

        trainerSummaryService.saveTrainerSummary(request.getUsername(), trainerSummary);

        log.info("Exit GymReportService processTrainerWorkload method");
    }

    public Long getWorkloadByUsernameAndMonth(String username, int year, int monthValue, String authHeader) {
        authenticationService.handleAuthorization(authHeader);
        return trainerSummaryService.findTrainerWorkLoad(username, year, monthValue)
                .orElse(0L);
    }
}
