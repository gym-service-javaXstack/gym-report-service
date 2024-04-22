package com.example.gymreport.service;

import com.example.gymreport.feign.GymMainClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final GymMainClient gymMainClient;

    public void validateToken(String authHeader) {
        log.info("AuthenticationService validateToken");
        gymMainClient.validateToken(authHeader, getCorrelationId());
    }

    private static String getCorrelationId() {
        log.info("AuthenticationService getCorrelationId");
        String correlationIdWithPrefix = MDC.get("correlationId");
        return correlationIdWithPrefix.substring(correlationIdWithPrefix.indexOf('[') + 1, correlationIdWithPrefix.indexOf(']'));
    }
}
