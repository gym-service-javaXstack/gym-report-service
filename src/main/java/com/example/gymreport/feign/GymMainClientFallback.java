package com.example.gymreport.feign;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


public class GymMainClientFallback implements GymMainClient {

    @Override
    public ResponseEntity<Void> validateToken(String token, String correlationId) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }
}
