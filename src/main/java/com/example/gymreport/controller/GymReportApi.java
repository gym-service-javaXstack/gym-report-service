package com.example.gymreport.controller;

import com.example.gymreport.dto.TrainerWorkLoadRequest;
import com.example.gymreport.redis.model.TrainerSummary;
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
    ResponseEntity<TrainerSummary> getTrainerSummaryByUsername(@RequestParam String username);
}
