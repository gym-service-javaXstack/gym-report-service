package com.example.gymreport.service;

import com.example.gymreport.feign.GymMainFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;


/**
 * <p>The {@code AuthenticationService} class is responsible for the authentication
 * and validation of tokens through the Gym main client.</p>
 *
 * <p><b>Note:</b> This class is currently NOT in use in the application, because have problems with mocking via BDD approach.</p>
 *
 * <p>Dependencies:</p>
 * <ul>
 *     <li>{@link GymMainFeignClient} - Feign client for interacting with the main Gym service.</li>
 * </ul>
 *
 * <p>Annotation Summary:</p>
 * <ul>
 *     <li>{@code @Service} - Marks this class as a Spring service component.</li>
 *     <li>{@code @Slf4j} - Enables logging via SLF4J.</li>
 *     <li>{@code @RequiredArgsConstructor} - Generates a constructor with required arguments (final fields).</li>
 * </ul>
 *
 * @see GymMainFeignClient
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final GymMainFeignClient gymMainFeignClient;


    /**
     * Validates the given authentication token by calling the Gym main client.
     *
     * @param authHeader the authentication token to be validated.
     */
    public void validateToken(String authHeader) {
        log.info("AuthenticationService validateToken");
        gymMainFeignClient.validateToken(authHeader, getCorrelationId());
    }


    /**
     * Retrieves the current correlation ID from the MDC (Mapped Diagnostic Context).
     *
     * @return the correlation ID.
     */
    private static String getCorrelationId() {
        log.info("AuthenticationService getCorrelationId");
        String correlationIdWithPrefix = MDC.get("correlationId");
        return correlationIdWithPrefix.substring(correlationIdWithPrefix.indexOf('[') + 1, correlationIdWithPrefix.indexOf(']'));
    }
}
