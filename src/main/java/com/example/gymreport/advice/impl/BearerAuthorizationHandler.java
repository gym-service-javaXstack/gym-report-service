package com.example.gymreport.advice.impl;

import com.example.gymreport.advice.AuthorizationHandler;
import com.example.gymreport.exceptions.Error;
import com.example.gymreport.exceptions.UnauthorizedException;
import com.example.gymreport.service.web.WebClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class BearerAuthorizationHandler implements AuthorizationHandler {
    private final WebClientService bearerWebClientServiceImpl;

    @Override
    public boolean isApplicable(String authHeader) {
        log.info("BearerAuthorizationHandler isApplicable method");
        return authHeader != null && authHeader.startsWith("Bearer ");
    }

    @Override
    public void processAuthorization(String authHeader) {
        bearerWebClientServiceImpl
                .requestDataFromOtherMicroservice(authHeader)
                .doOnError(this::handleError)
                .block();
    }

    private void handleError(Throwable throwable) {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        if (throwable instanceof WebClientResponseException ex) {
            try {
                Error error = objectMapper.readValue(ex.getResponseBodyAsString(), Error.class);
                if (ex.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                    // Return the original error message
                    log.error("Error when calling gym-main service", error);
                    throw new UnauthorizedException(error.message(), error.errorType(), error.timeStamp());
                } else {
                    // Handle other errors
                    log.error("Error when calling gym-main service", ex.getMessage());
                    throw new RuntimeException(ex.getMessage());
                }
            } catch (IOException e) {
                log.error("Failed to process error response from gym-main service", e);
                throw new RuntimeException("Failed to process error response from gym-main service", e);
            }
        } else {
            // Handle other types of errors
            log.error("Error when calling gym-main service", throwable.getMessage());
            throw new RuntimeException(throwable.getMessage());
        }
    }
}
