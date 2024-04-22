package com.example.gymreport.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "gym-main")
public interface GymMainClient {
    @GetMapping("/api/v1/token")
    ResponseEntity<Void> validateToken(@RequestHeader("Authorization") String token,
                                       @RequestHeader("X-Trace-Id") String correlationId);
}
