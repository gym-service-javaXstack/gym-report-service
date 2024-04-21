package com.example.gymreport.service;

import com.example.gymreport.advice.AuthorizationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final List<AuthorizationHandler> authorizationHandlers;

    public void handleAuthorization(String authHeader) {
        AuthorizationHandler handler = authorizationHandlers.stream()
                .filter(h -> h.isApplicable(authHeader))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No suitable authorization handler found"));

        handler.processAuthorization(authHeader);
    }
}
