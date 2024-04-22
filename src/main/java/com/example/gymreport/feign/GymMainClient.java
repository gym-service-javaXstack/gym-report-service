package com.example.gymreport.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "gym-main")
@Component
public interface GymMainClient {

    @GetMapping("/api/v1/token")
    @CircuitBreaker(name = "GymMainClientvalidateTokenStringString")
    ResponseEntity<Void> validateToken(@RequestHeader("Authorization") String token,
                                       @RequestHeader("X-Trace-Id") String correlationId);
}
