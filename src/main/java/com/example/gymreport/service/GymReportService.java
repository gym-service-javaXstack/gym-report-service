package com.example.gymreport.service;

import com.example.gymreport.dto.ActionType;
import com.example.gymreport.dto.TrainerWorkLoadRequest;
import com.example.gymreport.redis.model.TrainerSummary;
import com.example.gymreport.redis.service.TrainerSummaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class GymReportService {
    private final TrainerSummaryService trainerSummaryService;

    public void processTrainerWorkload(TrainerWorkLoadRequest request) {
        log.info("Entry GymReportService processTrainerWorkload method, request = {}", request);

        Long duration = request.getTrainingDuration();
        Month trainingMonth = Month.values()[request.getTrainingDate().getMonthValue()-1];

        // Получение текущей рабочей нагрузки тренера или создание новой, если она отсутствует
        TrainerSummary trainerSummary = trainerSummaryService.findTrainerSummaryByUsername(request.getUsername())
                .orElse(new TrainerSummary());

        Map<Month, Long> monthlyWorkLoad = trainerSummary.getMonthlyWorkLoad();
        Long currentWorkLoad = monthlyWorkLoad.getOrDefault(trainingMonth, 0L);

        if (request.getActionType() == ActionType.ADD) {
            monthlyWorkLoad.put(trainingMonth, currentWorkLoad + duration);
        } else if (request.getActionType() == ActionType.DELETE) {
            long updatedWorkLoad = currentWorkLoad - duration;

            if (updatedWorkLoad < 0) {
                monthlyWorkLoad.put(trainingMonth, 0L);
            } else {
                monthlyWorkLoad.put(trainingMonth, updatedWorkLoad);
            }
        }

        // Сохранение обновленной рабочей нагрузки в Redis
        trainerSummary.setMonthlyWorkLoad(monthlyWorkLoad);
        trainerSummaryService.saveTrainerWorkLoad(request.getUsername(), trainingMonth, monthlyWorkLoad.get(trainingMonth));

        log.info("Exit GymReportService processTrainerWorkload method");
    }

    public TrainerSummary getTrainerSummaryByUsername(String username) {
        return trainerSummaryService.findTrainerSummaryByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("No such user at DB"));
    }
}
