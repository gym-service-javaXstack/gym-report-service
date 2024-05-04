package com.example.gymreport.controller.imp;

import com.example.gymreport.controller.GymReportApi;
import com.example.gymreport.dto.TrainerWorkLoadRequest;
import com.example.gymreport.service.GymReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "INTERACTION_BETWEEN_MICROSERVICES", havingValue = "feign", matchIfMissing = true)
public class GymReportControllerImpl implements GymReportApi {
    private final GymReportService gymReportService;

    @Override
    public ResponseEntity<Void> processTrainerWorkload(TrainerWorkLoadRequest request) {
        log.info("Entry GymReportControllerImpl processTrainerWorkload method, request = {}", request);
        gymReportService.processTrainerWorkload(request);
        log.info("Exit GymReportControllerImpl processTrainerWorkload method, request = {}", request);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Integer> getTrainerSummaryByUsername(
            String username,
            int year,
            int monthValue,
            String authHeader) {
        log.info("Entry GymReportControllerImpl getTrainerSummaryByUsername method");
        Integer workloadByUsernameAndMonth = gymReportService.getWorkloadByUsernameAndMonth(username, year, monthValue, authHeader);
        log.info("Exit GymReportControllerImpl getTrainerSummaryByUsername method");
        return ResponseEntity.ok(workloadByUsernameAndMonth);
    }
}
