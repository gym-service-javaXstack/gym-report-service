package com.example.gymreport.service;

import com.example.gymreport.dto.TrainerWorkLoadRequest;
import com.example.gymreport.model.TrainerSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.Collections;
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

        int updatedWorkLoad;
        switch (request.getActionType()) {
            case ADD:
                updatedWorkLoad = currentWorkLoad + duration;
                break;
            case DELETE:
                updatedWorkLoad = currentWorkLoad - duration;
                if (updatedWorkLoad < 0) {
                    updatedWorkLoad = 0;
                }
                break;
            default:
                updatedWorkLoad = currentWorkLoad;
                break;
        }

        yearWorkLoad.put(trainingMonth, updatedWorkLoad);
        trainerSummaryService.saveTrainerSummary(trainerSummary);

        log.info("Exit GymReportService processTrainerWorkload method");
    }

    public Integer getWorkloadByUsernameAndMonth(String username, int year, int monthValue, String authHeader) {
        log.info("Entry GymReportService getWorkloadByUsernameAndMonth method");
        authenticationService.validateToken(authHeader);

        return trainerSummaryService.findTrainerSummaryByUsername(username)
                .map(trainerSummary -> trainerSummary.getYearlySummary().getOrDefault(year, Collections.emptyMap()))
                .map(yearlySummary -> yearlySummary.getOrDefault(Month.of(monthValue), 0))
                .orElse(0);
    }
}
