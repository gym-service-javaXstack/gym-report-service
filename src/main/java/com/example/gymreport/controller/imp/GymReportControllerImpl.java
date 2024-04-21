package com.example.gymreport.controller.imp;

import com.example.gymreport.controller.GymReportApi;
import com.example.gymreport.dto.TrainerWorkLoadRequest;
import com.example.gymreport.service.GymReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class GymReportControllerImpl implements GymReportApi {
    private final GymReportService gymReportService;

    @Override
    public ResponseEntity<Void> processTrainerWorkload(TrainerWorkLoadRequest request) {
        log.info("Entry GymReportControllerImpl processTrainerWorkload method, request = {}", request);
        gymReportService.processTrainerWorkload(request);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Long> getTrainerSummaryByUsername(
            String username,
            int year,
            int monthValue,
            String authHeader) {
        Long workloadByUsernameAndMonth = gymReportService.getWorkloadByUsernameAndMonth(username, year, monthValue, authHeader);
        return ResponseEntity.ok(workloadByUsernameAndMonth);
    }
}
