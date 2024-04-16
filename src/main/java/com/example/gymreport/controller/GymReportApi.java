package com.example.gymreport.controller;

import com.example.gymreport.dto.TrainerWorkLoadRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1")
public interface GymReportApi {

    @PostMapping("/workload")
    ResponseEntity<Void> processTrainerWorkload(@RequestBody TrainerWorkLoadRequest request);

    @GetMapping("/workload")
    ResponseEntity<Long> getTrainerSummaryByUsername(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "year") int year,
            @RequestParam(name = "month") @Min(1) @Max(12) int monthValue
    );
}
